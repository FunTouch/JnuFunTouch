package com.funtouch;

import java.io.Serializable;

public class Code implements Serializable{
	private String code;
    private String status;
    private String value;
    private String act_id;
    private String info;
    private String name;
    
    public String getCode() {
    	return this.code;
    }
    public String getAct_id() {
    	return this.act_id;
    }
    public String getInfo() {
    	return this.info;
    }
    public String getName() {
    	return this.name;
    }
    public String getStatus() {
    	return this.status;
    }
    public String getValue() {
    	return this.value;
    }
    
    public void setCode(String s) {
    	this.code = s;
    }
    public void setAct_id(String s) {
    	this.act_id = s;
    }
    public void setInfo(String s) {
    	this.info = s;
    }
    public void setName(String s) {
    	this.name = s;
    }
    public void setStatus(String s) {
    	this.status = s;
    }
    public void setValue(String s) {
    	this.value = s;
    }

}
