package com.funtouch;

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

public class EnrollListDetails extends Activity{
	private SimpleAdapter adapter;
	private List<Map<String, String>> EnrollDetail = new ArrayList<Map<String, String>>();
	protected void onCreate(Bundle savedInstanceState) {
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enroll_details);
		List<EnrollListDetailsInfo> objectList = (List<EnrollListDetailsInfo>) getIntent().getSerializableExtra("ListObject");
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
				EnrollListDetailsInfo info = new EnrollListDetailsInfo(EnrollDetail);
				List<EnrollListDetailsInfo> objectList = new ArrayList<EnrollListDetailsInfo>();
				objectList.add(info);		
			}
		});
	}
	
}
