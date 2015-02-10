package com.funtouch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ActDetails extends Activity{
	private SimpleAdapter adapter1;
	private List<Map<String, Object>> ActDetail = new ArrayList<Map<String, Object>>();
	protected void onCreate(Bundle savedInstanceState) {
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_details);
		List<ActDetailsInfo> objectList = (List<ActDetailsInfo>) getIntent().getSerializableExtra("ListObject");
		ActDetail = objectList.get(0).getActDetail();
		ListView lsvActDetails = (ListView)findViewById(R.id.lsv_act_details);
		adapter1 = new SimpleAdapter(this, ActDetail, R.layout.lsv_act_detail_raw,
				new String[] {"name", "info", "time","place","type","org","actor"},
				new int[] {R.id.act_detail_name, R.id.act_detail_info, R.id.act_detail_time,R.id.act_detail_place,R.id.act_detail_type,R.id.act_detail_org,R.id.act_detail_actor,});
		lsvActDetails.setAdapter(adapter1);
		
		lsvActDetails.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,  
				     int position, long id) {
				ListView listView = (ListView)parent;
				ActDetailsInfo info = new ActDetailsInfo(ActDetail);
				List<ActDetailsInfo> objectList = new ArrayList<ActDetailsInfo>();
				objectList.add(info);		
				Intent intent = new Intent();
				intent.setClass(ActDetails.this, ActMenu.class);
				//intent.putExtra("ListObject", (Serializable) objectList);
				intent.putExtra("act_id", ActDetail.get(0).get("act_id").toString());
				startActivity(intent);	
			}
		});
	}
}
