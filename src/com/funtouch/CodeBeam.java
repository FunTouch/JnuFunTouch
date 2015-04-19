package com.funtouch;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.funtouch.util.nfc.BobNdefMessage;

public class CodeBeam extends MenuHavingActivity implements CreateNdefMessageCallback,
OnNdefPushCompleteCallback{
	NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private static final int MESSAGE_SENT = 1;
	public Cookie application ; 
	private String username,act_id,value = null;
	private DataRetriever dataRetriever = new DataRetriever();
    String cookie = application.getInstance().getCookie();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_beam);
        Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		username = intent1.getStringExtra("username");
		value = intent1.getStringExtra("value");
		
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
		try {
			json_data.put("username", username);
			json_data.put("act_id", act_id);
			json_data.put("value", value);
		}catch (JSONException e) {				// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
                Toast.makeText(getApplicationContext(), "验证码发送成功!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
				intent.setClass(CodeBeam.this, MyCode.class);
				startActivity(intent);
				finish();
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
