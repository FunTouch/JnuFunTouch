package com.funtouch;

import java.io.Serializable;
import java.util.List;

public class Speaker implements Serializable{
	private String name;
    private String info;
    private String time;
    private String place;
    private String type;
    private String org;
    private String actor;
    private String limit;
    private String code;
    
    public String getName() {
    	return this.name;
    }
    public String getInfo() {
    	return this.info;
    }
    public String getTime() {
    	return this.time;
    }
    public String getPlace() {
    	return this.place;
    }
    public String getType() {
    	return this.type;
    }
    public String getOrg() {
    	return this.org;
    }
    public String getActor() {
    	return this.actor;
    }
    public String getLimit() {
    	return this.limit;
    }
    public String getCode() {
    	return this.code;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    public void setTime(String time) {
    	this.time = time;
    }
    public void setPlace(String place) {
    	this.place = place;
    }
    public void setInfo(String info) {
    	this.info = info;
    }
    public void setType(String type) {
    	this.type = type;
    }
    public void setOrg(String org) {
    	this.org = org;
    }
    public void setLimit(String limit) {
    	this.limit = limit;
    }
    public void setActor(String actor) {
    	this.actor = actor;
    }
    public void setCode(String code) {
    	this.code = code;
    }

}
