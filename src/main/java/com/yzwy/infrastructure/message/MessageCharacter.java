package com.yzwy.infrastructure.message;

/**
 * 报文特征值 通过读取特定位置获取到相关值 便于解析
 * Created by gin on 2017/6/1.
 */
public class MessageCharacter {


    /**
     * 功能码
     */
    private String functionCode;

    /**
     * 源终端类型
     */
    private String sourceTerminalType;
    /**
     * 源设备类型 -如terminalType为0x01 则 00 00 AA BB  --AA为设备类型码 BB为设备序号
     */
    private String sourceDeviceType;

    /**
     * 目标终端类型
     */
    private String destTerminalType;

    /**
     * 目标 设备类型 -如terminalType为0x01 则 00 00 AA BB  --AA为设备类型码 BB为设备序号
     */
    private String destDeviceType;


    /**
     * 会话id
     */
    private String sessionCode;

    /**
     * 报文长度
     */
    private String length;

    /**
     * 控制码（1byte 第8bit为发送／应答标志） 该码>= 0x80表示应答 其余为发送
     */
    private String controlCode;

    /**
     * 整包报文校验位
     */
    private String crc;

    public MessageCharacter() {

    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
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

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }
}
