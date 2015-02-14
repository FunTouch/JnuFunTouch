package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class Cookie extends Application{ 
	private
	 static Cookie instance;

	public
	 static Cookie getInstance() {
	    return instance;
	}

	private String cookie;
	private String name;
	private String user_id;
	
	public List<UserInfo> info = new ArrayList<UserInfo>(); 
	public void onCreate() { 
        super.onCreate(); 
        instance = this;
    } 
	
	public void setInfo(List<UserInfo> i){
		this.info = i;
	}
	
	public void setCookie(String s){
		this.cookie=s;
	}
	public void setUser_id(String s){
		this.user_id=s;
	}
	public void setName(String s){
		this.name=s;
	}
	
	public List<UserInfo> getInfo(){
		return info;
	}
	public String getCookie(){
		return cookie;
	}
	public String getUser_id(){
		return user_id;
	}
	public String getName(){
		return name;
	}
}

