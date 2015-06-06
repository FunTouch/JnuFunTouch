package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class VoteOLDetail extends MenuHavingActivity implements OnItemClickListener{
	public Cookie application ;
	private SimpleAdapter adapter;
	private List<Map<String, String>> VoteDetail = new ArrayList<Map<String, String>>();
	private Map<String,String> Team = new HashMap<String, String>();
	private ImageButton btnSubmit = null;
	private TextView name,info,time, type, place, actor, org, limit,rest = null;
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	private String act_id = null;
	private ListView lv_vote_team = null;
	private List<Act> listAct;
	private int[] flag = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private String vote_id = null;
    private int vote_limit = 0;
	private List<Team> listTeam = new ArrayList<Team>();
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	private MyAdapter mSimpleAdapter ;
	
	protected void onCreate(Bundle savedInstanceState) {
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vote_ol_detail);
		
		Intent intent1 = getIntent();
		act_id = intent1.getStringExtra("act_id");
		//listAct = dataRetriever.getEnrollActDetail(act_id);
		lv_vote_team = (ListView)findViewById(R.id.lsv_vote_act_team);
		btnSubmit = (ImageButton)findViewById(R.id.btn_vote_act_detail_submit);
		name = (TextView)findViewById(R.id.vote_act_detail_name);
		info = (TextView)findViewById(R.id.vote_act_detail_info);
		time = (TextView)findViewById(R.id.vote_act_detail_time);
		type = (TextView)findViewById(R.id.vote_act_detail_type);
		place = (TextView)findViewById(R.id.vote_act_detail_place);
		actor = (TextView)findViewById(R.id.vote_act_detail_actor);
		org = (TextView)findViewById(R.id.vote_act_detail_org);
		limit = (TextView)findViewById(R.id.vote_act_detail_limit);
		rest = (TextView)findViewById(R.id.vote_act_detail_rest);
		List<DetailsInfo> objectList = (List<DetailsInfo>) getIntent().getSerializableExtra("ListObject");
		VoteDetail = objectList.get(0).getEnrollDetails();
		Team = VoteDetail.get(0);
		
		name.setText(Team.get("name"));
		info.setText(Team.get("info"));
		limit.setText(Team.get("limit"));
		time.setText(Team.get("time"));
		type.setText(Team.get("type"));
		place.setText(Team.get("place"));
		actor.setText(Team.get("actor"));
		org.setText(Team.get("org"));
		rest.setText(Team.get("rest"));
		
		listTeam = dataRetriever.seeVoteAdmin(cookie,act_id);
		vote_limit =  Integer.parseInt(Team.get("limit").toString());
		
		getData();		//获取投票列表
		
		mSimpleAdapter = new MyAdapter(this, listData, R.layout.lsv_vote_team_raw,
				new String[] {"team_name", "team_info"},
				new int[] {R.id.multiple_team, R.id.multiple_info});
		
		lv_vote_team.setAdapter(mSimpleAdapter);
        lv_vote_team.setOnItemClickListener(this);
        
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
        				if(q > vote_limit)
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
        			intent.setClass(VoteOLDetail.this, SeeVote.class);
        			intent.putExtra("act_id", act_id);
        			intent.putExtra("authority", "user");
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
  			Map<String, String> tmp = new HashMap<String, String>();
  			Team tl = it.next();		
  			tmp.put("team_name", tl.getTeam_name());
  			tmp.put("team_info", tl.getTeam_info());
  			listData.add(tmp);
  		}
  	}
  	
  //自创Adapter
    public class MyAdapter extends SimpleAdapter {
        
        Map<Integer, Boolean> map; 
        
        LayoutInflater mInflater;
        
        private List<? extends Map<String, ?>> mList;
        
        public MyAdapter(Context context, List<Map<String, String>> data,
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
  		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
  	}

}
