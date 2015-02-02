package com.funtouch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TeamListInfo implements Serializable{
	private List<Team> TeamList;
	public TeamListInfo(){}
	public TeamListInfo(List<Team> TeamList){
		this.TeamList = TeamList;
	}
	public List<Team> getTeamList(){
		return TeamList;
	}  
}
