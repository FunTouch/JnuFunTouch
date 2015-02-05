package com.funtouch;

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
    private static final int MESSAGE_SENT = 1;
    private List<Team> listTeam = new ArrayList<Team>();
    private int limit ;
    public Cookie application ; 
    private ListView lv_vote_team = null;
    private MyAdapter mSimpleAdapter;
    private int[] flag = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
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
        Log.i("limit",intent1.getStringExtra("limit"));
        act_id = intent1.getStringExtra("act_id");  
        lv_vote_team = (ListView)findViewById(R.id.lv_vote_team);
        List<TeamListInfo> objectList = (List<TeamListInfo>)getIntent().getSerializableExtra("teamlist");
        listTeam = objectList.get(0).getTeamList();		 //获得intent传过来的listTeam
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        btnReturn = (Button)findViewById(R.id.btn_return);
        
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
            showToast("NFC is not available on this device.");
        } else {
            // Register callback to set NDEF message
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        
        
        btnReturn.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		finish();
         	}
        });
        
        btnSubmit.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		int q = 0;
        		int flag1 = 0;
        		int flag2 = 0;
        		for(int i = 0; i < listTeam.size(); i++)
        		{
        			int j = flag[i];
        			if( j%2 == 1)
        			{
        				q++;
        				if(q > limit)
        				{
        					showToast("超过投票限制!");
        					flag2 = 1;
        					break;
        				}
        				
        			}
        		}
        		if(flag2 == 0){
        			for(int i = 0; i < listTeam.size(); i++)
        			{
        				int j = flag[i];
        				if(q == 0)
        				{
                			showToast("请选择队伍!");
                			break;
        				}
        				if( j%2 == 1)
        				{
        					vote_id = listTeam.get(i).getVote_id();
        					flag1 = dataRetriever.postVote(cookie,act_id,vote_id);
        					if(flag1 == 430)
        					{
        						showToast("票数已用完");
        						break;
        					}
        				
        				}
        			}
        		}
        		
        		if(flag1 == 200)
        		{
        			showToast("投票成功");
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
        sb.append("{"+"\"act_id\":"+"\""+act_id+"\""+","+"\"limit\":"+"\""+limit+"\""+","+"\"team\":");
        sb.append("[");
        for(int i = 0; i < listTeam.size(); i++)
        {
        	sb.append("{"+"\"team_name\":"+"\""+listTeam.get(i).getTeam_name()+"\""+","+"\"team_info\":"+"\""+listTeam.get(i).getTeam_info()+"\""+","
        			+"\"vote_id\":"+"\""+listTeam.get(i).getVote_id()+"\""+"}");
        	if(i < listTeam.size() - 1)
			{
				sb.append(",");
			}
        }
        sb.append("]"+"}");
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
                Toast.makeText(getApplicationContext(), "投票信息发送成功!", Toast.LENGTH_LONG).show();
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

}
