package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class Data extends Application{ 
	private String userName, password, userMailbox, userClass, userPhone,temp,actName;
	public List<UserInfo> info = new ArrayList<UserInfo>(); 
	public void onCreate() { 
        super.onCreate(); 
    } 
	public void setInfo(List<UserInfo> i){
		this.info = i;
	}
	public void setUserName(String s){
		this.userName=s;
	}
	public void setPassword(String s){
		this.password=s;
	}
	public void setUserMailbox(String s){
		this.userMailbox=s;
	}
	public void setUserClass(String s){
		this.userClass=s;
	}
	public void setUserPhone(String s){
		this.userPhone=s;
	}
	public void setTemp(String s){
		this.temp=s;
	}
	public void setActName(String s){
		this.actName=s;
	}
	public List<UserInfo> getInfo(){
		return info;
	}
	public String getUserName(){
		return userName;
	}
	public String getPassword(){
		return password;
	}
	public String getUserMailbox(){
		return userMailbox;
	}
	public String getUserClass(){
		return userClass;
	}
	public String getUserPhone(){
		return userPhone;
	}
	public String getActName(){
		return actName;
	}
	public String getTemp(){
		return temp;
	}
}
