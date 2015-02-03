package com.funtouch.util.nfc;

import android.app.Activity;
import android.app.PendingIntent;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import android.annotation.SuppressLint;
import com.funtouch.util.nfc.BobNdefMessage;

import com.funtouch.Cookie;
import com.funtouch.DataRetriever;
import com.funtouch.R;
import com.funtouch.SeeVote;
import com.funtouch.Team;
import com.funtouch.TeamListInfo;
import com.funtouch.VoteEdit;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Beam extends Activity implements CreateNdefMessageCallback,
        OnNdefPushCompleteCallback, OnItemClickListener {
    NfcAdapter mNfcAdapter;
    TextView mInfoText;
    private Button btnSubmit = null;
    private Button btnReturn = null;
    private List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
    private PendingIntent mPendingIntent;
    private Context mContext = null;
    private static final int MESSAGE_SENT = 1;
    private List<Team> listTeam = new ArrayList<Team>();
    private int limit ;
    public Cookie application ; 
    private ListView lv_vote_team = null;
    private MyAdapter mSimpleAdapter;
    private int[] flag = {0,0,0,0,0,0};
    private String vote_id = null;
    private String act_id = null;
    private DataRetriever dataRetriever = new DataRetriever();
    String cookie = application.getInstance().getCookie();
    
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beam);
        
        Intent intent1=getIntent();
        limit = Integer.parseInt(intent1.getStringExtra("limit"));         //获得intent传过来的limit,并转化为int型
        act_id = intent1.getStringExtra("act_id");  
        lv_vote_team = (ListView)findViewById(R.id.lv_vote_team);
        List<TeamListInfo> objectList = (List<TeamListInfo>)getIntent().getSerializableExtra("teamlist");
        listTeam = objectList.get(0).getTeamList();		 //获得intent传过来的listTeam
        mContext = this;
        mInfoText = (TextView) findViewById(R.id.nfc_test); 
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        
        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
        		new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        
        getData();		//获取投票列表
        
        mSimpleAdapter = new MyAdapter(this, listData, R.layout.lsv_vote_team_raw,
				new String[] {"team_name", "team_info"},
				new int[] {R.id.multiple_team, R.id.multiple_info});
        
        
        lv_vote_team.setAdapter(mSimpleAdapter);
        lv_vote_team.setOnItemClickListener(this);
        
        
        if (mNfcAdapter == null) {
            mInfoText = (TextView) findViewById(R.id.nfc_test);
            mInfoText.setText("NFC is not available on this device.");
        } else {
            // Register callback to set NDEF message
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        
        btnSubmit.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		int q = 0;
        		int flag1 = 0;
        		for(int i = 0; i < listTeam.size(); i++)
        		{
        			int j = flag[i];
        			if( j%2 == 1)
        			{
        				q++;
        				if(q > limit)
        				{
        					showToast("超过投票限制!");
        					break;
        				}
        				else{
        					vote_id = listTeam.get(i).getVote_id();
        					flag1 = dataRetriever.postVote(cookie,act_id,vote_id);
        					Log.i("flag1",Integer.toString(flag1));
        				if(flag1 == 200)
           					showToast("投票成功");
        				else if(flag1 == 430)
        				{
        					showToast("票数已用完");
        					break;
        				}
        				}
        			}
        		}
        		if(q == 0)
        			showToast("请选择队伍!");
        		if(flag1 == 200)
        		{
        			Intent intent = new Intent();
        			intent.setClass(Beam.this, SeeVote.class);
        			intent.putExtra("act_id", act_id);
        			startActivity(intent);
        			finish();
        		}
         	}
        });
    }
    
    
    
  //获取投票数据
  	private void getData() {
  		listData.clear();
  		for (Iterator<Team> it=listTeam.iterator(); it.hasNext(); ){
  			Map<String, Object> tmp = new HashMap<String, Object>();
  			Team tl = it.next();		
  			tmp.put("team_name", tl.getTeam_name());
  			tmp.put("team_info", tl.getTeam_info());
  			listData.add(tmp);
  		}
  	}


    /**
     * Implementation for the CreateNdefMessageCallback interface
     */
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        StringBuffer sb = new StringBuffer();  	
        sb.append("[");
        for(int i = 0; i < listTeam.size(); i++)
        {
        	sb.append("{"+"\"队伍名称\":"+"\""+listTeam.get(i).getTeam_name()+"\""+","+"\"队伍简介\":"+"\""+listTeam.get(i).getTeam_info()+"\""+"}");
        	if(i < listTeam.size() - 1)
			{
				sb.append(",");
			}
        }
        sb.append("]");
        Log.i("sb",sb.toString());
        NdefMessage msg = BobNdefMessage.getNdefMsg_from_RTD_TEXT(sb.toString(), false, false);
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

    /*@Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }*/
    
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
    	//Log.i("b",uri.toString());
    	uiControl(uri);
    }
    
    private void uiControl(final Uri uri)
    {
    	Button button = (Button)findViewById(R.id.btn_return);
    	try
    	{
    		JSONArray jsonArray = new JSONArray(uri.toString());
    		for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				String team = jsonObj.getString("队伍名称");
				String info = jsonObj.getString("队伍简介");
				mInfoText.setText("Rev MSG : " + "\n" + "队伍名称: " + team +"\n" +"队伍简介: "+ info );
    		}
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
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mNfcAdapter != null) 
			mNfcAdapter.disableForegroundDispatch(this);
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
    
    //自创Adapter
    public class MyAdapter extends SimpleAdapter {
        
        Map<Integer, Boolean> map; 
        
        LayoutInflater mInflater;
        
        private List<? extends Map<String, ?>> mList;
        
        public MyAdapter(Context context, List<Map<String, Object>> data,
                        int resource, String[] from, int[] to) {
                super(context, data, resource, from, to);
                map = new HashMap<Integer, Boolean>();
                mInflater = LayoutInflater.from(context);
                mList = data;
                for(int i = 0; i < data.size(); i++) {
                        map.put(i, false);
                } 
        }
        
        @Override
        public int getCount() {
                return mList.size();
        }
        
        public List<Map<String, Object>> getList() {
                return (List<Map<String, Object>>)mList;
        }

        @Override
        public Object getItem(int position) {
                return position;
        }

        @Override
        public long getItemId(int position) {
                return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                        convertView = mInflater.inflate(R.layout.lsv_vote_team_raw, null);
                }
                TextView tN = (TextView) convertView.findViewById(R.id.multiple_team);
                tN.setText((String)mList.get(position).get("team_name"));
                
                TextView tP = (TextView) convertView.findViewById(R.id.multiple_info);
                tP.setText((String)mList.get(position).get("team_info"));
                
                CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.multiple_checkbox);
                
                checkBox.setChecked(map.get(position)); 
                
                return convertView;
        }
        
    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.multiple_checkbox);
        checkBox.toggle();
        int i = flag[position];
        i++;
        flag[position] = i;
        
        mSimpleAdapter.map.put(position, checkBox.isChecked());
        
    }
    
  //提示类
  	public void showToast(String msg){
  		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
