package com.funtouch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.funtouch.util.nfc.BobNdefMessage;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class RoleBeam extends MenuHavingActivity implements CreateNdefMessageCallback,
OnNdefPushCompleteCallback{
	NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private static final int MESSAGE_SENT = 1;
	TextView tv_role;
	public Cookie application ; 
	private String username,act_id,value = null;
	private DataRetriever dataRetriever = new DataRetriever();
    String cookie = application.getInstance().getCookie();
    List<String> list = new ArrayList<String>();
    int count = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_beam);
        Intent intent1 = getIntent();
		list = intent1.getStringArrayListExtra("list");
		
		Log.i("list",list.toString());
		tv_role = (TextView)findViewById(R.id.tv_role);
		
		// Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
        		new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        
        if (mNfcAdapter == null) {
            showToast("NFC is not available on this device.");
        } else {
            // Register callback to set NDEF message
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        
    }

    
	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// TODO Auto-generated method stub
		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		// TODO Auto-generated method stub
		JSONObject json_data = new JSONObject();
		if(count<list.size())
		{
			try {
				json_data.put("role", list.get(count));			
				count++;
				System.out.println(list.size());
			}catch (JSONException e) {				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		else
			showToast("角色已分配完毕!");
        NdefMessage msg = BobNdefMessage.getNdefMsg_from_RTD_TEXT(json_data.toString(), false, false);
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
                
                if(count<list.size())
                {
                	tv_role.setText(list.get(count)+"");
                	Toast.makeText(getApplicationContext(), "角色推送成功!", Toast.LENGTH_LONG).show();
                }
                else
                	showToast("角色已分配完毕!");
                
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
  		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  	}

}
