package com.yzwy.infrastructure.message;

import java.io.UnsupportedEncodingException;

/**
 * Created by gin on 2017/6/1.
 */
public class RadixUtil {

//    CRC8校验算法：X^8 + X^2 + X^1 + 1
static byte[]   CRC8_TAB = {
        (byte)0x00,(byte)0x07,(byte)0x0E,(byte)0x09,(byte)0x1C,(byte)0x1B,(byte)0x12,(byte)0x15,(byte)0x38,(byte)0x3F,(byte)0x36,(byte)0x31,(byte)0x24,(byte)0x23,(byte)0x2A,(byte)0x2D,
        (byte)0x70,(byte)0x77,(byte)0x7E,(byte)0x79,(byte)0x6C,(byte)0x6B,(byte)0x62,(byte)0x65,(byte)0x48,(byte)0x4F,(byte)0x46,(byte)0x41,(byte)0x54,(byte)0x53,(byte)0x5A,(byte)0x5D,
        (byte)0xE0,(byte)0xE7,(byte)0xEE,(byte)0xE9,(byte)0xFC,(byte)0xFB,(byte)0xF2,(byte)0xF5,(byte)0xD8,(byte)0xDF,(byte)0xD6,(byte)0xD1,(byte)0xC4,(byte)0xC3,(byte)0xCA,(byte)0xCD,
        (byte)0x90,(byte)0x97,(byte)0x9E,(byte)0x99,(byte)0x8C,(byte)0x8B,(byte)0x82,(byte)0x85,(byte)0xA8,(byte)0xAF,(byte)0xA6,(byte)0xA1,(byte)0xB4,(byte)0xB3,(byte)0xBA,(byte)0xBD,
        (byte)0xC7,(byte)0xC0,(byte)0xC9,(byte)0xCE,(byte)0xDB,(byte)0xDC,(byte)0xD5,(byte)0xD2,(byte)0xFF,(byte)0xF8,(byte)0xF1,(byte)0xF6,(byte)0xE3,(byte)0xE4,(byte)0xED,(byte)0xEA,
        (byte)0xB7,(byte)0xB0,(byte)0xB9,(byte)0xBE,(byte)0xAB,(byte)0xAC,(byte)0xA5,(byte)0xA2,(byte)0x8F,(byte)0x88,(byte)0x81,(byte)0x86,(byte)0x93,(byte)0x94,(byte)0x9D,(byte)0x9A,
        (byte)0x27,(byte)0x20,(byte)0x29,(byte)0x2E,(byte)0x3B,(byte)0x3C,(byte)0x35,(byte)0x32,(byte)0x1F,(byte)0x18,(byte)0x11,(byte)0x16,(byte)0x03,(byte)0x04,(byte)0x0D,(byte)0x0A,
        (byte)0x57,(byte)0x50,(byte)0x59,(byte)0x5E,(byte)0x4B,(byte)0x4C,(byte)0x45,(byte)0x42,(byte)0x6F,(byte)0x68,(byte)0x61,(byte)0x66,(byte)0x73,(byte)0x74,(byte)0x7D,(byte)0x7A,
        (byte)0x89,(byte)0x8E,(byte)0x87,(byte)0x80,(byte)0x95,(byte)0x92,(byte)0x9B,(byte)0x9C,(byte)0xB1,(byte)0xB6,(byte)0xBF,(byte)0xB8,(byte)0xAD,(byte)0xAA,(byte)0xA3,(byte)0xA4,
        (byte)0xF9,(byte)0xFE,(byte)0xF7,(byte)0xF0,(byte)0xE5,(byte)0xE2,(byte)0xEB,(byte)0xEC,(byte)0xC1,(byte)0xC6,(byte)0xCF,(byte)0xC8,(byte)0xDD,(byte)0xDA,(byte)0xD3,(byte)0xD4,
        (byte)0x69,(byte)0x6E,(byte)0x67,(byte)0x60,(byte)0x75,(byte)0x72,(byte)0x7B,(byte)0x7C,(byte)0x51,(byte)0x56,(byte)0x5F,(byte)0x58,(byte)0x4D,(byte)0x4A,(byte)0x43,(byte)0x44,
        (byte)0x19,(byte)0x1E,(byte)0x17,(byte)0x10,(byte)0x05,(byte)0x02,(byte)0x0B,(byte)0x0C,(byte)0x21,(byte)0x26,(byte)0x2F,(byte)0x28,(byte)0x3D,(byte)0x3A,(byte)0x33,(byte)0x34,
        (byte)0x4E,(byte)0x49,(byte)0x40,(byte)0x47,(byte)0x52,(byte)0x55,(byte)0x5C,(byte)0x5B,(byte)0x76,(byte)0x71,(byte)0x78,(byte)0x7F,(byte)0x6A,(byte)0x6D,(byte)0x64,(byte)0x63,
        (byte)0x3E,(byte)0x39,(byte)0x30,(byte)0x37,(byte)0x22,(byte)0x25,(byte)0x2C,(byte)0x2B,(byte)0x06,(byte)0x01,(byte)0x08,(byte)0x0F,(byte)0x1A,(byte)0x1D,(byte)0x14,(byte)0x13,
        (byte)0xAE,(byte)0xA9,(byte)0xA0,(byte)0xA7,(byte)0xB2,(byte)0xB5,(byte)0xBC,(byte)0xBB,(byte)0x96,(byte)0x91,(byte)0x98,(byte)0x9F,(byte)0x8A,(byte)0x8D,(byte)0x84,(byte)0x83,
        (byte)0xDE,(byte)0xD9,(byte)0xD0,(byte)0xD7,(byte)0xC2,(byte)0xC5,(byte)0xCC,(byte)0xCB,(byte)0xE6,(byte)0xE1,(byte)0xE8,(byte)0xEF,(byte)0xFA,(byte)0xFD,(byte)0xF4,(byte)0xF3};

//uint8_t crc

    //CRC-8/MAXIM
//    static byte[] crc8_tab = { (byte) 0, (byte) 94, (byte) 188, (byte) 226, (byte) 97, (byte) 63, (byte) 221, (byte) 131, (byte) 194, (byte) 156, (byte) 126, (byte) 32, (byte) 163, (byte) 253, (byte) 31, (byte) 65, (byte) 157, (byte) 195, (byte) 33, (byte) 127, (byte) 252, (byte) 162, (byte) 64, (byte) 30, (byte) 95, (byte) 1, (byte) 227, (byte) 189, (byte) 62, (byte) 96, (byte) 130, (byte) 220, (byte) 35, (byte) 125, (byte) 159, (byte) 193, (byte) 66, (byte) 28, (byte) 254, (byte) 160, (byte) 225, (byte) 191, (byte) 93, (byte) 3, (byte) 128, (byte) 222, (byte) 60, (byte) 98, (byte) 190, (byte) 224, (byte) 2, (byte) 92, (byte) 223, (byte) 129, (byte) 99, (byte) 61, (byte) 124, (byte) 34, (byte) 192, (byte) 158, (byte) 29, (byte) 67, (byte) 161, (byte) 255, (byte) 70, (byte) 24,
//            (byte) 250, (byte) 164, (byte) 39, (byte) 121, (byte) 155, (byte) 197, (byte) 132, (byte) 218, (byte) 56, (byte) 102, (byte) 229, (byte) 187, (byte) 89, (byte) 7, (byte) 219, (byte) 133, (byte) 103, (byte) 57, (byte) 186, (byte) 228, (byte) 6, (byte) 88, (byte) 25, (byte) 71, (byte) 165, (byte) 251, (byte) 120, (byte) 38, (byte) 196, (byte) 154, (byte) 101, (byte) 59, (byte) 217, (byte) 135, (byte) 4, (byte) 90, (byte) 184, (byte) 230, (byte) 167, (byte) 249, (byte) 27, (byte) 69, (byte) 198, (byte) 152, (byte) 122, (byte) 36, (byte) 248, (byte) 166, (byte) 68, (byte) 26, (byte) 153, (byte) 199, (byte) 37, (byte) 123, (byte) 58, (byte) 100, (byte) 134, (byte) 216, (byte) 91, (byte) 5, (byte) 231, (byte) 185, (byte) 140, (byte) 210, (byte) 48, (byte) 110, (byte) 237,
//            (byte) 179, (byte) 81, (byte) 15, (byte) 78, (byte) 16, (byte) 242, (byte) 172, (byte) 47, (byte) 113, (byte) 147, (byte) 205, (byte) 17, (byte) 79, (byte) 173, (byte) 243, (byte) 112, (byte) 46, (byte) 204, (byte) 146, (byte) 211, (byte) 141, (byte) 111, (byte) 49, (byte) 178, (byte) 236, (byte) 14, (byte) 80, (byte) 175, (byte) 241, (byte) 19, (byte) 77, (byte) 206, (byte) 144, (byte) 114, (byte) 44, (byte) 109, (byte) 51, (byte) 209, (byte) 143, (byte) 12, (byte) 82, (byte) 176, (byte) 238, (byte) 50, (byte) 108, (byte) 142, (byte) 208, (byte) 83, (byte) 13, (byte) 239, (byte) 177, (byte) 240, (byte) 174, (byte) 76, (byte) 18, (byte) 145, (byte) 207, (byte) 45, (byte) 115, (byte) 202, (byte) 148, (byte) 118, (byte) 40, (byte) 171, (byte) 245, (byte) 23, (byte) 73, (byte) 8,
//            (byte) 86, (byte) 180, (byte) 234, (byte) 105, (byte) 55, (byte) 213, (byte) 139, (byte) 87, (byte) 9, (byte) 235, (byte) 181, (byte) 54, (byte) 104, (byte) 138, (byte) 212, (byte) 149, (byte) 203, (byte) 41, (byte) 119, (byte) 244, (byte) 170, (byte) 72, (byte) 22, (byte) 233, (byte) 183, (byte) 85, (byte) 11, (byte) 136, (byte) 214, (byte) 52, (byte) 106, (byte) 43, (byte) 117, (byte) 151, (byte) 201, (byte) 74, (byte) 20, (byte) 246, (byte) 168, (byte) 116, (byte) 42, (byte) 200, (byte) 150, (byte) 21, (byte) 75, (byte) 169, (byte) 247, (byte) 182, (byte) 232, (byte) 10, (byte) 84, (byte) 215, (byte) 137, (byte) 107, 53 };


    public static int FindCRC(byte[] data){
        int CRC=0;
        int genPoly = 0X00;
        for(int i=0;i<data.length; i++){
            CRC ^= data[i];
            for(int j=0;j<8;j++){
                if((CRC & 0x80) != 0){
                    CRC = (CRC << 1) ^ genPoly;
                }else{
                    CRC <<= 1;
                }
            }
        }
        CRC &= 0xff;//保证CRC余码输出为2字节。
        return CRC;
    }


    public static byte crc8(byte[] msg_ptr) {
        int x;
        int index;
        byte xorResult;

        xorResult = 0x00;

        for (x = 0; x < msg_ptr.length; x++) {
            index = ((xorResult ^ (msg_ptr[x])) & 0x00ff);
            xorResult = CRC8_TAB[index];
        }

        return (byte) ~xorResult;
    }

    public static String getCRCStrByHexString(String[] hexStrArr){
//        int crc2 = byteToInt(calcCrc8(HexStrArrToByteArr(hexStrArr)));
        int crc2 = byteToInt(crc8(HexStrArrToByteArr(hexStrArr)));

        String crc16 = Integer.toHexString(crc2);//把10进制的结果转化为16进制
        //如果想要保证校验码必须为2位，可以先判断结果是否比16小，如果比16小，可以在16进制的结果前面加0
        if(crc2 < 16 ){
            crc16 = "0"+crc16;
        }
        return crc16.toUpperCase();
    }

    public static byte[] HexStrArrToByteArr(String[] hexArr){
        byte[] buff = new byte[hexArr.length];

        for (int i = 0; i < hexArr.length; i++)
        {
            buff[i] =(byte)Integer.parseInt(hexArr[i], 16);
        }
        return buff;
    }

    public static byte[] HexStrToByte(String hexstring)
    {
        //去除前后空格
        String[] tmpary = hexstring.trim().split(" ");

        byte[] buff = new byte[tmpary.length];

        for (int i = 0; i < tmpary.length; i++)
        {
            buff[i] =(byte)Integer.parseInt(tmpary[i], 16);
        }
        return buff;
    }

    //byte 与 int 的相互转换
    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    //byte 数组与 int 的相互转换
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static String printfBytes(byte[] by) {
        StringBuffer sb2 = new StringBuffer(by.length);
        String sTemp;
        for(byte aaa: by){
            sTemp = Integer.toHexString(0xFF &  aaa);
            if (sTemp.length() < 2)
                sb2.append(0);
            sb2.append(sTemp.toUpperCase()+" ");
        }

        return sb2.toString();
    }

    /**
     * 计算数组的CRC8校验值
     *
     * @param data
     *            需要计算的数组
     * @return CRC8校验值
     */
    public static byte calcCrc8(byte[] data) {
        return calcCrc8(data, 0, data.length, (byte) 0);
    }

    /**
     * 计算CRC8校验值
     *
     * @param data
     *            数据
     * @param offset
     *            起始位置
     * @param len
     *            长度
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len) {
        return calcCrc8(data, offset, len, (byte) 0);
    }

    /**
     * 计算CRC8校验值
     *
     * @param data
     *            数据
     * @param offset
     *            起始位置
     * @param len
     *            长度
     * @param preval
     *            之前的校验值
     * @return 校验值
     */
    public static byte calcCrc8(byte[] data, int offset, int len, byte preval) {
        byte ret = preval;
        for (int i = offset; i < (offset + len); ++i) {
//            ret = crc8_tab[(0x00ff & (ret ^ data[i]))];
            ret = CRC8_TAB[(0x00ff & (ret ^ data[i]))];
        }
        return ret;
    }

    /**
     * 接收打印 byte->16进制字符串转换  与HexStrToByte需要配对使用 或者使用系统调用 string.getBytes() + new String(bytes[])
     * @param by
     * @return
     */
    public static String bytesToHexStr(byte[] by) {
        StringBuffer sb2 = new StringBuffer(by.length);
        String sTemp;
        for(byte aaa: by){
            sTemp = Integer.toHexString(0xFF &  aaa);
            if (sTemp.length() < 2)
                sb2.append(0);
            sb2.append(sTemp.toUpperCase()+" ");
        }

        return sb2.toString();
    }

    public static String stringParam2HexString(String param,int length) throws Exception {
        //字符串 默认使用"00"占位符
        return param2HexString(param,length,"00");
    }

    /**
     * 填入参数转换 --字符串参数
     * @param param 入参
     * @param lenth 该参数规定的长度
     * @param placeHolder 不足该长度的以该占位符补足 如"00"--用于字符串 或"FF"--用于默认无效值
     * @return
     */
    public static String param2HexString(String param,int lenth,String placeHolder) throws Exception {
        StringBuilder hexStr = new StringBuilder();
        hexStr.append(bytesToHexStr(param.trim().getBytes("GB2312")).trim());
        Integer diff = lenth - param.trim().getBytes("GB2312").length;
        if (diff >= 0){
            //param长度小于目标长度length
            for (int i = 0; i < diff; i++) {
                hexStr.append(" "+placeHolder);
            }

        }else {
            throw new Exception("字符串转换入参:["+param+"] 长度:"+param.trim().getBytes("GB2312").length+" 超过需要的长度:"+lenth);
        }
        return hexStr.toString();
    }

    /**
     * 读取16进制字符串后转换为--字符串参数
     * @param string
     * @return
     */
    public static String hexString2Param(String string){
        try {
            return new String(HexStrToByte(string),"GB2312").trim();
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
        return null;
    }

    /**
     * 整数转换为16进制字符串 不足位数的前补00 int不能超过4的length
     * @param inputInt
     * @param length
     * @return
     */
    public static String int2HexString(int inputInt,int length){
        String hex = "";
        for (int i = 0; i < length; i++) {
            String h = Integer.toString((inputInt >> 8* (length-i-1)) & 0xff, 16);
            if((h.length() & 0x01) == 1)//奇数长度前面补0
                h = '0' + h;
            hex +=" "+h;
        }
        return hex.trim().toUpperCase();
    }

    /**
     * 整数转换为16进制字符串 不足位数的前补00  long型转换
     * @param inputLong
     * @param length
     * @return
     */
    public static String long2HexString(long inputLong,int length){
        String hex = "";
        for (int i = 0; i < length; i++) {
            String h = Long.toString((inputLong >> 8* (length-i-1)) & 0xff, 16);
            if((h.length() & 0x01) == 1)//奇数长度前面补0
                h = '0' + h;
            hex +=" "+h;
        }
        return hex.trim().toUpperCase();
    }


    /**
     * 整数串分割为字符串  电话号码处理方式
     * @param integerStr
     * @return
     */
    public static String splitStringToHexString(String integerStr){
        String hex = "";
        if (integerStr.length()%2!=0) {
            //奇数
            integerStr = "0" + integerStr;
        }
        for (int i = 0; i < integerStr.length()-1; i=i+2) {
            hex += " "+integerStr.substring(i,i+2);
        }
        return hex.trim();
    }

    /**
     * 报文不转换直接替换空格合并 用于电话号码
     * @param hexString
     * @return
     */
    public static String combineStringWithHexString(String hexString){
        return hexString.replace(" ","");
    }

    /**
     *  16进制转10进制读数--数字型参数
     */
    public static String HexStr2NumericValue(String str){
        str = String.valueOf(Integer.parseInt(str.replaceAll(" ",""),16));
        return str;
    }

    /**
     *  16进制转10进制读数--数字型参数 长整形
     */
    public static String HexStr2LongValue(String str){
        str = String.valueOf(Long.parseLong(str.replaceAll(" ",""),16));
        return str;
    }

    /**
     * ip地址转换 入参为带点的ip字符串 例如"192.168.1.1"
     * @param ipStr
     * @return
     */
    public static String ipString2HexStr(String ipStr){
        StringBuilder hexStr = new StringBuilder();
        String[] ipArr = ipStr.split("\\.");
        for (int i = 0; i < ipArr.length; i++) {
            hexStr.append(" "+int2HexString(Integer.parseInt(ipArr[i]),1));
        }
        return hexStr.toString().trim();
    }

    /**
     * 把16进制字符串转换为ip地址字符串
     * @param hexStr
     * @return
     */
    public static String hexStr2IpAddress(String  hexStr){
        StringBuilder ipAddress = new StringBuilder();
        String[] hexArr = hexStr.split("\\s+");
        for (int i = 0; i < hexArr.length; i++) {
            if (i == 0){
                ipAddress.append(""+HexStr2NumericValue(hexArr[i]));
            }else {
                ipAddress.append("."+HexStr2NumericValue(hexArr[i]));
            }
        }
        return ipAddress.toString().trim();
    }

    public static String getPackageCount(String controCode){
        Integer cout = Integer.parseInt(controCode,16) & 0x3F;  //后6包
        return String.valueOf(cout);
    }


    public static void main(String[] args) {
        String result =getPackageCount("81");
        System.out.println(result);
//        int crc2 = byteToInt(calcCrc8(HexStrToByte("16 22")));
//
//        String crc16 =  Integer.toHexString(crc2);//把10进制的结果转化为16进制
//        //如果想要保证校验码必须为2位，可以先判断结果是否比16小，如果比16小，可以在16进制的结果前面加0
//        if(crc2 < 16 ){
//            crc16 = "0"+crc16;
//        }

//        System.out.println(getCRCStrByHexString("16 22"));
    }
}
