package com.funtouch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ActDetailsInfo implements Serializable{
	private List<Map<String, Object>> ActDetail;
	public ActDetailsInfo(){}
	public ActDetailsInfo(List<Map<String, Object>> ActDetail){
		this.ActDetail = ActDetail;
	}
	public List<Map<String, Object>> getActDetail(){
		return ActDetail;
	}  

}
