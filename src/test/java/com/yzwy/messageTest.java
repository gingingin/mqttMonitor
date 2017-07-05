package com.yzwy;

import com.yzwy.infrastructure.StringUtil;
import com.yzwy.infrastructure.message.MessageUtil;
import com.yzwy.infrastructure.message.RadixUtil;
import com.yzwy.infrastructure.message.model.DeviceParam;
import com.yzwy.infrastructure.message.model.FaceTerminal;
import com.yzwy.infrastructure.message.model.LineFiPlugin;
import com.yzwy.infrastructure.message.model.SmartPlugin;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by gin on 2017/6/2.
 */
public class messageTest {

    @Test
    public void makeTest(){
        SmartPlugin plugin = new SmartPlugin();
        plugin.setFunctionCode("01");
        plugin.setControlCode("00");
        plugin.setDestTerminalType("04");
        plugin.setDestAddress("04 00 00 00 01");
        plugin.setSourceAddress("C9 00 00 00 01");
        plugin.setSessionId("01 02 03 04 05 06");
        plugin.setOperateSecret("01 02 03 04 05 06");
        plugin.setChannelId("01");
        plugin.setNewSecret("FF FF FF FF FF FF");
        plugin.setMaxLoadCurrent("09 09");
        plugin.setOverCurrentPeriod("08 08");
        plugin.setMaxPower("07 07 07");
        plugin.setPowerLimitation("06 06");
        plugin.setWorkMode("05");


        try {
            //组装报文
            MessageUtil.makeMessage(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//AA 00 2D 00 01 04 00 00 00 01 C9 00 00 00 01 01 02 03 04 05 06 01 02 03 04 05 06 01 FF FF FF FF FF FF 09 09 08 08 07 07 07 06 06 05 82


    @Test
    public void makeLineFiTest() throws Exception {
        LineFiPlugin plugin = new LineFiPlugin();
        plugin.setFunctionCode("01");
        plugin.setControlCode("00");
        plugin.setDestTerminalType("03");
        plugin.setDestAddress("03 00 00 00 01");
        plugin.setSourceAddress("C9 00 00 00 01");
        plugin.setSessionId("01 02 03 04 05 06");
        plugin.setTimeStamp("00 16 D9 43");
        plugin.setHomeName(RadixUtil.stringParam2HexString("猜猜看",20));
        plugin.setHomeSerialNum(RadixUtil.stringParam2HexString("家庭序",10));
        plugin.setRoomName(RadixUtil.stringParam2HexString("房间名",10));
        plugin.setDomainName(RadixUtil.stringParam2HexString("interest.ren",32));
        //todo-自定义生成ip 4个字段的方法
        plugin.setDomainIp(RadixUtil.ipString2HexStr("192.168.1.101"));
        plugin.setMqttBrokerIp(RadixUtil.ipString2HexStr("192.168.1.101"));
        plugin.setUserName(RadixUtil.stringParam2HexString("用户",6));
        plugin.setSecret(RadixUtil.stringParam2HexString("密码",6));
        plugin.setMainCloudTerminal("00");
        plugin.setChannelOfTwoAndFourG("00");


        try {
            //组装报文
            String msg = MessageUtil.makeMessage(plugin);
            MessageUtil.analyzeMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void smartPluginTest(){
        SmartPlugin plugin = new SmartPlugin();
        plugin.setDestTerminalType("04");
        plugin.setControlCode("00");
        plugin.setFunctionCode("01");

        plugin.setDestAddress("04 00 00 00 01");
        plugin.setSourceAddress("C9 00 00 00 01");
        plugin.setSessionId(StringUtil.uniqueTimeStr());

        plugin.setOperateSecret("01 02 03 04 05 06");
        plugin.setChannelId("01");
        plugin.setNewSecret("FF FF FF FF FF FF");
        plugin.setMaxLoadCurrent("09 09");
        plugin.setOverCurrentPeriod("08 08");
        plugin.setMaxPower("07 07 07");
        plugin.setPowerLimitation("06 06");
        plugin.setWorkMode("05");
        plugin.setAlarmInterval("00 00");

        plugin.setUserPhone("01 53 08 02 48 38");
        plugin.setErrorCode("00");
        plugin.setTimeStamp("20 17 07 08");

        plugin.setContextualMode("00 00 00 00");

        //组装报文
        String message = null;
        try {
            message = MessageUtil.makeMessage(plugin);

            MessageUtil.analyzeMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deviceMsgTest() throws Exception {
        DeviceParam plugin = new DeviceParam();
        plugin.setDestTerminalType("01");
        plugin.setControlCode("00");
        plugin.setFunctionCode("01");
        plugin.setDestDeviceType("12");

        plugin.setUserPhone("01 53 08 02 48 38");
        plugin.setErrorCode("00");

        plugin.setDestAddress("01 00 00 12 01");
        plugin.setSourceAddress("C9 00 00 00 01");
        plugin.setSessionId(StringUtil.uniqueTimeStr());

        plugin.setAssociatedSensor1("01 02 03 04 05");
        plugin.setAssociatedSensor2("05 04 03 02 01");
        plugin.setThresholdSet(RadixUtil.stringParam2HexString("阀值设置",20));
        plugin.setInfraredParam("09 09 08 08");
        plugin.setContextualModelName1(RadixUtil.stringParam2HexString("回家模式",8));
        plugin.setContextualModelName2(RadixUtil.stringParam2HexString("离家模式",8));
        plugin.setContextualModelName3(RadixUtil.stringParam2HexString("什么模式",8));
        plugin.setContextualModelName4(RadixUtil.stringParam2HexString("随便模式",8));

        plugin.setContextualModeAction1("01");
        plugin.setContextualModeAction2("02");
        plugin.setContextualModeAction3("03");
        plugin.setContextualModeAction4("04");

        plugin.setTimerAutomaticControl1("01 FF FF FF FF");
        plugin.setTimerAutomaticControl2("01 FF FF FF FF");
        plugin.setTimerAutomaticControl3("01 FF FF FF FF");
        plugin.setAssociatedTerminal("AA AA AA AA AA");
        plugin.setAssociatedChannelId("EE");




        //组装报文
        String message = null;
        try {
            message = MessageUtil.makeMessage(plugin);

            MessageUtil.analyzeMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void analysisTest() throws Exception {
        String msg = "AA 00 8C 80 11 03 00 00 00 04 01 00 00 01 00 A8 0B 01 17 55 01 03 03 00 00 00 00 00 " +
                "FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF B3 F8 B7 BF B5 C6 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FF FF FF FF BB D8 BC D2 00 00 00 00 C0 EB BC D2 00 00 00 00 BB E1 D2 E9 00 00 00 00 00 00 00 00 00 00 00 00 02 02 02 02 02 00 00 00 00 02 00 00 00 00 02 00 00 00 00 04 00 00 00 04 00 83 ";
                     //解析报文
        MessageUtil.analyzeMessage(msg);
//        String crcM = "AA 00 crc";
//         String[] crc=MessageUtil.CRCCheck(crcM.split("\\s"));
//
//        System.out.println(crc[crc.length-1]);

    }

    @Test
    public void makePhotoTest() throws Exception {
        FaceTerminal face = new FaceTerminal();
        face.setFunctionCode("01");
        face.setControlCode("00");
        face.setDestTerminalType("01");
//        face.setDestAddress("13 00 00 13 01");
//        face.setSourceAddress("C9 00 00 00 01");
//
//        face.setMemberId(RadixUtil.int2HexString(9527,21));
//        face.setPhotoUrl1(RadixUtil.stringParam2HexString("http://interest.ren",80));
//
//        face.setPhotoUrl2(RadixUtil.stringParam2HexString("http://interest.ren",80));
//
//        face.setPhotoUrl3(RadixUtil.stringParam2HexString("http://interest.ren",80));



        try {
            //组装报文
           String message = MessageUtil.makeMessage(face);
            //解析
           MessageUtil.analyzeMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lineFiReadTest() throws Exception {
        LineFiPlugin face = new LineFiPlugin();
        face.setFunctionCode("11");
        face.setControlCode("00");
        face.setDestTerminalType("03");
        face.setDestAddress("03 00 00 00 01");
        face.setSourceAddress("C9 00 00 00 01");

//        face.setMemberId(RadixUtil.int2HexString(9527,21));
//        face.setPhotoUrl1(RadixUtil.stringParam2HexString("http://interest.ren",80));
//
//        face.setPhotoUrl2(RadixUtil.stringParam2HexString("http://interest.ren",80));
//
//        face.setPhotoUrl3(RadixUtil.stringParam2HexString("http://interest.ren",80));



        try {
            //组装报文
            String message = MessageUtil.makeMessage(face);
            //解析

            MessageUtil.analyzeMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void RadixUtilTest() throws Exception {

//        String hexStr = RadixUtil.int2HexString(99918,4);
//        System.out.println("hexStr:"+hexStr);
//        String result = RadixUtil.HexStr2NumericValue(hexStr);
//        System.out.println(result);

        String urlHex = null;
        try {
//            urlHex = RadixUtil.int2HexString(9527,2);
//           urlHex = RadixUtil.stringParam2HexString("情景模式",12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        urlHex = RadixUtil.stringParam2HexString("1234",4);
        System.out.println("HexStr:"+urlHex);
        String urlStri = RadixUtil.hexString2Param(urlHex);
        System.out.println("phone:"+urlStri);
    }

    @Test
    public void urlTest() throws Exception {
        String msg = "http://interest.com";//解析报文
        System.out.println("原字符串长度："+msg.getBytes().length);

        String hex = "00 01";
        System.out.println(RadixUtil.HexStr2NumericValue(hex));

//        System.out.println(StringUtil.HexStrToByte(after).length+" hex 2 string:"+ RadixUtil
//                .hexString2Param(after));

//        System.out.println(RadixUtil.int2HexString(9527,2));
//        System.out.println(RadixUtil.int2HexString(9527,4));
    }
    @Test
    public void filePathTest() throws Exception {
        File file = new File("/");

        String path = file.getPath();

        path = this.getClass().getResource("/").toString();
        path = this.getClass().getResource("").getPath();

        System.out.println(path);
    }


}
