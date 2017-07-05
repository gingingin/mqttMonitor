package com.yzwy.domain.model;

/**
 * Created by gin on 2017/6/1.
 */
public class Properties {
    private Integer id;
    private String code;
    private String type;
    private Integer length;
    private String name;
    private String formate;
    private Integer is_var;
    private String default_field;
    private String remark;
    private Integer is_conversion;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormate() {
        return formate;
    }

    public void setFormate(String formate) {
        this.formate = formate;
    }

    public Integer getIs_var() {
        return is_var;
    }

    public void setIs_var(Integer is_var) {
        this.is_var = is_var;
    }

    public String getDefault_field() {
        return default_field;
    }

    public void setDefault_field(String default_field) {
        this.default_field = default_field;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIs_conversion() {
        return is_conversion;
    }

    public void setIs_conversion(Integer is_conversion) {
        this.is_conversion = is_conversion;
    }
}
