package com.yzwy;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gin on 2017/6/2.
 */
public class StringUtil {


    /**
     * byte转16进制字符串
     * @param by
     * @return
     */
    public static String bytes2HexString(byte[] by) {
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
     * 生成6bytes sessionId使用 如： 25 57 01 11 21 48
     * @return
     */
    public static String uniqueTimeStr(){
        SimpleDateFormat format =new SimpleDateFormat("HH mm ss");
        Date date = new Date();
        String nTimeStr = format.format(date);

        // nano
        long nano = System.nanoTime();
        Integer last5 = new Long(nano%65535).intValue() ;
        String nanoStr  = intStrToHexString(last5,2);

        //线程id
        String thId = intStrToHexString(new  Long(Thread.currentThread().getId()).intValue() ,1)  ;

        return nanoStr+" "+thId+" "+nTimeStr;
    }

    /**
     * 16进制字符串转换为byte[] 入参例如：25 57 01 11 21 48 中间以空格分割
     * @param hexstring
     * @return
     */
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

    /**
     * int转16进制串
     * @return
     */
    public static String intStrToHexString(int inputInt,int length){
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
     * 加和16进制字符串数组 返回一个16进制字符串 用于做校验和
     * @param inputArr
     * @return
     */
    public static String sumHexString(String[] inputArr){
        int sum = 0;
        for (String hexStr : inputArr){
            sum += Integer.parseInt(hexStr,16);
        }
        String hexStr = Integer.toString(sum & 0xff,16);
        if ((hexStr.length() & 0x01) == 1){
            hexStr = '0' + hexStr;
        }
        return hexStr.toUpperCase();
    }
}
