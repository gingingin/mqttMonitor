package com.yzwy.infrastructure.message.model;

import com.yzwy.infrastructure.message.RadixUtil;

/**
 * Created by gin on 2017/6/9.
 */
public class LineFiPlugin extends BaseParam{
    /**
     * 家庭名称 20
     */
    private String homeName;

    /**
     * 家庭序列号 10
     */
    private String homeSerialNum;

    /**
     * 房间名字 10bytes
     */
    private String roomName;

    /**
     * 公网域名 32bytes
     */
    private String domainName = RadixUtil.stringParam2HexString("http://",32);
    /**
     * 公网ip 4bytes
     */
    private String domainIp;
    /**
     *mqtt中间件ip 4bytes
     */
    private String mqttBrokerIp;
    /**
     * 用户名 6bytes
     */
    private String userName;
    /**
     * 密码 6bytes
     */
    private String secret;
    /**
     *主云终端 1byte
     */
    private String mainCloudTerminal;
    /**
     * 2.4g频道号 1byte
     */
    private String channelOfTwoAndFourG;

    public LineFiPlugin() throws Exception {
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getHomeSerialNum() {
        return homeSerialNum;
    }

    public void setHomeSerialNum(String homeSerialNum) {
        this.homeSerialNum = homeSerialNum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainIp() {
        return domainIp;
    }

    public void setDomainIp(String domainIp) {
        this.domainIp = domainIp;
    }

    public String getMqttBrokerIp() {
        return mqttBrokerIp;
    }

    public void setMqttBrokerIp(String mqttBrokerIp) {
        this.mqttBrokerIp = mqttBrokerIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMainCloudTerminal() {
        return mainCloudTerminal;
    }

    public void setMainCloudTerminal(String mainCloudTerminal) {
        this.mainCloudTerminal = mainCloudTerminal;
    }

    public String getChannelOfTwoAndFourG() {
        return channelOfTwoAndFourG;
    }

    public void setChannelOfTwoAndFourG(String channelOfTwoAndFourG) {
        this.channelOfTwoAndFourG = channelOfTwoAndFourG;
    }
}
