package com.yzwy.infrastructure.message.model;

/**
 * Created by gin on 2017/6/2.
 */
public class SmartPlugin extends BaseParam {
    /**
     * "通道号
     1byte"
     */
    private String channelId;
    /**
     * "操作密码
     4bytes"
     */
    private String operateSecret;
    /**
     * "新密码
     4bytes"
     */
    private String newSecret;
    /**
     * "最大负载电流(单位0.01A)
     2bytes"
     */
    private String maxLoadCurrent;
    /**
     * "过流持续时间（单位秒）
     2bytes"
     */
    private String overCurrentPeriod;
    /**
     * "最大设备功率(单位1W)
     2bytes"
     */
    private String maxPower;
    /**
     * "功率变化值(单位1W)
     2bytes"
     */
    private String powerLimitation;
    /**
     * 工作模式 1byte
     */
    private String workMode;
    /**
     * 情景模式 4byte
     */
    private String contextualMode;
    /**
     * 报警时间间隔 2byte
     */
    private String alarmInterval;
    /**
     * 命令ID 1byte
     */
    private String commandId;
    /**
     * "负载过流报警(单位0.01A)
     2bytes"
     */
    private String overCurrentAlarm;
    /**
     * "超功率报警(单位1W)
     2bytes"
     */
    private String overPowerAlarm;
    /**
     * "报警时间
     4bytes"
     */
    private String alarmTime;
    /**
     * "MAC地址
     5bytes"
     */
    private String macAddress;
    /**
     *"开关次数
     2bytes"
     */
    private String switchTimes;
    /**
     *"开关状态（0x01开0x02关）
     1byte"
     */
    private String switchStatus;
    /**
     *"电量(单位度)
     3bytes"
     */
    private String powerConsumption;
    /**
     *"电压（单位0.1V）
     2bytes"
     */
    private String voltage;
    /**
     *"电流(单位0.01A)
     2bytes"
     */
    private String current;
    /**
     "功率(单位1W)
     2bytes"
     */
    private String power;
    /**
     "运行时间(单位分)
     2bytes"
     */
    private String workTimeCount;



    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getOperateSecret() {
        return operateSecret;
    }

    public void setOperateSecret(String operateSecret) {
        this.operateSecret = operateSecret;
    }

    public String getNewSecret() {
        return newSecret;
    }

    public void setNewSecret(String newSecret) {
        this.newSecret = newSecret;
    }

    public String getMaxLoadCurrent() {
        return maxLoadCurrent;
    }

    public void setMaxLoadCurrent(String maxLoadCurrent) {
        this.maxLoadCurrent = maxLoadCurrent;
    }

    public String getOverCurrentPeriod() {
        return overCurrentPeriod;
    }

    public void setOverCurrentPeriod(String overCurrentPeriod) {
        this.overCurrentPeriod = overCurrentPeriod;
    }

    public String getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(String maxPower) {
        this.maxPower = maxPower;
    }

    public String getPowerLimitation() {
        return powerLimitation;
    }

    public void setPowerLimitation(String powerLimitation) {
        this.powerLimitation = powerLimitation;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getContextualMode() {
        return contextualMode;
    }

    public void setContextualMode(String contextualMode) {
        this.contextualMode = contextualMode;
    }

    public String getAlarmInterval() {
        return alarmInterval;
    }

    public void setAlarmInterval(String alarmInterval) {
        this.alarmInterval = alarmInterval;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getOverCurrentAlarm() {
        return overCurrentAlarm;
    }

    public void setOverCurrentAlarm(String overCurrentAlarm) {
        this.overCurrentAlarm = overCurrentAlarm;
    }

    public String getOverPowerAlarm() {
        return overPowerAlarm;
    }

    public void setOverPowerAlarm(String overPowerAlarm) {
        this.overPowerAlarm = overPowerAlarm;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSwitchTimes() {
        return switchTimes;
    }

    public void setSwitchTimes(String switchTimes) {
        this.switchTimes = switchTimes;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(String switchStatus) {
        this.switchStatus = switchStatus;
    }

    public String getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(String powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getWorkTimeCount() {
        return workTimeCount;
    }

    public void setWorkTimeCount(String workTimeCount) {
        this.workTimeCount = workTimeCount;
    }


}
