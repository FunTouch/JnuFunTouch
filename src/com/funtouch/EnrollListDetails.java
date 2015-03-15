package com.funtouch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EnrollListDetails extends Activity{
	private SimpleAdapter adapter;
	public Cookie application ;
	private String act_id;
	private Button btnDelSign = null;
	private List<Map<String, String>> EnrollDetail = new ArrayList<Map<String, String>>();
	String cookie = application.getInstance().getCookie();
	private DataRetriever dataRetriever = new DataRetriever();
	
	protected void onCreate(Bundle savedInstanceState) {
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enroll_details);
		
		btnDelSign = (Button) findViewById(R.id.btn_delete_enroll);
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		List<DetailsInfo> objectList = (List<DetailsInfo>) getIntent().getSerializableExtra("ListObject");
		EnrollDetail = objectList.get(0).getEnrollDetails();
		ListView lsvActDetails = (ListView)findViewById(R.id.lsv_enroll_details);
		adapter = new SimpleAdapter(this, EnrollDetail, R.layout.lsv_enroll_details_raw,
				new String[] {"name", "sno", "grade","phone","mailbox","qq"},
				new int[] {R.id.enroll_name, R.id.enroll_sno, R.id.enroll_grade,R.id.enroll_phone,R.id.enroll_mailbox,R.id.enroll_qq,});
		lsvActDetails.setAdapter(adapter);
		
		lsvActDetails.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,  
				     int position, long id) {
				ListView listView = (ListView)parent;
				DetailsInfo info = new DetailsInfo(EnrollDetail);
				List<DetailsInfo> objectList = new ArrayList<DetailsInfo>();
				objectList.add(info);		
			}
		});
		
		/*btnDelSign.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showDialog();
        		
        	}
        });*/
		
	}
	
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void showDialog(){
		new AlertDialog.Builder(EnrollListDetails.this)
		.setTitle("删除此报名信息")
		.setMessage("确定要删除该项报名吗?\n")
		.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				int flag = dataRetriever.deleteOneSignUp(cookie, act_id, EnrollDetail.get(0).get("user_id"));
				if(flag == 200)
				{
					showToast("该活动所有报名信息删除成功");
					Intent intent=new Intent();
	        		intent.setClass(EnrollListDetails.this, EnrollList.class);	
	        		intent.putExtra("act_id", act_id);
	        		startActivity(intent);
	        		finish();
				}
				else if(flag == 432)
					showToast("你不是该活动的创建者");
				else if(flag == 455)
					showToast("user_id错误");
				  		
			}	
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
}
