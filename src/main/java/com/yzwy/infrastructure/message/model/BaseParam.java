package com.yzwy.infrastructure.message.model;


import com.yzwy.infrastructure.message.RadixUtil;
import com.yzwy.infrastructure.message.TimeUtil;

import java.sql.Time;

/**
 *  基本参数 这里填入的值默认是16进制字符串舍去0x 不足8bit的高位补0如：02
 *
 * Created by gin on 2017/6/1.
 */
public class BaseParam {
    //组装报文关键信息
    /**
     * 功能码
     */
    private String functionCode;

    /**
     * 目标终端类型
     */
    private String destTerminalType ;
    /**
     * 目标设备类型
     */
    private String destDeviceType;

    /**
     * 源地址中头一位
     */
    private String sourceTerminalType;
    /**
     * 源地址中 如果终端类型为01 则标示设备 如在00 00 aa bb中aa为设备类型序号
     */
    private String sourceDeviceType;
    /**
     * 控制位 由三部分组成
     */
    private String controlCode = "00"; //00表示单包下发组合
    /**
     * 控制位中 判断是终端响应-1 还是下发的报文-0
     */
    private String isResponse;
    /**
     * 控制位中 针对后续包 标示第几包
     */
    private String packageNo;
    /**
     * 控制位中 针对有后续包情况 最后一包该位为0
     */
    private String packageEnd;

    //报文框架基本要素
    /**
     * 目标地址
     */
    private String destAddress;
    /**
     * 源地址
     */
    private String sourceAddress;
    /**
     * 会话id 6bytes
     */
    private String sessionId = TimeUtil.uniqueTimeStr();

    /**
     * 用户手机号 6byte 默认FF FF FF FF FF FF表示无效手机号
     */
    private String userPhone = "FF FF FF FF FF FF";

    /**
     * 错误码 1byte 默认FF表示无效
     */
    private String errorCode="FF";

    /**
     * 时间戳
     */
    private String timeStamp = TimeUtil.unixTimeStamp();



    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getDestTerminalType() {
        return destTerminalType;
    }

    public void setDestTerminalType(String destTerminalType) {
        this.destTerminalType = destTerminalType;
    }

    public String getDestDeviceType() {
        return destDeviceType;
    }

    public void setDestDeviceType(String destDeviceType) {
        this.destDeviceType = destDeviceType;
    }

    public String getSourceTerminalType() {
        return sourceTerminalType;
    }

    public void setSourceTerminalType(String sourceTerminalType) {
        this.sourceTerminalType = sourceTerminalType;
    }

    public String getSourceDeviceType() {
        return sourceDeviceType;
    }

    public void setSourceDeviceType(String sourceDeviceType) {
        this.sourceDeviceType = sourceDeviceType;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public String getIsResponse() {
        return isResponse;
    }

    public void setIsResponse(String isResponse) {
        this.isResponse = isResponse;
    }

    public String getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String getPackageEnd() {
        return packageEnd;
    }

    public void setPackageEnd(String packageEnd) {
        this.packageEnd = packageEnd;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
