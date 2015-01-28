package com.funtouch;

public class UserInfo {
	private String userName;
	private String password;
	private String userMailbox;
	private String userClass;
	private String userPhone;
	private String registOrgan;
	private String registActName;
	
	/*public UserInfo(){
		initialize("1","1","1","1","1","1","1");
	}
	public UserInfo(String u,String p,String m,String c,String ph,String ro,String ra){
		initialize(u,p,m,c,ph,ro,ra);
	}*/
	
	
	public void initialize(String u,String p,String m,String c,String ph,String ro,String ra){
		this.userName=u;
		this.password=p;
		this.userMailbox=m;
		this.userClass=c;
		this.userPhone=ph;
		this.registOrgan=ro;
		this.registActName=ra;
	}
	
	public String getName(){
		return this.userName;
	}
	public String getPassword(){
		return this.password;
	}
	public String getMailbox(){
		return this.userMailbox;
	}
	public String getUserClass(){
		return this.userClass;
	}
	public String getUserPhone(){
		return this.userPhone;
	}
	public String getRegistOrgan(){
		return this.registOrgan;
	}
	public String getRegistActName(){
		return this.registActName;
	}
}
