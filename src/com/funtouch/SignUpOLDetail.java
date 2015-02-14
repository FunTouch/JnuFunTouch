package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SignUpOLDetail extends Activity{
	public Cookie application ;
	private SimpleAdapter adapter;
	private Button btnSignUp = null;
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private String act_id = null;
	private List<Act> listAct;
	private String flag = null;
	private String user_id =null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	String user_name = application.getInstance().getName();
	
	protected void onCreate(Bundle savedInstanceState) {
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_ol_detail);
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		flag = intent1.getStringExtra("flag");
		listAct = dataRetriever.getEnrollActDetail(act_id);
		
		ListView lsvDetail = (ListView)findViewById(R.id.lsv_enroll_act_detail);	
		btnSignUp = (Button)findViewById(R.id.btn_sign_up);
		
		getData();
		adapter = new SimpleAdapter(this, listData, R.layout.lsv_enroll_act_detail_raw,
				new String[] {"name", "info", "time","place","type","org","actor","limit","num","need_info"},
				new int[] {R.id.enroll_act_detail_name, R.id.enroll_act_detail_info, R.id.enroll_act_detail_time,R.id.enroll_act_detail_place,
				R.id.enroll_act_detail_type,R.id.enroll_act_detail_org,R.id.enroll_act_detail_actor,R.id.enroll_act_detail_limit,R.id.enroll_act_detail_num,R.id.enroll_act_detail_need_info,});
		lsvDetail.setAdapter(adapter);
		
		if(flag.equals("0"))
		{
		btnSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int flag = dataRetriever.signUp(cookie, act_id);
				if(flag == 200)
				{
					showToast("用户"+"\""+user_name+"\""+"已成功报名"+"\""+listData.get(0).get("name")+"\"活动");
					SignUpOLDetail.this.recreate();
				}
				else if (flag == 450)
		    		showToast("用户"+"\""+user_name+"\""+"已经报名过"+"\""+listData.get(0).get("name")+"\"活动");
		    	else if (flag == 452)
		    		showToast("\""+listData.get(0).get("name")+"\" 活动"+"名额已满");
			}
		});
		}
		else if(flag.equals("1"))
		{
			btnSignUp.setText("撤销报名");
			btnSignUp.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int flag = dataRetriever.deleteOneSignUp(cookie, act_id, user_id);
					if(flag == 200)
					{
						showToast("用户"+"\""+user_name+"\""+"已成功报名"+"\""+listData.get(0).get("name")+"\"活动");
						SignUpOLDetail.this.recreate();
					}
					else if (flag == 450)
			    		showToast("用户"+"\""+user_name+"\""+"已经报名过"+"\""+listData.get(0).get("name")+"\"活动");
			    	else if (flag == 452)
			    		showToast("\""+listData.get(0).get("name")+"\" 活动"+"名额已满");
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
				tmp.put("place", act.getPlace());
				tmp.put("type", act.getType());
				tmp.put("org", act.getOrg());
				tmp.put("actor", act.getActor());
				tmp.put("limit", act.getEnrollLimit());
				tmp.put("num", act.getNum());
				tmp.put("need_info", act.getNeedInfo());
				listData.add(tmp);
			}
		}
			
	//提示类
		private void showToast(CharSequence msg) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}

}
