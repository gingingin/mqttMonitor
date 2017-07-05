package com.yzwy.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gin on 2016/11/3.
 */
public class StringUtil {

//    private static final Logger LOG = LogManager.getLogger(StringUtil.class);

    /**
     * String json格式字符串转map
     */
    public static Map StringToMap(String str) throws Exception{
        Map<String,Object> map =  new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        map = objectMapper.readValue(str,Map.class);
//        System.out.println(map);
        return map;
    }

    /**
     * 日期转字符串类型
     * @param date
     * @param exp
     *            表达式如："yyyy-MM-dd" 也可以不传入默认方式为"yyyy-MM-dd"
     */
    public static String dateForString(Date date,String... exp){
        SimpleDateFormat simpleDateFormat = null;
        if(null != date){
            if(exp.length != 0){
                simpleDateFormat = new SimpleDateFormat(exp[0]);
            }else{
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
        }
        return simpleDateFormat.format(date);
    }

    public static String milliDateString(){
        String msg="";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss.SSS");
        msg+="["+sdf.format(date)+"]";
        return msg;
    }

    /**
     * 接收打印 byte->16进制字符串转换  与HexStrToByte需要配对使用 或者使用系统调用 string.getBytes() + new String(bytes[])
     * @param by
     * @return
     */
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

    public static boolean startWithBeginWord(byte[] by){
        //begin with "68"
//        StringBuilder sb = new StringBuilder(by.length);
        String sTemp = Integer.toHexString(0xFF & by[0]).trim();
        String funCode = Integer.toHexString(0xFF & by[3]).trim();
        //功能码20 为主动上报 可以排除
        if (sTemp.equals("68") && !funCode.equals("20")){

            return true;
        }

        return false;
    }

    public static String getMsgTimeStr(byte[] bytes){
        StringBuffer sb2 = new StringBuffer(6);
        String sTemp;
        byte[] by2 = new byte[6];

        System.arraycopy(bytes,6,by2,0,6);
        for(byte aaa: by2){
            sTemp = Integer.toHexString(0xFF &  aaa);
            if (sTemp.length() < 2)
                sb2.append(0);
            sb2.append(sTemp.toUpperCase()+" ");
        }

        return sb2.toString().trim();
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

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
//        LOG.info(s);
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
//                LOG.warn(e.getMessage());
//                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "gbk");
        } catch (Exception e1) {
//            LOG.warn(e1.getMessage());

//            e1.printStackTrace();
        }
        return s;
    }

    public static String combineHex(byte[] byteArray){
        int sum = 0;
        for (int i = 0;i < byteArray.length;i++){
            //高位在前
            sum += (byteArray[i] & 0xff) << 8*(byteArray.length-i-1);
        }
        return Integer.toString(sum) ;

    }

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

    /**
     * 判断空字符串
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     */
    public static boolean isEmpty(final CharSequence cs){
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断空白字符串
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }





}
