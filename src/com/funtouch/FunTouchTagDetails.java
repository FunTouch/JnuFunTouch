package com.funtouch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FunTouchTagDetails extends Activity {
	TextView tv_details = null;
	String[] detailsDatas = null;
	int selectedId = 0;
	String whatIsThis = null;
	String howToBuy = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funtouch_nfc_tag_details);
		init();
		selectedId = getIntent().getIntExtra("selectedId", 0);
		tv_details.setText(detailsDatas[selectedId]);
	}
	
	public void init(){
		whatIsThis = getString(R.string.whatisthis);
		howToBuy = getString(R.string.howtobuy);
		tv_details = (TextView)findViewById(R.id.tv_details);
		detailsDatas = new String[]{
		"无纸动态宣传海报标签:\n"
	   + whatIsThis + '\n' + "活动举办方的福音！\n"
	   +"告别纸质传单的浪费，告别一成不变静态的黑白，告别设计师看到成品后的痛心疾首，"
	   + "告别彩印的昂贵，告别派传单时的辛酸。现在，您只需要一个宣传标签，"
	   + "并将您的传单、海报导入其中，参与者只要用手机轻轻地一touch，"
	   + "就会分毫不差的获得你们全部的线下宣传。\n"
	   + '\n' + howToBuy + '\n'
	   +"点击“去购买”，并将您的传单、海报发送给我们，定制您的专属标签，"
	   + "标签制作好后我们会尽快与您取得联系。"
       ,	
       "自动开启Wifi标签:\n"
       + whatIsThis + '\n' + "店铺老板的福音！\n"
       +"告别一次次被询问密码的烦恼，告别突然忘记密码的窘迫。"
       + "现在，您只需要一个wifi标签，并将您的wifi名称和密码存入其中，"
       + "挂在任何您想让顾客看到的地方，顾客在自主用手机touch到您的wifi的同时，"
       + "也可以达到一定的宣传效果。\n"
       + '\n' + howToBuy + '\n'
       +"点击“去购买”，并将您的wifi名称和密码发送给我们，"
       + "定制您的专属标签，标签制作好后我们会尽快与您取得联系。"
       ,	
       "个人登记信息标签:\n"
       + whatIsThis + '\n' + "社交恐惧症患者的福音！\n"
       +"告别自我介绍时的绞尽脑汁，告别信息登记时的前后不一，告别字体“见光死”的噩梦，"
       + "告别半推半就欲拒还迎的搭讪。现在，您只需要一个名片标签，"
       + "里面保存着所有你可以让别人知道的个人信息，只要贴近拥有NFC功能的手机，"
       + "就可以将您的个人信息按照您的个人意愿传递给对方，让您把握交流的主动权。\n"
       + '\n' + howToBuy + '\n'
       +"点击“去购买”，填写您的个人信息设置您的并提交，定制您的专属标签，"
       + "标签制作好后我们会尽快与您取得联系。"
       ,	
       "开会自动静音并签到标签:\n"
       + whatIsThis + '\n' + "强迫症患者的福音！\n"
       +"告别夜晚被电话骚扰的困扰，告别会议中手机突然响铃的尴尬。"
       + "现在，您只需要一个自定义模式标签，"
       + "在其中设置好您希望手机所处的模式状态（如飞行模式、夜间模式…）"
       + "对方只需要用手机轻轻一touch，就可以满足您变态的控制欲，"
       + "让您轻松掌控自己或活动现场其他人的手机模式。\n"
       + '\n' + howToBuy + '\n'
       +"点击“去购买”，自定义您需要的手机模式，定制您的专属标签，"
       + "标签制作好后我们会尽快与您取得联系。"
       ,	
       "图书馆新书信息标签:\n"
       +"\t详情：图书馆有新书信息，图书馆有新的通知等只需要记录于标签之中，"
       + "学生只需要将手机靠近标签，就可以获取图书馆新书的信息并保存于手机之中。\n"
       +"\t使用方式：使用时，直接挂于特定位置即可，学生的手机靠近就能获取信息。\n"
       +"\t购买方式：您只需要将新书信息，通知信息等发送给我们，我们将它放入标签之中，完成之后就会联系您来取标签。"
       ,
       "兼职、迁移等信息标签:\n"
       +"\t详情：这个标签可以代替常见于贴在墙上的兼职或者办公地点迁移等纸质的信息，"
       + "并且可以更便捷的存储于手机之中。\n"
       +"\t使用方式：使用者只需要将手机靠近标签，就可以获取兼职、迁移等信息。\n"
       +"\t购买方式：您只需要将兼职、迁移等信息发送给我们，我们将它放入标签之中，完成之后就会联系您来取标签。"
		};
	}
}
