package com.funtouch;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AboutActivity extends Activity {
	public Cookie application ; 

	private Button btnCreateAct = null;
	private Button btnSeeAct = null;
	private SimpleAdapter adapter;
	private SimpleAdapter adapter1;
	private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> ActDetail = new ArrayList<Map<String, Object>>();
	private List<Act> listSpeaker;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	Map<String, Object> tmp = new HashMap<String, Object>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectAll()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
     StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
     
		setContentView(R.layout.about_activity);
		
		ListView lsvActInfo = (ListView)findViewById(R.id.lsv_act_info);
				
		init();
		
		//获取活动列表
		listSpeaker = dataRetriever.retrieveAllAct(cookie);
		if(listSpeaker.get(0).getCode().equals("200"))
		{
			showToast("获取活动列表成功");
			getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_act_info_raw,
					new String[] {"name", "info", "time"},
					new int[] {R.id.act_name, R.id.act_info, R.id.act_time});
			lsvActInfo.setAdapter(adapter);
			
			//点击活动显示详情
			lsvActInfo.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view,  
					     int position, long id) {
					ListView listView = (ListView)parent; 
					ActDetail.clear();
				    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
				    String name = map.get("name");
				    String info = map.get("info");
				    for (Iterator<Act> it=listSpeaker.iterator(); it.hasNext(); )
				    {	    	
				    	Act spk = it.next();
				    	if(name.equals(spk.getName()) && info.equals(spk.getInfo()))
				    	{
				    		tmp.put("name", spk.getName());
							tmp.put("info", spk.getInfo());
							tmp.put("time", spk.getTime());
							tmp.put("place", spk.getPlace());
							tmp.put("type", spk.getType());
							tmp.put("org", spk.getOrg());
							tmp.put("actor", spk.getActor());
							tmp.put("act_id", spk.getAct_id());	
							tmp.put("user_id", spk.getUser_id());
							ActDetail.add(tmp);

							ActDetailsInfo info1 = new ActDetailsInfo(ActDetail);		//����Serializable����ͨ��intent����ActDetail
							List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
							objectList.add(info1);		
							Intent intent = new Intent();
							intent.setClass(AboutActivity.this, ActDetails.class);
							intent.putExtra("ListObject", (Serializable) objectList);
							startActivity(intent);
							break;
				    	}			    	
				    }
				}		
			}); 
		}
		else if(listSpeaker.get(0).getCode().equals("null"))
		{
			showToast("活动列表为空");
		}
		else if(listSpeaker.get(0).getCode().equals("414"))
		{
			showDialog();
		}
		else if(listSpeaker.get(0).getCode().equals("404")||listSpeaker.get(0).getCode().equals("412"))
		{
			showToast("请先登陆!");
			Intent intent = new Intent();
			intent.setClass(AboutActivity.this, Login.class);
			startActivity(intent);
			finish();
		}
		
		
			
		btnCreateAct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AboutActivity.this, CreateActivity.class);
				startActivity(intent);
			}
		});
		
		btnSeeAct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AboutActivity.this, SeeActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void showDialog(){
		new AlertDialog.Builder(AboutActivity.this)
		.setTitle("权限错误")
		.setMessage("非管理员没有此权限!")
		.setPositiveButton("返回", new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AboutActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}	
		})
		
		.show();
	}

	private void init() {
		btnCreateAct = (Button) findViewById(R.id.btn_create_activity);
		btnSeeAct = (Button) findViewById(R.id.btn_see_activity);
	}
	
	//获取活动列表数据
	private void getData() {
		listData.clear();
		for (Iterator<Act> it=listSpeaker.iterator(); it.hasNext(); ){
			Map<String, Object> tmp = new HashMap<String, Object>();
			Act spk = it.next();		
			tmp.put("name", spk.getName());
			tmp.put("info", spk.getInfo());
			tmp.put("time", spk.getTime());
			listData.add(tmp);
		}
	}
	
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		AboutActivity.this.recreate();
	}

}
