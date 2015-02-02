package com.funtouch;

import java.io.Serializable;

public class Team implements Serializable{
	private String team_name;
    private String team_info;
    private int ticket;
    private String vote_id;
    private String code;
    private String limit;
    
    public String getTeam_name() {
    	return this.team_name;
    }
    public String getTeam_info() {
    	return this.team_info;
    }
    public int getTicket() {
    	return this.ticket;
    }
    public String getVote_id() {
    	return this.vote_id;
    }
    public String getCode() {
    	return this.code;
    }
    public String getLimit() {
    	return this.limit;
    }
    
    public void setTeam_name(String name) {
    	this.team_name = name;
    }
    public void setTeam_info(String info) {
    	this.team_info = info;
    }
    public void setTicket(int ticket) {
    	this.ticket = ticket;
    }
    public void setVote_id(String id) {
    	this.vote_id = id;
    }
    public void setCode(String code) {
    	this.code = code;
    }
    public void setLimit(String limit) {
    	this.limit = limit;
    }
}
