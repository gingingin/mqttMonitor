package com.yzwy.infrastructure.message.model;

/**
 * Created by gin on 2017/6/19.
 */
public class DeviceParam extends  BaseParam{

    /**
     * 关联传感器地址1 5byte
     */
    private String associatedSensor1;
    /**
     * 关联传感器地址2 5byte
     */
    private String associatedSensor2;
    /**
     * 阀值设置 20byte
     "
     byte0~1   人检无人关机时间
     byte2~5   照度阀值范围
     byte6~7   湿度阀值范围
     byte8~9   温度阀值范围
     byte10~11 空气阀值范围
     byte12~13 煤气阀值范围
     "
     */
    private String thresholdSet;
    /**
     * 红外参数  4byte
     */
    private String infraredParam;
    /**
     * 情景模式名字1 8byte
     */
    private String contextualModelName1;
    /**
     * 情景模式名字2 8
     */
    private String contextualModelName2;
    /**
     * 情景模式名字3
     */
    private String contextualModelName3;
    /**
     * 情景模式名字4
     */
    private String contextualModelName4;
    /**
     * 情景模式动作1  1开 2关
     */
    private String contextualModeAction1;
    /**
     * 情景模式动作2
     */
    private String contextualModeAction2;
    /**
     * 情景模式动作3
     */
    private String contextualModeAction3;
    /**
     * 情景模式动作4 1byte
     */
    private String contextualModeAction4;
    /**
     * 定时自动控制1 5byte
     */
    private String timerAutomaticControl1;
    /**
     * 定时自动控制2 5byte
     */
    private String timerAutomaticControl2;
    /**
     * 定时自动控制3 5byte
     */
    private String timerAutomaticControl3;
    /**
     * 关联终端地址 5byte
     */
    private String associatedTerminal;
    /**
     * 关联频道号 1byte
     */
    private String associatedChannelId;

    public String getAssociatedSensor1() {
        return associatedSensor1;
    }

    public void setAssociatedSensor1(String associatedSensor1) {
        this.associatedSensor1 = associatedSensor1;
    }

    public String getAssociatedSensor2() {
        return associatedSensor2;
    }

    public void setAssociatedSensor2(String associatedSensor2) {
        this.associatedSensor2 = associatedSensor2;
    }

    public String getThresholdSet() {
        return thresholdSet;
    }

    public void setThresholdSet(String thresholdSet) {
        this.thresholdSet = thresholdSet;
    }

    public String getInfraredParam() {
        return infraredParam;
    }

    public void setInfraredParam(String infraredParam) {
        this.infraredParam = infraredParam;
    }

    public String getContextualModelName1() {
        return contextualModelName1;
    }

    public void setContextualModelName1(String contextualModelName1) {
        this.contextualModelName1 = contextualModelName1;
    }

    public String getContextualModelName2() {
        return contextualModelName2;
    }

    public void setContextualModelName2(String contextualModelName2) {
        this.contextualModelName2 = contextualModelName2;
    }

    public String getContextualModelName3() {
        return contextualModelName3;
    }

    public void setContextualModelName3(String contextualModelName3) {
        this.contextualModelName3 = contextualModelName3;
    }

    public String getContextualModelName4() {
        return contextualModelName4;
    }

    public void setContextualModelName4(String contextualModelName4) {
        this.contextualModelName4 = contextualModelName4;
    }

    public String getContextualModeAction1() {
        return contextualModeAction1;
    }

    public void setContextualModeAction1(String contextualModeAction1) {
        this.contextualModeAction1 = contextualModeAction1;
    }

    public String getContextualModeAction2() {
        return contextualModeAction2;
    }

    public void setContextualModeAction2(String contextualModeAction2) {
        this.contextualModeAction2 = contextualModeAction2;
    }

    public String getContextualModeAction3() {
        return contextualModeAction3;
    }

    public void setContextualModeAction3(String contextualModeAction3) {
        this.contextualModeAction3 = contextualModeAction3;
    }

    public String getContextualModeAction4() {
        return contextualModeAction4;
    }

    public void setContextualModeAction4(String contextualModeAction4) {
        this.contextualModeAction4 = contextualModeAction4;
    }

    public String getTimerAutomaticControl1() {
        return timerAutomaticControl1;
    }

    public void setTimerAutomaticControl1(String timerAutomaticControl1) {
        this.timerAutomaticControl1 = timerAutomaticControl1;
    }

    public String getTimerAutomaticControl2() {
        return timerAutomaticControl2;
    }

    public void setTimerAutomaticControl2(String timerAutomaticControl2) {
        this.timerAutomaticControl2 = timerAutomaticControl2;
    }

    public String getTimerAutomaticControl3() {
        return timerAutomaticControl3;
    }

    public void setTimerAutomaticControl3(String timerAutomaticControl3) {
        this.timerAutomaticControl3 = timerAutomaticControl3;
    }

    public String getAssociatedTerminal() {
        return associatedTerminal;
    }

    public void setAssociatedTerminal(String associatedTerminal) {
        this.associatedTerminal = associatedTerminal;
    }

    public String getAssociatedChannelId() {
        return associatedChannelId;
    }

    public void setAssociatedChannelId(String associatedChannelId) {
        this.associatedChannelId = associatedChannelId;
    }
}
