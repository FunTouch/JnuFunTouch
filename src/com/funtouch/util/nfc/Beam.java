package com.funtouch.util.nfc;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import android.annotation.SuppressLint;
import com.funtouch.util.nfc.BobNdefMessage;

import com.funtouch.R;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.primitives.Bytes;


import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;


public class Beam extends Activity implements CreateNdefMessageCallback,
        OnNdefPushCompleteCallback {
    NfcAdapter mNfcAdapter;
    TextView mInfoText;
    private Context mContext = null;
    private static final int MESSAGE_SENT = 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_use);

        mContext = this;
        mInfoText = (TextView) findViewById(R.id.nfc_test);
        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            mInfoText = (TextView) findViewById(R.id.nfc_test);
            mInfoText.setText("NFC is not available on this device.");
        } else {
            // Register callback to set NDEF message
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
    }


    /**
     * Implementation for the CreateNdefMessageCallback interface
     */
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        Time time = new Time();
        time.setToNow();
        Byte identifierCode = 0x01;
        StringBuffer sb = new StringBuffer();  	
        sb.append("{"+"\"∂”ŒÈ√˚≥∆\":"+"\""+"fucker"+"\""+","+"\"∂”ŒÈºÚΩÈ\":"+"\""+"motherfucker"+"\""+"}");
        String text = ("Beam me up!\n" +
                "Beam Time: " + time.format("%H:%M:%S"));
        NdefMessage msg = BobNdefMessage.getNdefMsg_from_RTD_TEXT(sb.toString(), false, false);
        NdefMessage msg1 = new NdefMessage(NdefRecord.createMime(
                "application/com.example.android.beam", text.getBytes())
         /**
          * The Android Application Record (AAR) is commented out. When a device
          * receives a push with an AAR in it, the application specified in the AAR
          * is guaranteed to run. The AAR overrides the tag dispatch system.
          * You can add it back in to guarantee that this
          * activity starts when receiving a beamed message. For now, this code
          * uses the tag dispatch system.
          */
          //,NdefRecord.createApplicationRecord("com.example.android.beam")
        );
        return msg;
    }

    /**
     * Implementation for the OnNdefPushCompleteCallback interface
     */
    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        // A handler is needed to send messages to the activity when this
        // callback occurs, because it happens from a binder thread
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    /** This handler receives a message from onNdefPushComplete */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_SENT:
                Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
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
        
        // only one message sent during the beam
        //NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        //mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
    }
    
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
    				if(isUri(record))
    				{
    					parseUriRecord(record);
    				}
    			}
    		}
    	}
    }
    
    private void parseUriRecord(NdefRecord record)
    {
    	short tnf = record.getTnf();
    	if (tnf == NdefRecord.TNF_WELL_KNOWN)
    		parseWellKnownUriRecord(record);
    	else if(tnf == NdefRecord.TNF_ABSOLUTE_URI)
    		parseAbsoluteUriRecord(record);
    }
    
    private void parseAbsoluteUriRecord(NdefRecord record)
    {
    	byte[] payload = record.getPayload();
    	Uri uri = Uri.parse(new String(payload,Charset.forName("UTF-8")));
    	uiControl(uri);
    }
    private void parseWellKnownUriRecord(NdefRecord record)
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
    	//byte[] payload = record.getPayload();
    	//String prefix = URI_PREFIX_MAP.get(payload[0]);
    	byte[] fullUri = Bytes.concat(Arrays.copyOfRange(payload,0, payload.length));
    	Uri uri = Uri.parse(payloadStr);
    	Log.i("b",uri.toString());
    	uiControl(uri);
    }
    
    private void uiControl(final Uri uri)
    {
    	Button button = (Button)findViewById(R.id.btn_return);
    	try
    	{
    		JSONObject result = new JSONObject(uri.toString());
    		String team = result.getString("∂”ŒÈ√˚≥∆");
    		String info = result.getString("∂”ŒÈºÚΩÈ");
    		mInfoText.setText("Rev MSG : " + "\n" + "∂”ŒÈ√˚≥∆: " + team +"\n" +"∂”ŒÈºÚΩÈ: "+info);
    	}
    	catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//button.setText("Open Link : " + uri);
    	button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				data.setAction(Intent.ACTION_VIEW);
				data.setData(uri);
				try
				{
					startActivity(data);
				} catch (ActivityNotFoundException e)
				{
					return;
				}
			}
		});
    }
    
    public static boolean isUri(NdefRecord record)
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

    
    private static final BiMap<Byte, String> URI_PREFIX_MAP = ImmutableBiMap
            .<Byte, String> builder().put((byte)0x00, "").put((byte)0x01, "http://www.")
            .put((byte)0x02, "https://www.").put((byte)0x03, "http://").put((byte)0x04, "https://")
            .put((byte)0x05, "tel:").put((byte)0x06, "mailto:")
            .put((byte)0x07, "ftp://anonymous:anonymous@").put((byte)0x08, "ftp://ftp.")
            .put((byte)0x09, "ftps://").put((byte)0x0A, "sftp://").put((byte)0x0B, "smb://")
            .put((byte)0x0C, "nfs://").put((byte)0x0D, "ftp://").put((byte)0x0E, "dav://")
            .put((byte)0x0F, "news:").put((byte)0x10, "telnet://").put((byte)0x11, "imap:")
            .put((byte)0x12, "rtsp://").put((byte)0x13, "urn:").put((byte)0x14, "pop:")
            .put((byte)0x15, "sip:").put((byte)0x16, "sips:").put((byte)0x17, "tftp:")
            .put((byte)0x18, "btspp://").put((byte)0x19, "btl2cap://").put((byte)0x1A, "btgoep://")
            .put((byte)0x1B, "tcpobex://").put((byte)0x1C, "irdaobex://")
            .put((byte)0x1D, "file://").put((byte)0x1E, "urn:epc:id:")
            .put((byte)0x1F, "urn:epc:tag:").put((byte)0x20, "urn:epc:pat:")
            .put((byte)0x21, "urn:epc:raw:").put((byte)0x22, "urn:epc:")
            .put((byte)0x23, "urn:nfc:").build();

}
