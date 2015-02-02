package com.funtouch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.funtouch.util.nfc.Beam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class VoteEdit extends Activity{
	public Cookie application ; 
	private Button btnOk = null;
	private Button btnUse = null;
	private Button btnExport = null;
	private RadioGroup rg = null;
	private ListView lv_team = null;
	private ArrayList<Map<String, Object>> teams = null;
	private ArrayList<String> teamNames;
	private EditText et_teamName = null;
	private int selected = 0;
	private String act_id;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	private List<Team> listTeam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
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
     
		setContentView(R.layout.vote_edit);
		et_teamName = new EditText(this);
		teamNames = new ArrayList<String>();
		teams = new ArrayList<Map<String, Object>>();
		btnOk = (Button)findViewById(R.id.btn_ok);
		btnUse = (Button)findViewById(R.id.btn_use);
		btnExport = (Button)findViewById(R.id.btn_export);
		rg = (RadioGroup)findViewById(R.id.rg);
		lv_team = (ListView)findViewById(R.id.lv_team);
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		
		listTeam = dataRetriever.seeVote(cookie,act_id);
     	if(listTeam.get(0).getCode().equals("200"))
		{
			btnUse.setText("Beam投票信息/进行投票");
			
			//导出投票结果
			btnExport.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent();
	        		intent.setClass(VoteEdit.this, SeeVote.class);
	        		intent.putExtra("act_id", act_id);
	        		startActivity(intent);
	         	}
	        });
			
			btnUse.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		Intent intent = new Intent();
	        		TeamListInfo info = new TeamListInfo(listTeam);	
	        		intent.setClass(VoteEdit.this, Beam.class);
	        		List<TeamListInfo> objectList = new ArrayList<TeamListInfo>();
	        		objectList.add(info);
	        		intent.putExtra("teamlist", (Serializable) objectList);
	        		intent.putExtra("limit", listTeam.get(0).getLimit());
	        		//Log.i("rg",Integer.toString(rg.getCheckedRadioButtonId()));
	        		startActivity(intent);
	        		}      	
	        });
		}
     	else if(listTeam.get(0).getCode().equals("null"))
		{	
		//添加队伍按钮
		btnOk.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		showDialog();
	         	}
	        });
		
		//长按队伍修改队名
		lv_team.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				selected = arg2;
				showModifyDialog();
				return false;
			}
		});
		
		//添加队伍后提交队伍信息到服务器
		btnUse.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		if(rg.getCheckedRadioButtonId()!= -1 && lv_team.getCount()!=0)
        		{
        			String limit = Integer.toString(rg.getCheckedRadioButtonId());
        			int code = dataRetriever.createVote(cookie, act_id, limit , teams);
        			if(code == 200)
        			{
        				showToast("投票创建成功");
        				//btnUse.setText("Beam投票信息/进行投票");
        				Intent intent = new Intent();
    	        		intent.setClass(VoteEdit.this, Beam.class);
    	        		startActivity(intent);
        			}
        		}
        		else
        			showToast("请输入完整信息!");
         	}
        });
		
		//导出投票结果
		btnExport.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent = new Intent();
        		intent.setClass(VoteEdit.this, SeeVote.class);
        		intent.putExtra("act_id", act_id);
        		startActivity(intent);
         	}
        });
		}
	}
	
	
	public void showDialog(){
		et_teamName = new EditText(VoteEdit.this);
		new AlertDialog.Builder(VoteEdit.this)
		.setTitle("请输入队伍名称")
		.setView(et_teamName)
		.setPositiveButton("完成", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String teamName = et_teamName.getText().toString();
				String teamInfo = null;
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("team_name", teamName);
				tmp.put("team_info", teamInfo);
				teams.add(tmp);
				teamNames.add(teamName);
				lv_team.setAdapter(new ArrayAdapter<String>(VoteEdit.this, android.R.layout.simple_list_item_multiple_choice, teamNames));
				lv_team.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			}
			
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
	public void showModifyDialog(){
		et_teamName = new EditText(VoteEdit.this);
		new AlertDialog.Builder(VoteEdit.this)
		.setTitle("请输入修改后的队伍名称")
		.setView(et_teamName)
		.setPositiveButton("完成", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String teamName = et_teamName.getText().toString();
				String teamInfo = null;
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("team_name", teamName);
				tmp.put("team_info", teamInfo);
				teamNames.set(selected, teamName);
				teams.set(selected, tmp);
				lv_team.setAdapter(new ArrayAdapter<String>(VoteEdit.this, android.R.layout.simple_list_item_multiple_choice, teamNames));
				lv_team.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			}
			
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
	//提示类
	public void showToast(String msg){
		Toast.makeText(VoteEdit.this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_voteedit, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.rubbish:
			long[] toDeleteTeams = lv_team.getCheckItemIds();
			if(0 == toDeleteTeams.length){
				showToast("请选择你要删除的队伍!");
			}else{
				if(toDeleteTeams.length > 1){
					for(int i = 1 ; i < toDeleteTeams.length ; i++){
						toDeleteTeams[i]-=1;
					}
				}
				for(int i = 0 ; i < toDeleteTeams.length ; i++){
					teamNames.remove((int)toDeleteTeams[i]);
					teams.remove((int)toDeleteTeams[i]);
					lv_team.setAdapter(new ArrayAdapter<String>(VoteEdit.this, android.R.layout.simple_list_item_multiple_choice, teamNames));
					lv_team.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				}
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
}



