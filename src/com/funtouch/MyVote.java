package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyVote extends Activity{
	private Button btnBeamVote = null;
    private Button btnOLVote = null;
    public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	private DataRetriever dataRetriever = new DataRetriever();
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_vote);
		
		btnBeamVote = (Button) findViewById(R.id.btn_beam_vote);
		btnOLVote = (Button) findViewById(R.id.btn_ol_vote);
		
		btnBeamVote.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(MyVote.this, RevVoteBeam.class);	
        		startActivity(intent);
        	}
        });
		
		btnOLVote.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(MyVote.this, VoteOL.class);	
        		startActivity(intent);
        	}
        });
		
	}

}
