package com.funtouch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VoteOL extends MenuHavingActivity{
	public Cookie application ; 
	private SimpleAdapter adapter;
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> VoteDetail = new ArrayList<Map<String, String>>();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Act> listAct;
	private String act_id;
	String cookie = application.getInstance().getCookie();
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_ol);
        
        ListView lsvActInfo = (ListView)findViewById(R.id.lsv_vote_act);
        listAct = dataRetriever.getVoteAct(cookie);
        
        if(listAct.get(0).getCode().equals("200"))
        {
        	showToast("获取活动列表成功");
        	getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_vote_act_raw,
					new String[] {"name", "time", "info", "limit","rest"},
					new int[] {R.id.vote_act_name,R.id.vote_act_time, R.id.vote_act_info, R.id.vote_act_limit,R.id.vote_act_rest});
			lsvActInfo.setAdapter(adapter);
			
			lsvActInfo.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view,  
					     int position, long id) {
					ListView listView = (ListView)parent; 
					VoteDetail.clear();
					HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
					String name = map.get("name");
					for (Iterator<Act> it=listAct.iterator(); it.hasNext(); )
				    {	    	
				    	Act act = it.next();
				    	if(name.equals(act.getName()))
				    	{
				    		tmp.put("name", act.getName());
							tmp.put("info", act.getInfo());
							tmp.put("limit", act.getVoteLimit());
							tmp.put("time", act.getTime());
							tmp.put("act_id", act.getAct_id());
							tmp.put("place", act.getPlace());
							tmp.put("type", act.getType());
							tmp.put("org", act.getOrg());
							tmp.put("rest", Integer.toString(act.getRest()));
							tmp.put("actor", act.getActor());
							VoteDetail.add(tmp);
							
							DetailsInfo info1 = new DetailsInfo(VoteDetail);		
							List<DetailsInfo> objectList = new ArrayList<DetailsInfo>();
							objectList.add(info1);	
											
				    		Intent intent = new Intent();
							intent.setClass(VoteOL.this,VoteOLDetail.class);
							intent.putExtra("act_id", act.getAct_id());
							intent.putExtra("ListObject", (Serializable) objectList);
							startActivity(intent);
							break;
				    	}
				    }
				}
				});
        }
        
        
	}
	
	//获取报名列表数据
		private void getData() {
			listData.clear();
			for (Iterator<Act> it = listAct.iterator(); it.hasNext(); ){
				Map<String, String> tmp = new HashMap<String, String>();
				Act act = it.next();		
				tmp.put("name", act.getName());
				tmp.put("info", act.getInfo());
				tmp.put("limit", act.getVoteLimit());
				tmp.put("time", act.getTime());
				tmp.put("act_id", act.getAct_id());
				tmp.put("rest", Integer.toString(act.getRest()));
				listData.add(tmp);
			}
		}
			
	//提示类
		private void showToast(CharSequence msg) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
}
