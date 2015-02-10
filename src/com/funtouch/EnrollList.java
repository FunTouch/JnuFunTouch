package com.funtouch;

import java.io.Serializable;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EnrollList extends Activity {

	private ListView lsvDetails = null;
	private Button btnExport = null;
	private List<Enroll> listEnroll;
	private String act_id;
	private SimpleAdapter adapter;
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> EnrollDetail = new ArrayList<Map<String, String>>();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	
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
		setContentView(R.layout.enroll_list);
		init();
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		listEnroll = dataRetriever.seeEnroll(act_id);
		if(listEnroll.get(0).getCode().equals("200"))
		{
			showToast("获取报名列表成功");
			getData();
			adapter = new SimpleAdapter(this, listData, R.layout.lsv_enroll_raw,
					new String[] {"name", "grade", "phone"},
					new int[] {R.id.enroll_name, R.id.enroll_grade, R.id.enroll_phone});
			lsvDetails.setAdapter(adapter);
			//点击活动显示详情
			lsvDetails.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view,  
					     int position, long id) {
					ListView listView = (ListView)parent; 
					EnrollDetail.clear();
				    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
				    String name = map.get("name");
				    for (Iterator<Enroll> it=listEnroll.iterator(); it.hasNext(); )
				    {	    	
				    	Enroll enroll = it.next();
				    	if(name.equals(enroll.getName()))
				    	{
				    		tmp.put("name", enroll.getName());
							tmp.put("sno", enroll.getSno());
							tmp.put("grade", enroll.getGrade());
							tmp.put("phone", enroll.getPhone());
							tmp.put("mailbox", enroll.getMailbox());
							tmp.put("qq", enroll.getQQ());
							EnrollDetail.add(tmp);

							EnrollListDetailsInfo info1 = new EnrollListDetailsInfo(EnrollDetail);		//����Serializable����ͨ��intent����ActDetail
							List<EnrollListDetailsInfo> objectList = new ArrayList<EnrollListDetailsInfo>();
							objectList.add(info1);		
							Intent intent = new Intent();
							intent.setClass(EnrollList.this, EnrollListDetails.class);
							intent.putExtra("ListObject", (Serializable) objectList);
							startActivity(intent);
							break;
				    	}			    	
				    }
				}		
			}); 
		}
		else if(listEnroll.get(0).getCode().equals("453"))
		{
			showToast("该活动尚未创建报名!");
		}
		else if(listEnroll.get(0).getCode().equals("null"))
		{
			showToast("该活动尚未有报名信息");
		}
		
	}

	private void init() {
		// TODO Auto-generated method stub
		lsvDetails = (ListView)findViewById(R.id.lsv_enroll_details);
		
		btnExport = (Button)findViewById(R.id.btn_export);
	}
	
	//获取报名列表数据
		private void getData() {
			listData.clear();
			for (Iterator<Enroll> it = listEnroll.iterator(); it.hasNext(); ){
				Map<String, String> tmp = new HashMap<String, String>();
				Enroll enroll = it.next();		
				tmp.put("name", enroll.getName());
				tmp.put("grade", enroll.getGrade());
				tmp.put("phone", enroll.getPhone());
				listData.add(tmp);
			}
		}
	
	//提示类
		private void showToast(CharSequence msg) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}

}
