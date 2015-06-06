package com.funtouch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class WhoIsTheSpy extends MenuHavingActivity {
	
	SeekBar mSeekBar;
	TextView mProgressText,tv_civilian,tv_undercover;
	int num,idiot,room_id=0;
	int civilian=7;
	int undercover=3;
	List<String> list = new ArrayList<String>();
	ImageButton btnBegin,btnGetRole = null;
	public Cookie application ; 
	String cookie = application.getInstance().getCookie();
	private DataRetriever dataRetriever = new DataRetriever();
    
	
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.who_is_the_spy);
		
		mSeekBar = (SeekBar) findViewById(R.id.seek);
		mProgressText = (TextView)findViewById(R.id.tv_num);
		tv_civilian = (TextView)findViewById(R.id.tv_civilian);
		tv_undercover = (TextView)findViewById(R.id.tv_undercover);
       	btnBegin = (ImageButton) findViewById(R.id.btn_begin);
       	btnGetRole = (ImageButton) findViewById(R.id.btn_getrole);
       /*	list.clear();
    	for (int i = 0; i < 7; i++)
        {
        	list.add("civilian");
        }
        for (int i = 0; i < 3; i++)
        {
        	list.add("undercover");
        }       
        Collections.shuffle(list);*/
        mSeekBar.setOnSeekBarChangeListener(new setSeekBarListener());
  
        btnBegin.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			Spy room = dataRetriever.createRoom(Integer.toString(undercover), Integer.toString(civilian), cookie);
    			if(room.getCode().equals("200"))
    			{
    				room_id = room.getRoomId();
	    			Intent intent = new Intent();
	    			intent.setClass(WhoIsTheSpy.this, SpyRoomBeam.class);
	    			intent.putExtra("room_id", room_id);
	    			intent.putExtra("num", undercover+civilian);
	    			intent.putExtra("common_phrase", room.getCommonPhrase());
	    			intent.putExtra("spy_phrase", room.getSpyPhrase());
	    			startActivity(intent);
    			}
    			else
    				showToast("创建房间失败!");

    		}
    	});
        btnGetRole.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			Intent intent = new Intent();
    			intent.setClass(WhoIsTheSpy.this, SpyGetRoom.class);
    			startActivity(intent);

    		}
    	});
	}
	
	
	
	class setSeekBarListener implements SeekBar.OnSeekBarChangeListener{

		//当进度条的进度改变时，会调用此方法。可以根据fromUser判断，是否是用户手动调节进度
		//progress 表示进度滑块当前的位置
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
		boolean fromUser) {
		// TODO Auto-generated method stub
		if(fromUser){
			num = progress + 4;
			mProgressText.setText(num+"");
			if(num<=6)
	        {
	        	civilian=num-1;
	        	undercover=1;
	        	idiot=0;
	        	tv_civilian.setText(civilian+"");
	        	tv_undercover.setText(undercover+"");
	        	list.clear();
	        	for (int i = 0; i < civilian; i++)
	            {
	            	list.add("civilian");
	            }
	            for (int i = 0; i < undercover; i++)
	            {
	            	list.add("undercover");
	            }
	            
	            Collections.shuffle(list);
	            
	        }
	        else if(num<=9&&num>6)
	        {
	        	civilian=num-2;
	        	undercover=2;
	        	idiot=0;
	        	tv_civilian.setText(civilian+"");
	        	tv_undercover.setText(undercover+"");
	        	list.clear();
	        	for (int i = 0; i < civilian; i++)
	            {
	            	list.add("civilian");
	            }
	            for (int i = 0; i < undercover; i++)
	            {
	            	list.add("undercover");
	            }
	            
	            Collections.shuffle(list);
	           
	        }
	        else if(num<=20&&num>9)
	        {
	        	civilian=num-3;
	        	undercover=3;
	        	idiot=0;
	        	tv_civilian.setText(civilian+"");
	        	tv_undercover.setText(undercover+"");
	        	list.clear();
	        	for (int i = 0; i < civilian; i++)
	            {
	            	list.add("civilian");
	            }
	            for (int i = 0; i < undercover; i++)
	            {
	            	list.add("undercover");
	            }
	            
	            Collections.shuffle(list);
	            //System.out.println(list.toString());
	        }
			}
		else{
			System.out.println("system change seekBar to -->" + progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO 自动生成的方法存根
			
		}
	}
	
	//提示类
  	public void showToast(String msg){
  		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
  	}
}
