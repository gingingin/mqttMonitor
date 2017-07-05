package com.yzwy.interfaces.facade.dto;

import java.util.List;

/**
 * Created by gin on 2017/6/2.
 */
public class MsgTempletJson {
    private String function_code;
    private String terminal_type;
    private String deviece_type;
    private String control_code;
    private List<String> templet;
    private List<String> templet_length;
    private String remark;
    private List<String> templet_isvar;
    private List<String> templet_var;
    private String length;

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

    public List<String> getTemplet() {
        return templet;
    }

    public void setTemplet(List<String> templet) {
        this.templet = templet;
    }

    public List<String> getTemplet_length() {
        return templet_length;
    }

    public void setTemplet_length(List<String> templet_length) {
        this.templet_length = templet_length;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getTemplet_isvar() {
        return templet_isvar;
    }

    public void setTemplet_isvar(List<String> templet_isvar) {
        this.templet_isvar = templet_isvar;
    }

    public List<String> getTemplet_var() {
        return templet_var;
    }

    public void setTemplet_var(List<String> templet_var) {
        this.templet_var = templet_var;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
