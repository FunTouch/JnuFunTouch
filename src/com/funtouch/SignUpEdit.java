package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignUpEdit extends Activity{
	
	public Cookie application ;
	private Button btnOk = null;
	private Button btnUse = null;
	private Button btnDelete = null;
	private Button btnSee = null;
	private CheckBox chkName,chkSno,chkPhone,chkMailbox,chkQQ,chkClass = null;
	private EditText edtLimit = null;
	private String act_id, limit;
	private List<Enroll> listEnroll;
	private Map<String, String> signup = null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_edit);
		
		initCreate();
		
		signup = new HashMap<String, String>();
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		listEnroll = dataRetriever.seeEnroll(act_id);
	
		
		btnSee.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SignUpEdit.this, EnrollList.class);
        		intent.putExtra("act_id", act_id);
        		startActivity(intent);
			}
		});
		if(listEnroll.get(0).getCode().equals("200")||listEnroll.get(0).getCode().equals("null"))
		{
			btnUse.setOnClickListener(new OnClickListener() {
			
			@Override
				public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
        		intent.setClass(SignUpEdit.this, SignUpUse.class);
        		intent.putExtra("act_id", act_id);
        		startActivity(intent);
				}
			});
		}
		else{
		btnOk.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		StringBuffer info = new StringBuffer();
        		if(edtLimit.getText().toString().trim().equals(""))
        			showToast("请填写报名人数限制!");
        		else{
        			limit = edtLimit.getText().toString();
        			int chosen = 0;
        			if(chkName.isChecked())
        			{
        				info.append("name,");
        				chosen++;
        			}
        			if(chkSno.isChecked())
        			{
        				info.append("sno,");
        				chosen++;
        			}
        			if(chkPhone.isChecked())
        			{
        				info.append("phone,");
        				chosen++;
        				}
        			if(chkMailbox.isChecked())
        			{
        				info.append("mailbox,");
        				chosen++;
        			}
        			if(chkQQ.isChecked())
        			{
        				info.append("qq,");
        				chosen++;
        			}
        			if(chkClass.isChecked())
        			{
        				info.append("grade,");
        				chosen++;
        			}
        			
        			if(chosen == 0)
        				showToast("请至少勾选一个选项!");
        			else{
        				info.deleteCharAt(info.length()-1);
        			int flag = dataRetriever.createSignUp(cookie, act_id, limit, info.toString());
        			if(flag == 200)
        			{
        				showToast("报名创建成功");
        				SignUpEdit.this.recreate();
        			}
        			else if(flag == 451)
        				showToast("该活动已创建过报名!");
        			else if(flag == 454)
        				showToast("info存在无效值");
        			}
        			
        		}
         	}
        });
		}
	
	}

	private void initCreate() {
		// TODO Auto-generated method stub
		//button
		btnOk = (Button)findViewById(R.id.btn_sign_ok);
		btnUse = (Button)findViewById(R.id.btn_sign_rev_beam);
		btnDelete = (Button)findViewById(R.id.btn_sign_del);
		btnSee = (Button)findViewById(R.id.btn_sign_see);
		chkName = (CheckBox)findViewById(R.id.chk_name);
		chkSno = (CheckBox)findViewById(R.id.chk_sno);
		chkPhone = (CheckBox)findViewById(R.id.chk_phone);
		chkMailbox = (CheckBox)findViewById(R.id.chk_mailbox);
		chkQQ = (CheckBox)findViewById(R.id.chk_qq);
		chkClass = (CheckBox)findViewById(R.id.chk_class);
		edtLimit = (EditText)findViewById(R.id.edt_sign_up_limit);
	

	}
	
	//提示类
		private void showToast(CharSequence msg) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
}
