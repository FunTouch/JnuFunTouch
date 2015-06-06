package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MyVote extends MenuHavingActivity{
	private ImageButton btnBeamVote = null;
    private ImageButton btnOLVote = null;
    public Cookie application ; 
    private List<Act> listAct;
    private SimpleAdapter adapter;
    private String act_id = null;
	String cookie = application.getInstance().getCookie();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_vote);
		
		ListView lsvAct = (ListView)findViewById(R.id.lsv_my_vote);
		listAct = dataRetriever.seeMySignUp(cookie);
		
		btnBeamVote = (ImageButton) findViewById(R.id.btn_beam_vote);
		btnOLVote = (ImageButton) findViewById(R.id.btn_ol_vote);
		
		btnBeamVote.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(MyVote.this, RevVoteBeam.class);	
        		startActivity(intent);
        	}
        });
		
		btnOLVote.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(MyVote.this, VoteOL.class);	
        		startActivity(intent);
        	}
        });
		
		if(listAct.get(0).getCode().equals("200"))
        {
        	showToast("获取活动列表成功");
        	getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_act_info_raw,
					new String[] {"name", "time", "info"},
					new int[] {R.id.act_name,R.id.act_time, R.id.act_info});
			lsvAct.setAdapter(adapter);
			
			lsvAct.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view,  
					     int position, long id) {
					ListView listView = (ListView)parent; 
					HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
					String name = map.get("name");
					for (Iterator<Act> it=listAct.iterator(); it.hasNext(); )
				    {	    	
				    	Act act = it.next();
				    	if(name.equals(act.getName()))
				    	{
				    		Intent intent = new Intent();
							intent.setClass(MyVote.this, SeeVote.class);
							act_id = act.getAct_id();
							intent.putExtra("act_id", act_id);
							intent.putExtra("authority", "user");
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
			tmp.put("time", act.getTime());
			tmp.put("act_id", act.getAct_id());
			listData.add(tmp);
			}
	}
				
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
}
