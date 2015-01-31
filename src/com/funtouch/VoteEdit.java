package com.funtouch;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class VoteEdit extends Activity{
	Button btnOk = null;
	ListView lv_team = null;
	ArrayList<String> teamNames = null;
	EditText et_teamName = null;
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
}



