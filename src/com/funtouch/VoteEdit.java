package com.funtouch;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Toast;

public class VoteEdit extends Activity{
	Button btnOk = null;
	ListView lv_team = null;
	ArrayList<String> teamNames = null;
	EditText et_teamName = null;
	int selected = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vote_edit);
		et_teamName = new EditText(this);
		teamNames = new ArrayList<String>();
		btnOk = (Button)findViewById(R.id.btn_ok);
		lv_team = (ListView)findViewById(R.id.lv_team);
		btnOk.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
	        		showDialog();
	         	}
	        });
		
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
				teamNames.set(selected, teamName);
				lv_team.setAdapter(new ArrayAdapter<String>(VoteEdit.this, android.R.layout.simple_list_item_multiple_choice, teamNames));
				lv_team.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			}
			
		})
		.setNegativeButton("取消", null)
		.show();
	}
	
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
					lv_team.setAdapter(new ArrayAdapter<String>(VoteEdit.this, android.R.layout.simple_list_item_multiple_choice, teamNames));
					lv_team.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				}
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
}



