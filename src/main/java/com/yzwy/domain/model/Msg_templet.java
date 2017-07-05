package com.yzwy.domain.model;

/**
 * Created by gin on 2017/6/1.
 */
public class Msg_templet {
    private Integer id;
    private String function_code;
    private String terminal_type;
    private String deviece_type;
    private String control_code;
    private String templet;
    private String templet_length;
    private String remark;
    private Integer qos;
    private String templet_isvar;
    private String templet_var;
    private String packing_serial;
    private Integer length;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFunction_code() {
        return function_code;
    }

    public void setFunction_code(String function_code) {
        this.function_code = function_code;
    }

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }

    public String getDeviece_type() {
        return deviece_type;
    }

    public void setDeviece_type(String deviece_type) {
        this.deviece_type = deviece_type;
    }

    public String getControl_code() {
        return control_code;
    }

    public void setControl_code(String control_code) {
        this.control_code = control_code;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public String getTemplet_length() {
        return templet_length;
    }

    public void setTemplet_length(String templet_length) {
        this.templet_length = templet_length;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getTemplet_isvar() {
        return templet_isvar;
    }

    public void setTemplet_isvar(String templet_isvar) {
        this.templet_isvar = templet_isvar;
    }

    public String getTemplet_var() {
        return templet_var;
    }

    public void setTemplet_var(String templet_var) {
        this.templet_var = templet_var;
    }

    public String getPacking_serial() {
        return packing_serial;
    }

    public void setPacking_serial(String packing_serial) {
        this.packing_serial = packing_serial;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}