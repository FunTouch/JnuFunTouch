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
    private String code;
    private String act_id;
    private String user_id;
    
    public String getAct_id() {
    	return this.act_id;
    }
    public String getUser_id() {
    	return this.user_id;
    }
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
    public void setActor(String actor) {
    	this.actor = actor;
    }
    public void setCode(String code) {
    	this.code = code;
    }
    public void setAct_id(String id) {
    	this.act_id = id;
    }
    public void setUser_id(String id) {
    	this.user_id = id;
    }

}
