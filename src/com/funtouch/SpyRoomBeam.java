package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.funtouch.util.nfc.BobNdefMessage;

public class SpyRoomBeam extends MenuHavingActivity implements CreateNdefMessageCallback,
OnNdefPushCompleteCallback{
	NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private static final int MESSAGE_SENT = 1;
	TextView tv_role, tv_count , tv_common_phrase, tv_spy_phrase;
	ImageButton btnSeeRole;
	public Cookie application ; 
	private DataRetriever dataRetriever = new DataRetriever();
    String cookie = application.getInstance().getCookie();
    List<String> list = new ArrayList<String>();
    int room_id;
    int count ,num = 0;
    String common_phrase, spy_phrase = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spy_room_beam);
        Intent intent1 = getIntent();
		room_id = intent1.getIntExtra("room_id", 0);
		num = intent1.getIntExtra("num", 0);
		num = intent1.getIntExtra("num", 0);
		common_phrase = intent1.getStringExtra("common_phrase");
		spy_phrase = intent1.getStringExtra("spy_phrase");
		
		tv_role = (TextView)findViewById(R.id.tv_role);
		tv_count = (TextView)findViewById(R.id.tv_count);
		tv_common_phrase = (TextView)findViewById(R.id.tv_common_phrase);
		tv_spy_phrase = (TextView)findViewById(R.id.tv_spy_phrase);
		btnSeeRole = (ImageButton)findViewById(R.id.btn_seerole);
		
		// Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
        		new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        
		tv_role.setText(room_id+"");
		tv_common_phrase.setText(common_phrase+"");
		tv_spy_phrase.setText(spy_phrase+"");
		
        if (mNfcAdapter == null) {
            showToast("NFC is not available on this device.");
        } else {
            // Register callback to set NDEF message
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        
        btnSeeRole.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			if(count >= num)
    			{
        			Intent intent = new Intent();
        			intent.setClass(SpyRoomBeam.this, SpySeeRole.class);
        			intent.putExtra("room_id", room_id);
        			startActivity(intent);
    			}
    			else
    				showToast("请先为每个玩家推送房间号!");

    		}
    	});
        
    }

    
	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// TODO Auto-generated method stub
		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		// TODO Auto-generated method stub
        NdefMessage msg = BobNdefMessage.getNdefMsg_from_RTD_TEXT(Integer.toString(room_id), false, false);
         /**
          * The Android Application Record (AAR) is commented out. When a device
          * receives a push with an AAR in it, the application specified in the AAR
          * is guaranteed to run. The AAR overrides the tag dispatch system.
          * You can add it back in to guarantee that this
          * activity starts when receiving a beamed message. For now, this code
          * uses the tag dispatch system.
          */
          //,NdefRecord.createApplicationRecord("com.example.android.beam")
        
        return msg;
	}
	
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_SENT:
            	if(count < num)
                {
                	count++;
                	tv_count.setText(count+"");
                	Toast.makeText(getApplicationContext(), "房间推送成功!", Toast.LENGTH_LONG).show();
                }
            	else
            		showToast("已超过人数最大限制!!");
                
                break;
            }
        }
    };
    
  
    
    protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Log.i("onResume", "enableFroeground");
		if (mNfcAdapter != null) 
			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null,
                null);
	}

    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mNfcAdapter != null) 
			mNfcAdapter.disableForegroundDispatch(this);
	}

	
  //提示类
  	public void showToast(String msg){
  		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
  	}
  	
  	public void showDialog(){
		new AlertDialog.Builder(SpyRoomBeam.this)
		.setMessage("							确定要退出游戏吗?")
		.setPositiveButton("退出", new android.content.DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
			
		})
		.setNegativeButton("再耍一会", null)
		.show();
	}
  	
  	public boolean onKeyDown(int keyCode, KeyEvent event)   
	{  
	    // TODO Auto-generated method stub  	
		
	    if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN))  
	    {  
	    	showDialog(); 
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
