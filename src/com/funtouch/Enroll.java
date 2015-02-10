package com.funtouch;

import java.io.Serializable;

public class Enroll implements Serializable{
	private String num;
    private String code;
    private String limit;
    private String grade;
    private String phone;
    private String name;
    private String sno;
    private String mailbox;
    private String qq;

    public String getName() {
    	return this.name;
    }
    public String getNum() {
    	return this.num;
    }
    public String getCode() {
    	return this.code;
    }
    public String getLimit() {
    	return this.limit;
    }
    public String getGrade() {
    	return this.grade;
    }
    public String getPhone() {
    	return this.phone;
    }
    public String getSno() {
    	return this.sno;
    }
    public String getMailbox() {
    	return this.mailbox;
    }
    public String getQQ() {
    	return this.qq;
    }
    
    public void setName(String s) {
    	this.name = s;
    }
    public void setSno(String s) {
    	this.sno = s;
    }
    public void setPhone(String s) {
    	this.phone = s;
    }
    public void setNum(String s) {
    	this.num = s;
    }
    public void setCode(String s) {
    	this.code = s;
    }
    public void setLimit(String s) {
    	this.limit = s;
    }
    public void setGrade(String s) {
    	this.grade = s;
    }
    public void setMailbox(String s) {
    	this.mailbox = s;
    }
    public void setQQ(String s) {
    	this.qq = s;
    }
    
}
