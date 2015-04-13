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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyCode  extends MenuHavingActivity{
	private SimpleAdapter adapter;
    private List<Code> listCode;
    public Cookie application ; 
    private String act_id;
    String cookie = application.getInstance().getCookie();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_code);
		
		ListView lsvCode = (ListView)findViewById(R.id.lsv_my_code);
		listCode = dataRetriever.seeCode(cookie);
		
		if(listCode.get(0).getCode().equals("200"))
        {
        	showToast("获取验证码列表成功");
        	getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_code_raw,
					new String[] {"name", "info", "value"},
					new int[] {R.id.act_name, R.id.act_info, R.id.code});
			lsvCode.setAdapter(adapter);
			
			lsvCode.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view,  
					     int position, long id) {
					ListView listView = (ListView)parent; 
					HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
					String name = map.get("name");
					for (Iterator<Code> it=listCode.iterator(); it.hasNext(); )
				    {	    	
				    	Code code = it.next();
				    	if(name.equals(code.getName()))
				    	{
				    		Intent intent = new Intent();
							intent.setClass(MyCode.this, CodeBeam.class);
							act_id = code.getAct_id();
							intent.putExtra("act_id", act_id);
							intent.putExtra("value", code.getValue());
							intent.putExtra("username", application.getInstance().getName());
							startActivity(intent);
							break;
				    	}
				    }
				}
				});
        }
		else if(listCode.get(0).getCode().equals("null"))
		{
			showToast("你尚无活动验证码");
		}
		
		
	}
	
	//获取报名列表数据
	private void getData() {
		listData.clear();
		for (Iterator<Code> it = listCode.iterator(); it.hasNext(); ){
			Map<String, String> tmp = new HashMap<String, String>();
			Code code = it.next();		
			tmp.put("name", code.getName());
			tmp.put("info", code.getInfo());
			tmp.put("value", code.getValue());
			tmp.put("act_id", code.getAct_id());
			listData.add(tmp);
		}
	}
				
	//提示类
	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		MyCode.this.recreate();
	}

}
