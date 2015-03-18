package com.funtouch;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RelCode  extends Activity{
	private SimpleAdapter adapter;
    private List<Code> listCode;
    public Cookie application ; 
    private String act_id,key_id = null;
    String cookie = application.getInstance().getCookie();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	
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
		setContentView(R.layout.rel_code);
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		ListView lsvCode = (ListView)findViewById(R.id.lsv_rel_code);
		listCode = dataRetriever.seeRelCode(cookie,act_id);
		
		if(listCode.get(0).getCode().equals("200"))
        {
        	showToast("获取验证码列表成功");
        	getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_rel_code_raw,
					new String[] {"username", "status", "value"},
					new int[] {R.id.code_username, R.id.code_status, R.id.code});
			lsvCode.setAdapter(adapter);
			
			lsvCode.setOnItemLongClickListener(new OnItemLongClickListener(){
				public boolean onItemLongClick(AdapterView<?> parent, View view,  
					     int position, long id) {
					ListView listView = (ListView)parent; 
					HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
					String name = map.get("username");
					for (Iterator<Code> it=listCode.iterator(); it.hasNext(); )
				    {	    	
				    	Code code = it.next();
				    	if(name.equals(code.getName()))
				    	{
				    		showDialog(code);
							break;
				    	}
				    }
					return true;
				}
				});
        }
		
		else if(listCode.get(0).getCode().equals("null"))
		{
			showToast("尚未发放任何验证码");
		}
		
		
	}
	
	//获取报名列表数据
	private void getData() {
		listData.clear();
		for (Iterator<Code> it = listCode.iterator(); it.hasNext(); ){
			Map<String, String> tmp = new HashMap<String, String>();
			Code code = it.next();		
			tmp.put("username", code.getName());
			tmp.put("status", code.getStatus());
			tmp.put("value", code.getValue());
			//tmp.put("act_id", code.getAct_id());
			listData.add(tmp);
		}
	}
	
	public void showDialog(final Code code){
		new AlertDialog.Builder(RelCode.this)
		.setTitle("					更改验证码状态?")
		.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				int flag = dataRetriever.changeCode(cookie, act_id, code.getKey_id());
				if(flag == 200)
				{
					showToast("验证码状态已更改");
					RelCode.this.recreate();
				}
				else if(flag == 460)
				{
					showToast("活动尚未激活验证码功能!");
				}
				else if(flag == 462)
				{
					showToast("验证码无效!");
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
		RelCode.this.recreate();
	}
}
