package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SeeVote extends MenuHavingActivity{
	private SimpleAdapter adapter;
	public Cookie application ; 
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
	private List<Team> listTeamList;
	private DataRetriever dataRetriever = new DataRetriever();
	private Map<String, Object> tmp = new HashMap<String, Object>();
	private String act_id, authority;
	String cookie = application.getInstance().getCookie();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		authority = intent1.getStringExtra("authority");
     	setContentView(R.layout.see_vote);
     	
     	ListView lsvVoteInfo = (ListView)findViewById(R.id.lsv_vote_info);
     	
     	if(authority.equals("admin"))
     		listTeamList = dataRetriever.seeVoteAdmin(cookie,act_id);
     	else if(authority.equals("user"))
     		listTeamList = dataRetriever.seeVoteUser(cookie,act_id);
     	
     	if(listTeamList.get(0).getCode().equals("200"))
		{
			showToast("获取投票信息成功");
			getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_vote_info_raw,
					new String[] {"team_name", "team_info", "ticket"},
					new int[] {R.id.team_name, R.id.team_info, R.id.ticket});
			lsvVoteInfo.setAdapter(adapter);
		}
     	else if(listTeamList.get(0).getCode().equals("null")||listTeamList.get(0).getCode().equals("431"))
		{
			showToast("尚无投票信息");
		}
     	
	}
     	
     	
     //提示类
    private void showToast(CharSequence msg) {
    	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    
  //获取投票数据
  	private void getData() {
  		listData.clear();
  		for (Iterator<Team> it=listTeamList.iterator(); it.hasNext(); ){
  			Map<String, Object> tmp = new HashMap<String, Object>();
  			Team tl = it.next();		
  			tmp.put("team_name", tl.getTeam_name());
  			tmp.put("team_info", tl.getTeam_info());
  			tmp.put("ticket", tl.getTicket());
  			listData.add(tmp);
  		}
  	}

}
