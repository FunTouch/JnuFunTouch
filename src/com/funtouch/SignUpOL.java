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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SignUpOL extends MenuHavingActivity{
	private SimpleAdapter adapter;
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Act> listAct;
	private String act_id;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_ol);
        
        ListView lsvActInfo = (ListView)findViewById(R.id.lsv_enroll_act);
        listAct = dataRetriever.getEnrollAct();
        
        if(listAct.get(0).getCode().equals("200"))
        {
        	showToast("获取活动列表成功");
        	getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_enroll_act_raw,
					new String[] {"name", "time", "info", "rest"},
					new int[] {R.id.enroll_act_name,R.id.enroll_act_time, R.id.enroll_act_info, R.id.enroll_act_rest});
			lsvActInfo.setAdapter(adapter);
			
			lsvActInfo.setOnItemClickListener(new OnItemClickListener(){
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
							intent.setClass(SignUpOL.this, SignUpOLDetail.class);
							act_id = act.getAct_id();
							intent.putExtra("act_id", act_id);
							intent.putExtra("flag", "0");
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
			tmp.put("rest",  Integer.toString(act.getRest()));
			tmp.put("time", act.getTime());
			tmp.put("act_id", act.getAct_id());
			listData.add(tmp);
		}
	}
		
//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
