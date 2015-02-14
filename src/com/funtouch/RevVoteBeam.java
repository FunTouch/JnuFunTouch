package com.funtouch;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.funtouch.VoteBeam.MyAdapter;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Bytes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RevVoteBeam extends Activity implements OnItemClickListener{
	NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	public Cookie application ; 
	private String vote_id = null;
    private String act_id = null;
    private String limit ;
    private int[] flag = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private ListView lv_vote_team = null;
    private MyAdapter mSimpleAdapter;
	private List<Map<String, Object>> teams = null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rev_vote_beam);
        
        //lv_vote_team = (ListView)findViewById(R.id.lv_vote_team1);
        teams = new ArrayList<Map<String, Object>>();
        //btnOLVote = (Button)findViewById(R.id.btn_ol_vote);
        
     // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
        		new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        
        if(cookie == null)
        {
        	showToast("请先登陆!");
        	Intent intent = new Intent();
			intent.setClass(RevVoteBeam.this, Login.class);
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
    	teams.clear();
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
    	try
    	{
    		JSONObject object = new JSONObject(uri.toString());
    		act_id = object.getString("act_id");
    		limit = object.getString("limit");
    		String team = object.getString("team");
    		JSONArray jsonArray = new JSONArray(team);
    		
    		for (int i = 0; i < jsonArray.length(); i++) {  			
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Map<String, Object> tmp = new HashMap<String, Object>();
				String teamName = jsonObj.getString("team_name");
				String teamInfo = jsonObj.getString("team_info");
				vote_id = jsonObj.getString("vote_id");
				tmp.put("team_name", teamName);
				tmp.put("team_info", teamInfo);
				tmp.put("vote_id",vote_id);
				teams.add(tmp);
    		}
    		
    		mSimpleAdapter = new MyAdapter(this, teams, R.layout.lsv_vote_team_raw,
    				new String[] {"team_name", "team_info"},
    				new int[] {R.id.multiple_team, R.id.multiple_info});
           
            
            showDialog();
    	}
    	catch (JSONException e) {
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
    
    public void showDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("请勾选投票队伍");
        builder.setCancelable(true);
        builder.setPositiveButton("提交", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				int q = 0;
        		int flag1 = 0;
        		for(int i = 0; i < teams.size(); i++)
        		{
        			int j = flag[i];
        			if( j%2 == 1)
        			{
        				q++;
        				if(q > Integer.parseInt(limit))
        				{
        					showToast("超过投票限制!");
        					break;
        				}
        				else{
        					vote_id = (String)teams.get(i).get("vote_id");
        					flag1 = dataRetriever.postVote(cookie,act_id,vote_id);
        					Log.i("flag1",Integer.toString(flag1));
        				//if(flag1 == 200)
           					//showToast("投票成功");
        				if(flag1 == 430)
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
        			showToast("投票成功");
        			Intent intent = new Intent();
        			intent.setClass(RevVoteBeam.this, SeeVote.class);
        			intent.putExtra("act_id", act_id);
        			startActivity(intent);
        			finish();
        		}
			}
        	
        });
        
        builder.setNegativeButton("取消", null);
        
        LayoutInflater layout = LayoutInflater.from(RevVoteBeam.this);
        View dialogView  = layout.inflate(R.layout.dialog_teams, null);
        lv_vote_team =(ListView)dialogView.findViewById(R.id.lv_vote_team1);
        lv_vote_team.setAdapter(mSimpleAdapter);
        lv_vote_team.setOnItemClickListener(this);
        
        builder.setView(dialogView);
        builder.show();
    	
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
