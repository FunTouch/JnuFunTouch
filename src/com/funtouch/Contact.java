package com.funtouch;

import java.io.Serializable;

public class Contact implements Serializable {
	private String name;
    private String phone;
    private String value;

    public String getName() {
    	return this.name;
    }
    public String getPhone() {
    	return this.phone;
    }
    
    public void setName(String s) {
    	this.name = s;
    }
    public void setPhone(String s) {
    	this.phone = s;
    }
}
