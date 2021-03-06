package com.funtouch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ActVali extends MenuHavingActivity {
	private ImageButton btnActVali = null;
	private ImageButton btnGenCode = null;
	private ImageButton btnValiUser = null;
	private ImageButton btnSeeRelCode = null;
	public Cookie application ;
	String cookie = application.getInstance().getCookie();
	private String act_id;
	private EditText edt_Name = null;
	private DataRetriever dataRetriever = new DataRetriever();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
     
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_vali);
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		btnActVali = (ImageButton)findViewById(R.id.btn_act_vali);
		btnGenCode = (ImageButton)findViewById(R.id.btn_gen_code);
		btnSeeRelCode = (ImageButton)findViewById(R.id.btn_see_rel_code);
		btnValiUser = (ImageButton)findViewById(R.id.btn_vali_user);
		

		btnActVali.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showDialog();
        	}
        });
		
		btnGenCode.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		showNameDialog();
        	}
        });
		
		btnValiUser.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
				intent.setClass(ActVali.this, ValiUser.class);
				startActivity(intent);
        	}
        });
		
		btnSeeRelCode.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
				intent.setClass(ActVali.this,RelCode.class);
				intent.putExtra("act_id", act_id);
				startActivity(intent);
        	}
        });
		
		
	
	}
	
	public void showDialog(){
		new AlertDialog.Builder(ActVali.this)
		.setTitle("激活验证码功能")
		.setMessage("确定要在此活动激活使用验证码功能吗?")
		.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				int flag = dataRetriever.actVali(cookie, act_id);
				if (flag == 200)
				{
					showToast("该活动已激活验证码功能!");
				}
				else if (flag == 404)
					showToast("未登陆");
				else if (flag == 456)
					showToast("未报名");
			}	
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
	public void showNameDialog(){
		edt_Name = new EditText(ActVali.this);
		new AlertDialog.Builder(ActVali.this)
		.setTitle("请输入用户名")
		.setView(edt_Name)
		.setPositiveButton("生成验证码", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String userName = edt_Name.getText().toString();
				Code code = dataRetriever.genCode(cookie, act_id,userName);
				if(code.getCode().equals("200"))
				{
					showToast("用户 "+"\""+userName+"\""+" 的验证码已生成");
				}
				else if(code.getCode().equals("460"))
				{
					showToast("活动尚未激活验证码功能!");
				}
				else if(code.getCode().equals("410"))
				{
					showToast("没有此用户!");
				}
			}
			
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
