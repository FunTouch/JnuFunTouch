package com.funtouch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EnrollListDetailsInfo implements Serializable{
	private List<Map<String, String>> EnrollDetails;
	public EnrollListDetailsInfo(){}
	public EnrollListDetailsInfo(List<Map<String, String>> EnrollDetails){
		this.EnrollDetails = EnrollDetails;
	}
	public List<Map<String, String>> getEnrollDetails(){
		return EnrollDetails;
	}  
}
