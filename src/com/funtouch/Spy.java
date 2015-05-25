package com.funtouch;

import java.io.Serializable;

public class Spy implements Serializable{
	private String common_phrase;
    private String total_common;
    private String total_spy;
    private int room_id;
    private String spy_phrase;
    private String code;
    private String isspy;
    private String phrase;
    private String commons;
    private String spys;
    
    public String getCommonPhrase() {
    	return this.common_phrase;
    }
    public String getTotalCommon() {
    	return this.total_common;
    }
    public String getTotalSpy() {
    	return this.total_spy;
    }
    public int getRoomId() {
    	return this.room_id;
    }
    public String getSpyPhrase() {
    	return this.spy_phrase;
    }
    public String getCode() {
    	return this.code;
    }
    public String getIsspy() {
    	return this.isspy;
    }
    public String getPhrase() {
    	return this.phrase;
    }
    public String getCommons() {
    	return this.commons;
    }
    public String getSpys() {
    	return this.spys;
    }
    
    public void setCommonPhrase(String s) {
    	this.common_phrase = s;
    }
    public void setTotalCommon(String s) {
    	this.total_common = s;
    }
    public void setTotalSpy(String s) {
    	this.total_spy = s;
    }
    public void setRoomId(int s) {
    	this.room_id = s;
    }
    public void setSpyPhrase(String s) {
    	this.spy_phrase = s;
    }
    public void setCode(String s) {
    	this.code = s;
    }
    public void setPhrase(String s) {
    	this.phrase = s;
    }
    public void setSpys(String s) {
    	this.spys = s;
    }
    public void setCommons(String s) {
    	this.commons = s;
    }
    public void setIsspy(String b) {
    	this.isspy = b;
    }
}
