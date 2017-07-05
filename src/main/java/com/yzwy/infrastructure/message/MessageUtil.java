package com.yzwy.infrastructure.message;

import com.yzwy.infrastructure.message.model.BaseParam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by gin on 2017/5/31.
 */
public class MessageUtil {

    private static final Logger LOG = LogManager.getLogger(MessageUtil.class);


    public static Map formateReceivedMap(BaseParam param) throws Exception {
        Map formattedMap  =  new HashMap<>();

        //todo 通过BaseParam等入参 构建特征值对象messagecharacter以及形成dataMap--反射？
        MessageCharacter character = new MessageCharacter();
        //character必须包含controlcode functioncode  destTerminalType destDeviceType
        character.setControlCode(param.getControlCode());//00表示组装下发报文
        character.setFunctionCode(param.getFunctionCode());
        character.setDestTerminalType(param.getDestTerminalType());
        character.setDestDeviceType(param.getDestDeviceType());

        Map templetMap = findMakeTemplet(character);
        if (templetMap==null) {
            LOG.info("组装报文出错：找不到模版");
            return  null;
        }

        formattedMap.put("templet",templetMap);
        //反射获取参数列表map
        formattedMap.put("data",ClassUtil.getParameterMap(param));

        //return map必须包含data:map templet:map
        return formattedMap;
    }

    public static MessageCharacter filterWithDevice(String msgString){

        //todo 此处可扩展 根据头码 获得协议类型？  根据协议类型读取不同位置参数？需添加协议表来控制
        //todo 筛选报文 仅判断报文是否符合协议规范 做校验等

        msgString = msgString.trim();
        if (msgString == null || msgString.length() == 0){
            LOG.info("报文筛选：报文字符串长度错误或为空");
            return null;
        }
        String[] msgArr = msgString.trim().split("\\s+");
        if (msgArr == null || msgArr.length < 16) {
            //协议中报文最短为16
            LOG.info("报文筛选：有效位为空或长度不在报文协议内");
            return null;
        }
        if (!msgArr[0].equals("AA")){
            //验证头码
            LOG.info("报文筛选：头码校验失败");
            return null;
        }

        String[] LengthArrr = new String[2];
        LengthArrr[0] = msgArr[1];// 固定位置获取2bytes长度
        LengthArrr[1] = msgArr[2];
        Integer length = Integer.parseInt(String.join("",LengthArrr),16);
        if (length != msgArr.length){
            //验证长度
            LOG.info("报文筛选：长度校验失败 realLen:"+msgArr.length +" needLen:"+length);
            return null;
        }

        String crcStr = msgArr[msgArr.length-1]; //最后一位为crc校验位
        String[] subVarArr = new String[msgArr.length-1];
        System.arraycopy(msgArr,0,subVarArr,0,subVarArr.length);
        String crcCalculateStr = RadixUtil.getCRCStrByHexString(subVarArr);
        if (!crcStr.equals(crcCalculateStr)){
            //crc校验
            LOG.info("报文筛选：CRC校验失败");
            return null;
        }



        String funcCode = msgArr[4];//功能码  --读取固定位置
        String controlCode = msgArr[3];//控制码 --读固定位置 以区分返回

        String[] sessionId = new String[6];//会话id 固定位置 第16位开始起6的长度
        System.arraycopy(msgArr,15,sessionId,0,6);
        //获取6位时间戳  --此处当报文筛选的唯一标识用以区分调用者
        String sessionIdStr = String.join(" ",sessionId); //以空格分隔的6byte唯一标识

        String destTerminalType = msgArr[5]; //目标地址终端类型
        String destDeviceType = msgArr[8]; //目标地址设备类型
        String sourceTerminalType = msgArr[10];//源地址终端类型
        String sourceDeviceType = msgArr[13];//源地址设备类型

        MessageCharacter character = new MessageCharacter();
        character.setCrc(crcStr);
        character.setControlCode(controlCode);
        character.setDestTerminalType(destTerminalType);
        character.setDestDeviceType(destDeviceType);
        character.setSourceTerminalType(sourceTerminalType);
        character.setSourceDeviceType(sourceDeviceType);
        character.setSessionCode(sessionIdStr);
        character.setFunctionCode(funcCode);

        return character;
    }



    public static Map formateReceivedData(MessageCharacter character,String msgString) throws Exception {

        Map formattedMap  =  new HashMap<>();
        LOG.info("满足筛选条件 开始解析获取到的报文:["+msgString+"]");

        //todo 从缓存中读取报文模版
        Map templetMap = findAnalyzeTemplet(character);
        if (templetMap == null){
            return  null;
        }

        Map dataMap = new HashMap();
        dataMap.put("rowHexStr",msgString);

        formattedMap.put("templet",templetMap);
        formattedMap.put("data",dataMap);
        return formattedMap;

    }

    public static Map pickingSubData(Map<String,Map> formattedMap){

        Map dataMap  =  formattedMap.get("data");
        String[] rowHexArr = ((String)dataMap.get("rowHexStr")).split("\\s+");

        //提取数据
        String templetStr = (String)formattedMap.get("templet").get("templet");
        String tempVarStr = (String)formattedMap.get("templet").get("templet_var");
        String tempLenStr = (String)formattedMap.get("templet").get("templet_length");
        String[] tempLenArr = tempLenStr.split("\\,+");
        String[] templetArr = templetStr.split("\\,+"); //原模版值
//        String[] tempVarArr = tempVarStr.split("\\,+");//填入了固定值以及长度的模版 可以做验证用？
        Map indexMap = new HashMap(); //获得特定指令的index
        Map lengthMap = new HashMap();
        int index = 0;
        String code = "";
        String strValue = "";
        String data = "";
        int length = 0;
        for (int i = 0; i < templetArr.length; i++) {
            code = templetArr[i];
            length = Integer.parseInt(tempLenArr[i]);

            lengthMap.put(code,length);
            indexMap.put(code,index);
            index += length;
            //获得指令域的indexMap

        }

        //todo 比对入参长度

        if (rowHexArr.length!=index){
            LOG.error("解析报文错误：该报文长度与模版长度不符，请检查报文模版或硬件--"+dataMap.get("rowHexStr"));
            return null;
        }
        LOG.info("picking:---------------------begin-----------------------");
        Iterator entries = indexMap.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            code = (String) entry.getKey();
            index = (Integer)entry.getValue();
            length = (Integer)lengthMap.get(code);
            String[] tempHexArr = new String[length];
            System.arraycopy(rowHexArr,index,tempHexArr,0,length);
            strValue = String.join(" ",tempHexArr);
            dataMap.put(code,strValue);
            LOG.info("code = " + code + ", Value = " + strValue);

        }
        LOG.info( "picking:---------------------end-----------------------");

        return dataMap;
    }

    public static String[] pakingPubData(Map<String,Map> formattedMap){
        //填装发送报文模版 入参包含data templet两个map
        Map data = formattedMap.get("data");
        String tempLenStr = (String)formattedMap.get("templet").get("templet_length");
        String tempIsvarStr = (String)formattedMap.get("templet").get("templet_isvar");
        String tempVarStr = (String)formattedMap.get("templet").get("templet_var");
        String[] tempLenArr = tempLenStr.split("\\,+");
        String[] tempIsvarArr = tempIsvarStr.split("\\,+");
        String[] tempVarArr = tempVarStr.split("\\,+");
        Map indexMap = new HashMap(); //获得特定指令域的Index 方便校验
        int index = 0;
        String code = "";
        String value = "";
        String is_var = "";
        int length = 0;
        int realLen = 0;
        for (int i = 0; i < tempVarArr.length; i++) {
            is_var = tempIsvarArr[i];
            code = tempVarArr[i];
            length = Integer.parseInt(tempLenArr[i]);

            indexMap.put(code,index);//先取值再拿当前长度值计算
            index += length;

            // "1"值-填入变量
            if (is_var.equals("1")){
                value = (String) data.get(code);
                if (value == null ){
                    LOG.error("报文组装参数错误,期望参数:"+code+" 长度:"+length);
                }else {
                    realLen = value.split("\\s+").length;
                    if (realLen != length){
                        LOG.error("报文组装参数长度错误："+code+" 期望长度:"+length+" 实际长度:"+realLen);
                    }
                    tempVarArr[i] = value;
                }

            }
            // "2"值-计算预留 不填值
        }
        String templetStr = String.join(" ",tempVarArr);
        tempVarArr = templetStr.split("\\s+"); //转换为16进制字符串数组 每个元素存1byte
        //todo 校验组装的长度
        Integer lenthAll = (Integer) formattedMap.get("templet").get("length");
        if (lenthAll != tempVarArr.length){
            LOG.error("报文组装总长度错误");
            return null;
        }

        //计算校验和并填入
        tempVarArr = CRCCheck(tempVarArr);

        templetStr = String.join(" ",tempVarArr);
        LOG.info("packingPub:"+templetStr);

        return tempVarArr;
    }

    /**
     * 组装报文 入参为各种设备类型参数 继承于BaseParam类型
     * @param param
     * @return
     * @throws Exception
     */
    public static String makeMessage(BaseParam param) throws Exception {
        // 多包下发指令 一次组装完毕 根据device的packingNumbers包数 以及参数map-makelist
        // 组装完成的报文存入MakedMessageList

            //获取需要组装的参数数组
            Map formattedMap = formateReceivedMap(param);
            if (formattedMap == null){
//                LOG.info("组装报文出错：找不到模版");
                return null;
            }
            String[] msgArr = pakingPubData(formattedMap);
            if (msgArr == null){
                return null;
            }
            String messageInfo =String.join(" ",msgArr);

        //设置组装完成的报文数组

        return messageInfo;
    }


    /**
     * 解析报文调用 返回包含所有参数的map
     * @param receivedHexStr
     * @return
     * @throws Exception
     */
    public static Map analyzeMessage(String receivedHexStr) throws Exception {
        if (receivedHexStr == null || receivedHexStr.length()==0){
            LOG.info("解析-报文为空");
            return null;
        }

        MessageCharacter character = filterWithDevice(receivedHexStr);
        if (null == character){
            LOG.info("解析-报文未通过筛选");
            return null;
        }
        Map formattedMap = formateReceivedData(character,receivedHexStr);
        if (null == formattedMap){
            LOG.info("解析-未查到报文模版");
            return null;
        }
        Map parameterMap = pickingSubData(formattedMap);
        if (null == parameterMap){
            LOG.info("解析-提取参数map失败");
            return null;
        }
        return parameterMap;

    }


    //删除stirng数组中指定的Str并返回Str
    public static String reduceLengthArray(String lengthArrStr,String reducedStr){
        String[] lengthArr = lengthArrStr.trim().split(",");//数据库中以逗号分隔
        List<String> lengthList = new ArrayList<>();
        for (String str : lengthArr){
            lengthList.add(str);
        }
        int deleteIndex = 0;
        for (int i = 0 ; i < lengthList.size(); i ++){
            if (lengthList.get(i).equals(reducedStr)){
                deleteIndex = i;
                break;
            }
        }
        LOG.info("length needed delete:["+reducedStr+"] index:"+deleteIndex);
        lengthList.remove(deleteIndex);

        if (lengthList.size()==0) {
            //删除最后一个元素的处理
            return "";
        }


        return String.join(",",lengthList);
    }

    /**
     * string数组转list
     * @param strArr
     * @return
     */
    public static List<String> stringArrayToList(String[] strArr){
        List<String> list = new ArrayList<>();
        for (String str : strArr){
            list.add(str);
        }
        return list;
    }

    /**
     * CRC校验
     */
    public static String[] CRCCheck(String [] tempVarArr){
            LOG.info("组装报文-智能家具协议-计算校验和 ："+Arrays.asList(tempVarArr));
            //计算校验和并填入
            String[] subVarArr = new String[tempVarArr.length-1];
            System.arraycopy(tempVarArr,0,subVarArr,0,subVarArr.length);
            String CRCHexStr = RadixUtil.getCRCStrByHexString(subVarArr);
            tempVarArr[tempVarArr.length-1] = CRCHexStr;
        return tempVarArr;
    }

    /**
     * 获取组装的模版
     * @return
     */
    public  static Map findMakeTemplet(MessageCharacter messageCharacter){
        return MessageCache.getInstance().getTempletByKey(uniqueTempletKey(messageCharacter));
    }

    /**
     * 通过唯一标示从缓存中获取模版map
     * @param messageCharacter
     * @return
     */
    private static Map findAnalyzeTemplet(MessageCharacter messageCharacter){

        String uniqueKey = uniqueTempletKey(messageCharacter);
        Map resultMap = MessageCache.getInstance().getTempletByKey(uniqueKey);
        if (resultMap == null){
            LOG.error("解析报文错误 报文模版未找到 uniqueKey:"+uniqueKey);
            return null;
        }
        return MessageCache.getInstance().getTempletByKey(uniqueTempletKey(messageCharacter));
    }


    /**
     * 通过报文特征信息获取模版唯一标示key
     * @param messageCharacter
     * @return
     */
    private static String uniqueTempletKey(MessageCharacter messageCharacter){
        //判断报文类型是发送还是响应
        boolean isResponse = Integer.parseInt(messageCharacter.getControlCode(),16)>=0x80;

        StringBuilder templetKey = new StringBuilder();
        if (isResponse){
            //响应-80开头
            templetKey.append("80").append(messageCharacter.getFunctionCode()).append(messageCharacter
                    .getSourceTerminalType());
            //响应-通过源地址终端类型判断
            if (messageCharacter.getSourceTerminalType().equals("01")){
                //todo 设备 添加设备类型区分模版？ 目前设备模版是统一一个 不用区分设备
//                templetKey.append(messageCharacter.getSourceDeviceType());
            }else{
                //终端
            }
        }else {
            //下发
            templetKey.append("00").append(messageCharacter.getFunctionCode()).append(messageCharacter
                    .getDestTerminalType());
            //下发-通过目标终端类型判断
            if (messageCharacter.getDestTerminalType().equals("01")){
                //todo 设备 添加设备类型区分模版？ 目前设备模版是统一一个 不用区分设备
//                templetKey.append(messageCharacter.getDestDeviceType());
            }else{
                //终端
            }
        }
        LOG.info("获取报文模版-uniqueKey ："+templetKey.toString());

        return templetKey.toString();
    }


}
