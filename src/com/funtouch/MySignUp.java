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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MySignUp  extends MenuHavingActivity{
	private Button btnBeamSignUp = null;
    private Button btnOLSignUp = null;
    private SimpleAdapter adapter;
    private List<Act> listAct;
    public Cookie application ; 
    private String act_id;
	String cookie = application.getInstance().getCookie();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_sign_up);
		
		btnBeamSignUp = (Button) findViewById(R.id.btn_beam_sign_up);
		btnOLSignUp = (Button) findViewById(R.id.btn_ol_sign_up);
		
		ListView lsvAct = (ListView)findViewById(R.id.lsv_my_sign_up);
		listAct = dataRetriever.seeMySignUp(cookie);
		
		btnBeamSignUp.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(MySignUp.this, SignUpBeam.class);	
        		startActivity(intent);
        	}
        });
		
		btnOLSignUp.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(MySignUp.this, SignUpOL.class);	
        		startActivity(intent);
        	}
        });
		
		if(listAct.get(0).getCode().equals("200"))
        {
        	showToast("获取活动列表成功");
        	getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_enroll_act_raw,
					new String[] {"name", "time", "info", "rest"},
					new int[] {R.id.enroll_act_name,R.id.enroll_act_time, R.id.enroll_act_info, R.id.enroll_act_rest});
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
							intent.setClass(MySignUp.this, SignUpOLDetail.class);
							act_id = act.getAct_id();
							intent.putExtra("act_id", act_id);
							intent.putExtra("flag", "1");
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
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		}
		
		protected void onNewIntent(Intent intent) {

			super.onNewIntent(intent);
			MySignUp.this.recreate();
		}

}
