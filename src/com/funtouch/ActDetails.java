package com.funtouch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ActDetails extends MenuHavingActivity{
	private SimpleAdapter adapter;
	private List<Map<String, Object>> ActDetail = new ArrayList<Map<String, Object>>();
	private Button btnDelAct,btnUpdateAct = null;
	private DataRetriever dataRetriever = new DataRetriever();
	private Cookie application ;
	private String cookie = application.getInstance().getCookie();
	
	protected void onCreate(Bundle savedInstanceState) {
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_details);
		List<ActDetailsInfo> objectList = (List<ActDetailsInfo>) getIntent().getSerializableExtra("ListObject");
		ActDetail = objectList.get(0).getActDetail();
		ListView lsvActDetails = (ListView)findViewById(R.id.lsv_act_details);
		adapter = new SimpleAdapter(this, ActDetail, R.layout.lsv_act_detail_raw,
				new String[] {"name", "info", "time","place","type","org","actor"},
				new int[] {R.id.act_detail_name, R.id.act_detail_info, R.id.act_detail_time,R.id.act_detail_place,R.id.act_detail_type,R.id.act_detail_org,R.id.act_detail_actor,});
		lsvActDetails.setAdapter(adapter);
		
		btnDelAct = (Button)findViewById(R.id.btn_del_act);
		btnUpdateAct = (Button)findViewById(R.id.btn_update_act);
		
		btnDelAct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog();
			}
		});
		btnUpdateAct.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ActDetails.this, UpdateAct.class);
				intent.putExtra("act_id", ActDetail.get(0).get("act_id").toString());
				startActivity(intent);	
			}
		});
		
		lsvActDetails.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,  
				     int position, long id) {
				ListView listView = (ListView)parent;
				ActDetailsInfo info = new ActDetailsInfo(ActDetail);
				List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
				objectList.add(info);		
				Intent intent = new Intent();
				intent.setClass(ActDetails.this, ActMenu.class);
				intent.putExtra("act_id", ActDetail.get(0).get("act_id").toString());
				startActivity(intent);	
			}
		});
	}
	
	
	public void showDialog(){
		new AlertDialog.Builder(ActDetails.this)
		.setTitle("删除活动")
		.setMessage("确定要删除该活动吗?\n所有与该活动相关的投票及报名信息都会被清空!")
		.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				int flag = dataRetriever.deleteAct(cookie, ActDetail.get(0).get("act_id").toString());
				if(flag == 200)
				{
					showToast("\""+ActDetail.get(0).get("name").toString()+"\""+"活动删除成功");
					Intent intent=new Intent();
					intent.setClass(ActDetails.this, AboutActivity.class);
					startActivity(intent);
	        		finish();
				}
					
        		
			}	
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		ActDetails.this.recreate();
	}
}
