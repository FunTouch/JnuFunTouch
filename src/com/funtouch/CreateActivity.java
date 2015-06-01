package com.funtouch;

import java.lang.reflect.Field;

import com.funtouch.util.SystemBarTintManager;
import com.funtouch.util.SystemBarTintManager.SystemBarConfig;

import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class CreateActivity extends MenuHavingActivity{
	public Cookie application ; 
	//private int maxflag = 0;
	private EditText name, time, place, type, org, actor, limit, info;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);
		Button btnCreateAct = null;
		
		name = (EditText) findViewById(R.id.edt_ActName);
		time = (EditText) findViewById(R.id.edt_ActTime);
		place = (EditText) findViewById(R.id.edt_ActPlace);
		type = (EditText) findViewById(R.id.edt_ActType);
		org = (EditText) findViewById(R.id.edt_ActOrg);
		actor = (EditText) findViewById(R.id.edt_ActActor);
		info = (EditText) findViewById(R.id.edt_ActInfo);
		btnCreateAct = (Button) findViewById(R.id.btn_CreateAct);	
		
		info.setFilters(new InputFilter[] { new InputFilter() {
		    @Override
		    public CharSequence filter(CharSequence source, int start,
		      int end, Spanned dest, int dstart, int dend) {
		    
		     if (dest.length() > 49) {
		    	 //showToast("字数已超过50字!");
		    	 //Log.i("1111",source+" "+start+" "+end+" "+dest.toString()+" "+dstart+" "+dend);
		    	 //maxflag = 0;		    	 
		    	 return "";			//长度超过了当前的字符就不要显示了，也就不返回了
		     } 
		     else
		     {
		    	 //maxflag = 1;
		    	 return source;		//没超过就送给editText
		     }
		    }
		   } });
		
		name.setFilters(new InputFilter[] { new InputFilter() {
		    @Override
		    public CharSequence filter(CharSequence source, int start,
		      int end, Spanned dest, int dstart, int dend) {
		    	//Log.i("1111",dest.length()+dest.toString()+" "+dstart+" "+dend);
		    	if (dest.length() > 10) {
			    	 showToast("字数已超过10字!");
			    	 //maxflag = 0;		    	 
			    	 return "";			//长度超过了当前的字符就不要显示了，也就不返回了
			    }
		    	if (dest.length() > 9) {
			    	 //showToast("字数已超过50字!");
			    	 //maxflag = 0;		    	 
			    	 return "";			//长度超过了当前的字符就不要显示了，也就不返回了
			    } 		    	
			    else
			    {
			    	//maxflag = 1;
			    	return source;		//没超过就送给editText
			    }
		    }
		   } });
		
		btnCreateAct.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(name.getText().toString().trim().equals("")||time.getText().toString().trim().equals("")||
					org.getText().toString().trim().equals("")||info.getText().toString().trim().equals("")){
					showToast("请填写必要的活动信息!");
				}
				else{
					int flag = dataRetriever.createAct(cookie,name.getText().toString(),info.getText().toString(),time.getText().toString(),
							place.getText().toString(),type.getText().toString(),org.getText().toString(),actor.getText().toString());
					if(flag == 200)
					{
						showToast("活动创建成功");
						Intent intent = new Intent();
						intent.setClass(CreateActivity.this, AboutActivity.class);
						startActivity(intent);
						finish();
					}
					else if(flag == 420)
					{
						showToast("活动创建失败");
					}
					else if(flag == 404)
					{
						showToast("请先登陆!");
						Intent intent = new Intent();
						intent.setClass(CreateActivity.this, Login.class);
						startActivity(intent);
						finish();
					}
				}
			}
		});
		
	}	
	
	
	
	//��ʾ��
		private void showToast(CharSequence msg) {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		}
		
		
		
		
}
