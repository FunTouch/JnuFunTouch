package com.funtouch;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Preconditions;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

public class ValiUser extends Activity {
	public Cookie application ;
	NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private TextView username = null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	//String rev_cookie = null;
	//String act_id,value = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vali_user);
		
		username = (TextView)findViewById(R.id.username);

		 // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
        		new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        
        if(cookie == null)
        {
        	showToast("请先登陆!");
        	Intent intent = new Intent();
			intent.setClass(ValiUser.this, Login.class);
			startActivity(intent);
			finish();
        }
              
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Log.i("onResume", "enableFroeground");
		if (mNfcAdapter != null) 
			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null,
                null);
	}
    
    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
        processIntent(intent);
    }
    
    void processIntent(Intent intent) {
    	String action = intent.getAction();
    	if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
    	{
    		NdefMessage[] messages = null;
    		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
    		if(rawMsgs != null)
    		{
    			messages = new NdefMessage[rawMsgs.length];
    			for (int i = 0; i < rawMsgs.length; i++)
    			{
    				messages[i] = (NdefMessage) rawMsgs[i];
    			}
    		}else
    		{
    			byte[] empty = new byte[]{};
    			NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,empty,empty,empty);
    			NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
    			messages = new NdefMessage[] { msg };
    		}
    		processNDEFMsg(messages);
    	}
    }
    
    //处理NDEF消息
    void processNDEFMsg (NdefMessage[] messages)
    {
    	if (messages == null || messages.length == 0)
    	{
    		return;
    	}
    	for (int i = 0; i < messages.length; i++)
    	{
    		int length = messages[i].getRecords().length;
    		NdefRecord[] records = messages[i].getRecords();
    		for (int j = 0; j < length; j++)
    		{
    			for(NdefRecord record : records)
    			{
    				if(isText(record))
    				{
    					parseTextRecord(record);
    				}
    			}
    		}
    	}
    }
    
    private void parseTextRecord(NdefRecord record)
    {
    	short tnf = record.getTnf();
    	if (tnf == NdefRecord.TNF_WELL_KNOWN)
    		parseWellKnownTextRecord(record);
    }
    
    private void parseWellKnownTextRecord(NdefRecord record)
    {
    	Preconditions.checkArgument(record.getTnf() == NdefRecord.TNF_WELL_KNOWN);
    	Preconditions.checkArgument(Arrays.equals(record.getType(), NdefRecord.RTD_TEXT));
    	String payloadStr = "";
        byte[] payload = record.getPayload();
        Byte statusByte = record.getPayload()[0];

        String textEncoding = ((statusByte & 0200) == 0) ? "UTF-8" : "UTF-16";// 0x80=0200
        int languageCodeLength = statusByte & 0077; // & 0x3F=0077(bit 5 to 0)
        String languageCode = new String(payload, 1, languageCodeLength, Charset.forName("UTF-8"));
        try {
            payloadStr = new String(payload, languageCodeLength + 1, payload.length
                    - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	//byte[] fullUri = Bytes.concat(Arrays.copyOfRange(payload,0, payload.length));
    	Uri uri = Uri.parse(payloadStr);
    	//Log.i("b",uri.toString());
    	uiControl(uri);
    }
    
    private void uiControl(final Uri uri)
    {
    	try {
			JSONObject json_data = new JSONObject(uri.toString());
			int flag = dataRetriever.valiCode(cookie,json_data);
			if(flag == 200)
			{
	    		showToast("验证成功!");
	    		username.setText(json_data.getString("username"));
			}
	    	else if (flag == 461)
	    		showToast("无效key!");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mNfcAdapter != null) 
			mNfcAdapter.disableForegroundDispatch(this);
	}
    
    public static boolean isText(NdefRecord record)
    {
    	if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN)
    	{
    		if(Arrays.equals(record.getType(), NdefRecord.RTD_TEXT))
    			return true;
    		else
    			return false;
    	}
    	else if (record.getTnf() == NdefRecord.TNF_ABSOLUTE_URI)
    		return true;
    	else
    		return false;
    }
    
  //提示类
  	public void showToast(String msg){
  		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  	}
}
