package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SeeActivity extends Activity {

	private static ArrayList<Map<String,String>> groupData = new ArrayList<Map<String,String>>();
	private static ArrayList<ArrayList<Map<String,Object>>> childData = new ArrayList<ArrayList<Map<String,Object>>>();
	private String[] groupStrings = new String[]{"校级学生组织","院级学生组织"};
	private ExpandableListView expandListView;
	private TextView tv_header;
	private ImageView iv_header;
	
	private int screenWidth;
	private int screenHeight;
	/**
	 *当前打开的父节点
	 */
	private int expand_Group_position=-1;
	
	
	private int groupCount;
	
	private RelativeLayout header;
	public String[] organsDetails = new String[]{
			"共青团暨南大学珠海校区工作委员会" + '\n' +
			"微博：@暨大珠海校区团工委" + '\n' +
			"微信公众号：暨南大学珠海校区团工委" + '\n' +
			"官方网站：http://zh1.jnu.edu.cn/youth/new/" + '\n' +
			"官方邮箱： ojdtgw@jnu.edu.cn" + '\n' +
			"组织介绍：共青团暨南大学珠海校区工作委员会，简称“团工委”，成立于2003年，"
			+ "原与共青团暨南大学珠海学院委员会合署办公，"
			+ "统筹珠海学院各年级和校本部各学院在珠海就读各年级的团工作；"
			+ "2010年校区化全面实行后，团工委开始统筹珠海校区国际商学院、人文学院、"
			+ "电气信息学院、翻译学院、四海书院和校本部各学院在珠海就读各年级的团工作，"
			+ "目前共设有秘书处、组织部、人力资源部、宣传部、编辑部、社会实践部、"
			+ "公共关系部、学术科技部、国旗护卫队、形象大使团队、青年志愿者协会、"
			+ "暨南人网络联盟12个部门。" + '\n' +
			"品牌活动：" + '\n' +
			"·团日活动 " + '\n' +
			"·志愿者活动 " + '\n' +
			"·三下乡 " + '\n' +
			"·挑战杯 	" + '\n' ,
			"暨南大学学生会珠海校区执行委员会" + '\n' +
			"微博：@暨大珠海校区学生会" + '\n' +
			"微信公众号：暨南大学学生会" + '\n' +
			"官方网站：http://www.zh4211.net/" + '\n' +
			"官方邮箱：zh4211bjb@163.com" + '\n' +
			"组织介绍：暨南大学学生会珠海校区执行委员会，始终秉承“服务暨南人的宗旨”，"
			+ "认真贯彻4211精神，内练素质，外塑形象，"
			+ "独立自主地开展各种有益学生的工作活动，营造良好的校园文化氛围，"
			+ "构造高品位的校园生活。" + '\n' +
			"品牌活动：" + '\n' +
			"模拟联合国大会" + '\n' +
			"中国文化节" + '\n' +
			"哈罗喂" + '\n' +
			"心理剧大赛" + '\n' +
			"新生杯拔河赛" + '\n' +
			"校区篮球联赛" + '\n' +
			"校区足球联赛" + '\n' +
			"女生节" + '\n' +
			"侨生杯" + '\n',
			"暨南大学学生社团联合会珠海分会" + '\n' +
			"微博：@暨大社联珠海分会" + '\n' +
			"微信公众号：暨南大学学生社团联合会珠海分会" + '\n' +
			"组织介绍：暨大社联珠海分会是珠海暨南园社团文化的引领者、"
			+ "规范社团活动的监督体。社联有各大品牌活动（北极光歌唱大赛，"
			+ "社团文化艺术节，社团探秘会，新社团策划大赛，创意市集等）和40个品牌社团。"
			+ "为暨大学子送上精彩纷呈的校园活动，丰富同学们的校园生活。" + '\n' +
			"品牌活动：" + '\n' +
			"社团探秘会" + '\n' +
			"社团巡礼" + '\n' +
			"北极光歌唱大赛" + '\n' +
			"创意市集" + '\n' ,
			"暨南大学珠海校区学生代表大会" + '\n' +
			"微博：@暨大珠海校区学代会" + '\n' +
			"微信公共号：" + '\n' +
			"官方网站：http://www.zh4211.net/a/a/xuedai/" + '\n' +
			"组织介绍：学代会是一个代表学生的组织，它没有大型的活动、没有铺天盖地的宣传、"
			+ "没的繁复的部门类别和上下级关系，它有的，是平等互助的关系；有的，"
			+ "是一群带着热情为学生服务的常委会以及秘书处成员们。"
			+ "即使我们的建议不能够在短时间内得到实现，我们仍然会不断努力。"
			+ "我们都是学生，我们都有相同的愿望和期许。" + '\n' ,
			"暨南大学珠海校区勤工助学" + '\n' +
			"微博：@暨南大学珠海校区勤工助学" + '\n' +
			"微信公共号：" + '\n' +
			"官方邮箱：gwhb203@sina.com" + '\n' +
			"官方网站：http://zh1.jnu.edu.cn/qinggong/" + '\n' +
			"组织介绍：暨南大学珠海学院勤工助学成立于1998年, "
			+ "2009年学校实行校区化管理后，正式改名为暨南大学珠海校区勤工助学。"
			+ "自成立以来，勤工助学在珠海校区学生工作办公室直接领导、"
			+ "学校领导和各部门指导老师的关怀下，"
			+ "以及全体勤工助学同学的共同努力下迅速成长，"
			+ "建立了较为完善的管理系统及规章制度。"
			+ "珠海校区勤工助学先后成功创立了29个勤工助学服务部门,开辟校内岗位数百个，"
			+ "并创建了勤助网站和勤助宣传栏，以及勤助期刊。"
			+ "勤助各个部门活跃在校区各个角落，和同学生活息息相关，"
			+ "为校区的发展做出了一定的贡献。 " + '\n' ,
			"暨南大学职业发展协会珠海分会" + '\n' +
			"微博：@暨南大学职业发展协会珠海分会" + '\n' +
			"微信公众号：" + '\n' +
			"官方网站：http://www.jnuca.com/" + '\n' +
			"官方邮箱：lovejnuca@163.com" + '\n' +
			"组织介绍：“职业发展协会（Career Development Association），"
			+ "是暨南大学学生就业指导中心指导下，旨在培养与提高大学生自我认识与规划、"
			+ "协调与发展、适应社会、提高择业技能的学生社团。"
			+ "职业发展协会宣扬职业生涯规划理念，倡导认识自己、美化人生、自我设计、"
			+ "自律自立、学练成材，并致力于为全校学生提供一个适应就业市场需要和大学生自身职业发展需求的，"
			+ "提高自身就业竞争力和适应能力创造交流、协作、提升的统一平台。”"
			+ "——《毕业生就业指导手册》。"
			+ " 暨南大学珠海校区大学生职业发展协会热忱欢迎广大在校生以及企业参与职协的事业，"
			+ "共创辉煌的明天!" + '\n' +
			"品牌活动：" + '\n' +
			"简历设计大赛" + '\n' +
			"职业规划大赛" + '\n' +
			"公务员指导月" + '\n' +
			"勇往职前" + '\n' ,
			"暨南大学电气信息学院团委学生会" + '\n' +
			"微博：@暨南大学电气信息学院团委学生会" + '\n' +
			"微信公众号：" + '\n' +
			"官方网站：http://eic.jnu.edu.cn/twxsh/index.htm" + '\n' +
			"官方邮箱：jnueisu@163.com" + '\n' ,
			"暨大翻译学院团委学生会" + '\n' +
			"微博：@暨南大学翻译学院" + '\n' +
			"微信公众号：" + '\n' +
			"官方网站：http://jndx.syiou.com/ " + '\n' ,
			"暨大国际商学院团委学生会 " + '\n' +
			"微博：@暨大国际商学院团委学生会" + '\n' +
			"微信公众号：" + '\n' +
			"官方网站：http://gjsxy.jnu.edu.cn/" + '\n' ,
			"暨南大学人文学院团委学生会" + '\n' +
			"微博：@暨大人文学院团委学生会" + '\n' +
			"微信公众号：" + '\n' +
			"官方网站：http://rwxy.jnu.edu.cn/" + '\n' 
	};
	public String[][] organs = new String[][]{{"共青团暨大珠海校区工作委员会",
	"暨大学生会珠海校区执行委员会","暨大学生社团联合会珠海分会",
	"暨大珠海校区学生代表大会","暨大珠海校区勤工助学",
	"暨大职业发展协会珠海分会"},{"暨大电气信息学院团委学生会",
	"暨大翻译学院团委学生会","暨大国际商学院团委学生会","暨大人文学院团委学生会"}};
	
	public int[][] organs_Logos = new int[][]{{R.drawable.tuangongwei,
			R.drawable.xueshenghui,R.drawable.shelian,
			R.drawable.xuedai,R.drawable.qinzhu,
			R.drawable.zhixie},{R.drawable.dianqixinxixueyuan,
			R.drawable.fanyixueyan,R.drawable.guoshang,
			R.drawable.renwenxueyuan}};
	public void onCreate(Bundle saveBundle){
		super.onCreate(saveBundle);
		setContentView(R.layout.see_activity);
		initData();
		groupCount = groupData.size();
		
		WindowManager wManager = getWindowManager();
		DisplayMetrics dm = new DisplayMetrics();
		wManager.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		
		tv_header = (TextView)findViewById(R.id.tv_header);
		iv_header = (ImageView)findViewById(R.id.iv_header);
		
		/**
		 * 滑动列表时在上方显示父节点的header
		 */
		header = (RelativeLayout)findViewById(R.id.header);
		
		/**
		 * 配置Header
		 */
		configureHeader();
		
		header.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				tv_header.setVisibility(View.GONE);
				iv_header.setVisibility(View.GONE);
				expandListView.collapseGroup(expand_Group_position);
			}
			
		});
		
		expandListView = (ExpandableListView)findViewById(R.id.expandListView);
		expandListView.setGroupIndicator(null);
		
		LinearLayout.LayoutParams lp_expandListView = new LinearLayout.LayoutParams(    
				(6*screenWidth)/7, LayoutParams.WRAP_CONTENT);  
		lp_expandListView.leftMargin = screenWidth/7;
		
		expandListView.setLayoutParams(lp_expandListView);
		
		expandListView.setAdapter(new ExpandablexpandListViewaAdapter());
		
		/**
		 * 监听父节点关闭事件
		 */
		expandListView.setOnGroupCollapseListener(new OnGroupCollapseListener(){

			@Override
			public void onGroupCollapse(int groupPosition) {
				
				if(AllGroupCollapsed()){
					header.setVisibility(View.GONE);
				}
			}
			
		});
		
		expandListView.setOnChildClickListener(new OnChildClickListener() {


			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(SeeActivity.this,OrganDetailsActivity.class);
				if(0 == groupPosition){
					intent.putExtra("itemNumber", childPosition);
				}else if(1 == groupPosition){
					intent.putExtra("itemNumber", childPosition + 6);	
				}
				startActivity(intent);
				return false;
			}
		});
		
		/**
		 * 通过setOnScrollListener来监听列表上下滑动时item显示和消失的事件
		 */
		
		expandListView.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

					  int groupId = ExpandableListView.getPackedPositionGroup(expandListView.getExpandableListPosition(firstVisibleItem));  
					  //当前第一行的子ID  
					  int firRowId = ExpandableListView.getPackedPositionChild(expandListView.getExpandableListPosition(firstVisibleItem));  
					  //当前第二行的子ID  
					  int SecRowId = ExpandableListView.getPackedPositionChild(expandListView.getExpandableListPosition(firstVisibleItem+1));  
					  String groupTitle=((groupId>-1)?((Map)groupData.get(groupId)).get("group").toString():"");  
					  //如果第一行和第二行都是组header，不需要显示  
					  if(firRowId==-1 && SecRowId==-1){  
						  if(groupId == 0){
						   header.setVisibility(View.GONE);
						  }else {
						  tv_header.setVisibility(View.GONE);
						  iv_header.setVisibility(View.GONE);
						  }
					  }  
					  else {  
					
					  if( groupId == 0 && (firRowId == -1 && SecRowId >= 0)){
						  header.setVisibility(View.GONE);
								 }
					  else if(SecRowId>=0 && firRowId==-1){
						   tv_header.setVisibility(View.GONE);
						   iv_header.setVisibility(View.GONE);     
					   }  
					   else{
						   expand_Group_position = groupId;
						   header.setVisibility(View.VISIBLE);  
						   tv_header.setVisibility(View.VISIBLE);
						   iv_header.setVisibility(View.VISIBLE);
						   tv_header.setText(groupTitle);  
						   iv_header.setImageResource(R.drawable.black_right_arrow);
					   }
				   }
				} 
		});
		
	}
	
	
	
	class ExpandablexpandListViewaAdapter extends BaseExpandableListAdapter{

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childData.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			AbsListView.LayoutParams lp_child = new AbsListView.LayoutParams(    
			LayoutParams.MATCH_PARENT, 150);
			
			View childView = LayoutInflater.from(SeeActivity.this).
			inflate(R.layout.listview_child_item, null);
			
			RelativeLayout rlyt_child = (RelativeLayout)childView.
					findViewById(R.id.rlyt_child);
			ImageView iv_child = (ImageView)childView.
            		findViewById(R.id.iv_child);
            TextView tv_child = (TextView)childView.
            		findViewById(R.id.tv_child);   
            iv_child.setImageResource(organs_Logos[groupPosition][childPosition]);
            rlyt_child.setLayoutParams(lp_child);    
            tv_child.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);    
            tv_child.setText((String)childData.get(groupPosition).get(childPosition).get("childText"));    
            
			return rlyt_child;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return childData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return groupData.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupData.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater) getSystemService(SeeActivity.LAYOUT_INFLATER_SERVICE);
			View v = inflater.inflate(R.layout.listview_group, null);
			ExpandableListView.LayoutParams lp_group = new ExpandableListView.LayoutParams(    
			LayoutParams.MATCH_PARENT, 100);  
			RelativeLayout rlyt_group = (RelativeLayout) v.findViewById(R.id.rlyt_group);
			rlyt_group.setLayoutParams(lp_group);
			TextView tv_group = (TextView) v.findViewById(R.id.tv_group);
			tv_group.setText(groupData.get(groupPosition).get("group"));
			return rlyt_group;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition,
				int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	public void initData(){
		groupData.clear();
		childData.clear();
		for(int i=0; i<groupStrings.length;i++){
			 Map<String,String> map = new HashMap<String,String>();
			 map.put("group", groupStrings[i]);
			 groupData.add(map);
		}
		for(int i=0;i<groupStrings.length;i++){
			ArrayList<Map<String,Object>> child = new ArrayList<Map<String,Object>>();
			for(int j=0; j<organs[i].length;j++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("childText", organs[i][j]);
				map.put("childLogo", organs_Logos[i][j]);
				child.add(map);
			}
			childData.add(child);
		}
	}
	
	public void configureHeader(){
		LinearLayout.LayoutParams lp_header = new LinearLayout.
				LayoutParams((6*screenWidth)/7,100);
		lp_header.leftMargin = screenWidth/7;
		header.setLayoutParams(lp_header);
	}
	
	public boolean AllGroupCollapsed(){
		for(int groupPosition = 0; groupPosition < groupCount; groupPosition++){
			if(expandListView.isGroupExpanded(groupPosition)){
				return false;
			}
		}
				return true;
	}

}
