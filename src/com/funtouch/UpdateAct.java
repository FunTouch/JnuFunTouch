package com.funtouch;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
public class UpdateAct extends MenuHavingActivity{
	public Cookie application ; 
	private ImageButton btnSubmitUpdate = null;
	private EditText edtUpdateName, edtUpdateOrg, edtUpdateTime, edtUpdatePlace, edtUpdateType, edtUpdateActor, edtUpdateInfo = null;
	private DataRetriever dataRetriever = new DataRetriever();
	private Act act = new Act();
	private Map<String,String> update_info = new HashMap<String,String>();
	private String name,org,time,place,type,actor,info,act_id = null;
	String cookie = application.getInstance().getCookie();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
     	super.onCreate(savedInstanceState);
		setContentView(R.layout.update_act);
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		edtUpdateName = (EditText) findViewById(R.id.edt_update_name);
		edtUpdateInfo = (EditText) findViewById(R.id.edt_update_info);
		edtUpdatePlace = (EditText) findViewById(R.id.edt_update_place);
		edtUpdateTime = (EditText) findViewById(R.id.edt_update_time);
		edtUpdateActor = (EditText) findViewById(R.id.edt_update_actor);
		edtUpdateOrg = (EditText) findViewById(R.id.edt_update_org);
		edtUpdateType = (EditText) findViewById(R.id.edt_update_type);
		btnSubmitUpdate = (ImageButton)findViewById(R.id.btn_submit_update);
		
		act = dataRetriever.seeAct(cookie,act_id);
		
		edtUpdateName.setFilters(new InputFilter[] { new InputFilter() {
		    @Override
		    public CharSequence filter(CharSequence source, int start,
		      int end, Spanned dest, int dstart, int dend) {
		    	//Log.i("1111",dest.length()+dest.toString()+" "+dstart+" "+dend);
		    	if (dest.length() > 10) {
			    	 showToast("字数已超过10字!");	    	 
			    	 return "";			
			    }
		    	if (dest.length() > 9) {
			    	 //showToast("字数已超过50字!");		    	 
			    	 return "";			
			    } 		    	
			    else
			    {
			    	return source;		//没超过就送给editText
			    }
		    }
		   } });
		
		edtUpdateInfo.setFilters(new InputFilter[] { new InputFilter() {
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
		
		if(act.getCode().equals("200"))
  		{
  			showToast("获取活动信息成功");
  			if(!act.getName().toString().equals("null"))
  			{
  				edtUpdateName.setText(act.getName().toString());			
  			}
  			name = act.getName().toString();
  			if(!act.getInfo().toString().equals("null"))
  			{
  				edtUpdateInfo.setText(act.getInfo().toString());
  			}
  			info = act.getInfo().toString();
  			if(!act.getPlace().toString().equals("null"))
  			{
  				edtUpdatePlace.setText(act.getPlace().toString());
  				  			}
  			place = act.getPlace().toString();
  			if(!act.getTime().toString().equals("null"))
  			{
  				edtUpdateTime.setText(act.getTime().toString());
  				  			}
  			time = act.getTime().toString();
  			if(!act.getActor().toString().equals("null"))
  			{
  				edtUpdateActor.setText(act.getActor().toString());
  				  			}
  			actor = act.getActor().toString();
  			if(!act.getOrg().toString().equals("null"))
  			{
  				edtUpdateOrg.setText(act.getOrg().toString());
  				  			}
  			org = act.getOrg().toString();
  			if(!act.getType().toString().equals("null"))
  			{
  				edtUpdateType.setText(act.getType().toString());
  				  			}
  			type = act.getType().toString();
  			
  			btnSubmitUpdate.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        			if(!name.equals(null))
	        				update_info.put("name", edtUpdateName.getText().toString());
	        			else
	        				update_info.put("name", "null");
	        			if(!time.equals(null))
	        				update_info.put("time", edtUpdateTime.getText().toString());
	        			else
	        				update_info.put("time", "null");
	        			if(!place.equals(null))
	        				update_info.put("place", edtUpdatePlace.getText().toString());
	        			else
	        				update_info.put("place", "null");
	        			if(!info.equals(null))
	        				update_info.put("info", edtUpdateInfo.getText().toString());
	        			else
	        				update_info.put("info", "null");
	        			if(!org.equals(null))
	        				update_info.put("org", edtUpdateOrg.getText().toString());
	        			else
	        				update_info.put("org", "null");
	        			if(!actor.equals(null))
	        				update_info.put("actor", edtUpdateActor.getText().toString());
	        			else
	        				update_info.put("actor", "null");
	        			if(!type.equals(null))
	        				update_info.put("type", edtUpdateType.getText().toString());
	        			else
	        				update_info.put("type", "null");
	        			
	        			int flag = dataRetriever.updateAct(cookie, act_id, update_info);
	        			if(flag == 200)
	        			{
	        				showToast("信息更新完成!");
	        				Intent intent=new Intent();
	        				intent.setClass(UpdateAct.this, AboutActivity.class);
	        				startActivity(intent);
	        				finish();
	        			}
	        			else if(flag == 440)
	        			{
	        				showToast("有无效的参数");
	        			}        			
	        		}
  			});			
  		}
		
		else 
  			showToast("获取活动信息失败!");
     
	}
		
		
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	
	}
}
