package com.yzwy.application.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yzwy.application.MessageService;
import com.yzwy.domain.model.Msg_templet;
import com.yzwy.domain.model.Properties;
import com.yzwy.domain.repository.Msg_templetMapper;
import com.yzwy.domain.repository.PropertiesMapper;
import com.yzwy.infrastructure.StringUtil;
import com.yzwy.interfaces.facade.dto.MsgTempletJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.xml.ws.handler.MessageContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by gin on 2017/6/2.
 */
@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    Msg_templetMapper templetMapper;

    @Autowired
    PropertiesMapper propertiesMapper;


    @Override
    public void calculateTemplet(Integer id){

        List<Msg_templet> list = templetMapper.queryMsgTemplet(id);
        Msg_templet templet = null;
        if (list!= null && list.size()>0){
           templet = getPropertiesAndMakeTemplet(list.get(0));
            templetMapper.updateTempletByCalcu(templet);
        }
        System.out.println(templet);

    }

    public void caclulateAllTemplet(){
        List<Msg_templet> list = templetMapper.queryAllMsgTemplet();
        Msg_templet templet = null;
        for (int i = 0; i < list.size() ; i++) {
            templet = getPropertiesAndMakeTemplet(list.get(i));
            templetMapper.updateTempletByCalcu(templet);
        }
        System.out.println("all templet has been updated");
    }



    private Msg_templet getPropertiesAndMakeTemplet(Msg_templet templet){

        String[] fieldArray = templet.getTemplet().split("\\,+");
        //根据指令域模版计算出对应指令域长度数组 变量数组 并存入模版表对应条目下
        String[] lengthArr = new String[fieldArray.length];
        String[] isvarArr = new String[fieldArray.length];
        String[] varArr = new String[fieldArray.length];
        String is_var = "";
        Map indexMap = new HashMap(); //方便等会取值计算
        int index = 0;//累加长度以计算index
        int i = 0;
        System.out.println("得到模版 开始计算："+ Arrays.asList(fieldArray));
        for (String fieldStr : fieldArray) {

            //然后处理指令域后面的序号
            String fieldCode = fieldStr.replaceAll("\\d+", "");//去掉code后面的序号以查表
            //todo 模版的指令域出现错误-未在field表中获取得到时的错误提示处理

            //查询得到code对应唯一指令域
            Properties field = new Properties();
            List<Properties> listField = propertiesMapper.queryProperty(fieldCode);
            if (listField != null && listField.size() == 1) {
                //code唯一 对应一个指令域
                field = listField.get(0);
            }
            System.out.println("fieldStr :"+fieldStr+" ----code :"+fieldCode);
            Integer length = field.getLength();
            lengthArr[i] = String.valueOf(length); //组装长度模版
            is_var = field.getIs_var().toString();
            isvarArr[i] = is_var;//组装变量类型模版

            //判断该指令域是填入固定值还是变量名
            varArr[i] = is_var.equals("0") ? field.getDefault_field() : fieldStr;
            if (indexMap.containsKey(fieldStr)) {
                //todo 报文模版中出现重复指令域 报错  规定相同指令域后面加数字以区分如AA1 BB1 AA2 BB2

            }

            indexMap.put(fieldStr, index);//只累加该元素之前的元素的length 注意先获取再运算
            index += length;

            i++;//模版中一个指令域的查询替换操作结束
        }

        //组装出各模版并存入表
        String lengthStr = String.join(",", lengthArr);
        String isvarStr = String.join(",", isvarArr);
        String varStr = String.join(",", varArr);
        Integer aLength = (int) indexMap.get("crc") + 1;

        //组装模版用 计算出长度并替换相关的指令域
        varStr = varStr.replace("lengthAll", StringUtil.intStrToHexString(aLength, 2));

        templet.setLength(aLength);
        templet.setTemplet_length(lengthStr);
        templet.setTemplet_isvar(isvarStr);
        templet.setTemplet_var(varStr);

        System.out.println("templet_length(" + aLength + ") :[" + lengthStr + "]");
        System.out.println("templet_isvar(" + aLength + ") :[" + isvarStr + "]");
        System.out.println("templet_var(" + aLength + ") :[" + varStr + "]");

        return templet;
    }

    @Override
    public void generateTempletJson() throws IOException {
        List<MsgTempletJson> listForJson = new ArrayList<>();
        Map<String,Msg_templet> map = new HashMap<>();
        List<Msg_templet> list = templetMapper.queryAllMsgTemplet();

        for (Msg_templet templet : list) {
            //由80／00+ funcCode +terminalType+deviceType生成唯一key
            StringBuilder uniqueKey = new StringBuilder();
            uniqueKey.append(templet.getControl_code()).append(templet.getFunction_code()).append(templet
                    .getTerminal_type());
            if (templet.getTerminal_type().equals("01")){
                //设备类型模版 多一个参数生成
//                uniqueKey.append(templet.getDeviece_type());
            }


            map.put(uniqueKey.toString(),templet);
        }
        String jsonString = JSON.toJSONString(map);
        System.out.println(jsonString);

//        String path = "msg_templet.json";
        String path = this.getClass().getClassLoader().getResource("msg_templet.json").getPath();

        //todo 此处生成json文件的路径为相对路径 处于target中的class包中
//        String path = MessageServiceImpl.class.getClassLoader().getResource("").getPath();
//        path +="msg_templet.json";
        writeToJson(path,jsonString);
        System.out.println("json文件路径："+path);

    }

    public void writeToJson(String filePath,String jsonString) throws IOException
    {
        File file = new File(filePath);
        char [] stack = new char[1024];
        int top=-1;

        String string = jsonString;

        StringBuffer sb = new StringBuffer();
        char [] charArray = string.toCharArray();
        for(int i=0;i<charArray.length;i++){
            char c= charArray[i];
            if ('{' == c || '[' == c) {
                stack[++top] = c;
                sb.append("\n"+charArray[i] + "\n");
                for (int j = 0; j <= top; j++) {
                    sb.append("\t");
                }
                continue;
            }
            if ((i + 1) <= (charArray.length - 1)) {
                char d = charArray[i+1];
                if ('}' == d || ']' == d) {
                    top--;
                    sb.append(charArray[i] + "\n");
                    for (int j = 0; j <= top; j++) {
                        sb.append("\t");
                    }
                    continue;
                }
            }
            if (',' == c) {
                sb.append(charArray[i] + "");
                for (int j = 0; j <= top; j++) {
                    sb.append("");
                }
                continue;
            }
            sb.append(c);
        }

        Writer write = new FileWriter(file);
        write.write(sb.toString());
        write.flush();
        write.close();
    }

    public static void main(String[] args) {
//        String path = MessageServiceImpl.class.getClassLoader().getResource("").getPath();
//        String path = System.getProperty("user.dir");

        File directory = new File("");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
            System.out.println(directory.getPath());//获取绝对路径
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(MessageServiceImpl.class.getResource("/"));
        System.out.println(MessageServiceImpl.class.getResource(""));
        System.out.println(MessageServiceImpl.class.getClassLoader().getResource(""));
        System.out.println(MessageServiceImpl.class.getClassLoader().getResource("/"));
    }
}
