package com.funtouch;

import com.funtouch.util.nfc.BobNdefMessage;

import android.app.Activity;
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
import android.widget.Toast;

public class SignUpBeam extends Activity implements CreateNdefMessageCallback,
OnNdefPushCompleteCallback{
	
	NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private static final int MESSAGE_SENT = 1;
	public Cookie application ; 
	private DataRetriever dataRetriever = new DataRetriever();
    String cookie = application.getInstance().getCookie();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_beam);
        
        
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
        public NdefMessage createNdefMessage(NfcEvent event) {
            StringBuffer sb = new StringBuffer();  	
            sb.append("{"+"\"cookie\":"+"\""+cookie+"\""+"}");
            NdefMessage msg = BobNdefMessage.getNdefMsg_from_RTD_TEXT(cookie, false, false);
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
        
        
	
	
    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        // A handler is needed to send messages to the activity when this
        // callback occurs, because it happens from a binder thread
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }
    
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_SENT:
                Toast.makeText(getApplicationContext(), "报名信息发送成功!", Toast.LENGTH_LONG).show();
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
