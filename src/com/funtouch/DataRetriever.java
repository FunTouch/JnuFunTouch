package com.funtouch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.funtouch.Act;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.funtouch.Cookie;
import com.funtouch.util.Restful;

public class DataRetriever extends Activity{
	public Cookie application ;            

	//查看所有可报名活动简介(GET)
	public List<Act> getEnrollAct() {
		String url = "http://pyfun.sinaapp.com/act/enroll/get/simple/all";
		List<Act> actArrayList = new ArrayList<Act>();
		Restful restful = new Restful();

		try {
			JSONObject result = restful.get("activity/registration/?"+"act_id=0", "");
			
			String code = result.getString("code");
			if (code.equals("200"))
			{
				Act speaker;
				String res = result.getString("registarions");
				JSONArray jsonArray = new JSONArray(res);
				for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObj = jsonArray.getJSONObject(i);
				speaker = new Act();

				speaker.setAct_id(jsonObj.getString("act_id"));
				speaker.setInfo(jsonObj.getString("info"));
				speaker.setName(jsonObj.getString("name"));
				speaker.setRest(jsonObj.getInt("limit")-jsonObj.getInt("num"));
				speaker.setTime(jsonObj.getString("time"));
				speaker.setCode(code);

				actArrayList.add(speaker);

				}
			}
			else if(code.equals("404"))
			{
				Act speaker;
				speaker = new Act();
				speaker.setCode(code);
				actArrayList.add(speaker);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return actArrayList;
	}
	
	//查看一个可报名活动详情(GET)
	public List<Act> getEnrollActDetail(String act_id) {
		String url = "http://pyfun.sinaapp.com/act/enroll/get/one/act/"+act_id;
		List<Act> actArrayList = new ArrayList<Act>();
		Restful restful = new Restful();
		try {
			JSONObject result = restful.get("activity/registration/?act_id="+act_id, "");
			//Log.i("res",object.toString());
			String code = result.getString("code");
			if (code.equals("200"))
				{
					Act speaker;					
					speaker = new Act();

					speaker.setAct_id(result.getString("act_id"));
					speaker.setInfo(result.getString("info"));
					speaker.setName(result.getString("name"));
					speaker.setEnrollLimit(result.getString("limit"));
					speaker.setNeedInfo(result.getString("default_property_names"));
					speaker.setActor(result.getString("actor"));
					speaker.setNum(result.getString("num"));
					speaker.setPlace(result.getString("place"));
					speaker.setTime(result.getString("time"));
					speaker.setOrg(result.getString("org"));
					speaker.setType(result.getString("type"));
					//speaker.setUser_id(result.getString("user_id"));
					speaker.setCode(code);

					actArrayList.add(speaker);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return actArrayList;
	}
	
	
	//查看所有可投票活动简介(GET)
	public List<Act> getVoteAct(String token) {
		String url = "http://pyfun.sinaapp.com/act/vote/get/all";
		List<Act> actArrayList = new ArrayList<Act>();
		Restful restful = new Restful();
		try {
			JSONObject result = restful.get("activity/voting/",token+":");
			String code = result.getString("code");
			if (code.equals("200"))
			{
				Act speaker;
				String res = result.getString("votes");
				JSONArray jsonArray = new JSONArray(res);
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					speaker = new Act();

					speaker.setAct_id(jsonObj.getString("act_id"));
					speaker.setInfo(jsonObj.getString("info"));
					speaker.setName(jsonObj.getString("name"));
					speaker.setPlace(jsonObj.getString("place"));
					speaker.setTime(jsonObj.getString("time"));
					speaker.setOrg(jsonObj.getString("org"));
					speaker.setType(jsonObj.getString("type"));
					//speaker.setUser_id(jsonObj.getString("user_id"));
					speaker.setActor(jsonObj.getString("actor"));
					speaker.setVoteLimit(jsonObj.getString("limit"));
					speaker.setRest(jsonObj.getInt("rest_tickets"));
					speaker.setCode(code);

					actArrayList.add(speaker);

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return actArrayList;
		}
		
	//注册
	public int regist(String name, String password, String mailbox, String grade,String phone){
		
		Restful restful = new Restful();
		
		//HttpPost httpPost = new HttpPost(url);
		
		//HttpClient httpClient = new DefaultHttpClient();
		try {
			JSONObject json_data = new JSONObject();
			json_data.put("username", name);
			json_data.put("password", password);
			json_data.put("mailbox", mailbox);
			json_data.put("grade", grade);
			json_data.put("tel", phone);
			//httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			//HttpResponse httpResponse = httpClient.execute(httpPost);
			//HttpEntity httpEntity = httpResponse.getEntity();

			//String jsonString = EntityUtils.toString(httpEntity);

			JSONObject result = restful.post("register",  "", json_data.toString());

			//JSONObject result = new JSONObject(jsonString);
			String code = result.getString("code");
				
			
			if (code.equals("200"))      //注册成功
				return 200;
			if (code.equals("400"))      //注册失败
				return 400;
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//登陆
	public int login(String name, String password){
		
		Restful restful = new Restful();
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("password", password));	
			

		try {
			
			JSONObject result = restful.get("token",  name+":"+password);

			String code = result.getString("code");
			if (code.equals("200"))       //登陆成功
			{
				
				String token = result.getString("token");
				application.getInstance().setCookie(token);     //保存cookie到application
				application.getInstance().setName(name);
				//application.getInstance().setUser_id(name);
				return 200;
			}		
				
			if (code.equals("412"))      //认证错误
				return 412;

			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//创建活动
	public int createAct(String token,String name, String info, String time, String place,String type,String org,String actor){
	
		String url = "http://pyfun.sinaapp.com/act/create";
		Restful restful = new Restful();
		
		try {
			JSONObject json_data = new JSONObject();
			json_data.put("name", name);
			json_data.put("info", info);
			json_data.put("time", time);
			json_data.put("place", place);
			json_data.put("type", type);
			json_data.put("org", org);
			json_data.put("actor", actor);
							
			JSONObject result = restful.post("activity/", token+":", json_data.toString());
			String code = result.getString("code");			
				
			if (code.equals("200"))      //创建成功
				return 200;
			if (code.equals("420"))      //创建失败
				return 420;
			if (code.equals("404"))      //未登录
				return 404;

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return 0;
	}
	
	//查看一个活动基本信息
	public Act seeAct(String token,String act_id){
		
		Restful restful = new Restful();
		Act act = new Act();
		
		try {
								
			JSONObject result = restful.get("activity/"+act_id, token+":");
			String code = result.getString("code");	
			act.setAct_id(result.getString("act_id"));
			act.setName(result.getString("name"));
			act.setOrg(result.getString("org"));
			act.setTime(result.getString("time"));
			act.setPlace(result.getString("place"));
			act.setType(result.getString("type"));
			act.setActor(result.getString("actor"));
			act.setInfo(result.getString("info"));
					
			if (code.equals("200"))      
			{
				act.setCode(code);
				return act;
			}			
			else    					
			{
				act.setCode(code);
				return act;
			}

		}catch (JSONException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return act;
	}
	
	//修改个人信息
	public int updateAct(String token, String act_id, Map<String,String> info){
					
		Restful restful = new Restful();					
		//打包JSON数据  
		JSONObject json_data = new JSONObject();                			
							
		try {
			if(!info.get("name").toString().equals("null"))
				json_data.put("name", info.get("name").toString());
			if(!info.get("time").toString().equals("null"))
				json_data.put("time", info.get("time").toString());
			if(!info.get("info").toString().equals("null"))
				json_data.put("info", info.get("info").toString());
			if(!info.get("place").toString().equals("null"))
				json_data.put("place", info.get("place").toString());
			if(!info.get("actor").toString().equals("null"))
				json_data.put("actor", info.get("actor").toString());
			if(!info.get("org").toString().equals("null"))
				json_data.put("org", info.get("org").toString());
			if(!info.get("type").toString().equals("null"))
				json_data.put("type", info.get("type").toString());
				
			JSONObject result = restful.put("activity/"+act_id, token+":", json_data.toString());
			String code = result.getString("code");
								
				if (code.equals("200"))      //修改成功
					return 200;
				if (code.equals("440"))      //有无效的列名
					return 440;
				if (code.equals("404"))      //未登录
					return 404;

		}catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}						
		return 0;
	}
		
	//添加活动信息到Speaker
	public List<Act> retrieveAllAct(String token) {
		String url = "http://pyfun.sinaapp.com/act/myact";
		Restful restful = new Restful();
			
		List<Act> speakerArrayList = new ArrayList<Act>();
		
		try {
	
				JSONObject result = restful.get("activity/", token+":");
				String code = result.getString("code");
				Log.i("act",result.toString());
		
				if(code.equals("200"))
				{			
					String res = result.getString("activities");
					if(res.equals("[]"))
					{
						Act speaker;
						speaker = new Act();
						speaker.setCode("null");
						speakerArrayList.add(speaker);
					}
					else{
					JSONArray jsonArray = new JSONArray(result.getString("activities"));
					Act speaker;
				
					for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					speaker = new Act();

					speaker.setCode(code);
					speaker.setName(jsonObj.getString("name"));
					speaker.setInfo(jsonObj.getString("info"));
					speaker.setTime(jsonObj.getString("time"));
					speaker.setPlace(jsonObj.getString("place"));
					speaker.setType(jsonObj.getString("type"));
					speaker.setOrg(jsonObj.getString("org"));
					speaker.setActor(jsonObj.getString("actor"));
					speaker.setAct_id(jsonObj.getString("act_id"));
					//speaker.setUser_id(jsonObj.getString("user_id"));

					speakerArrayList.add(speaker);
					}

					}
				}
				else if(code.equals("412")||code.equals("404")||code.equals("414"))
				{
					Act speaker;
					speaker = new Act();
					speaker.setCode(code);
					speakerArrayList.add(speaker);
				}

			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return speakerArrayList;
		}
		
	//创建投票
	public int createVote(String token, String act_id, int limit, ArrayList<Map<String, Object>> teams){
			
		String url = "http://pyfun.sinaapp.com/act/vote/add";
		int length = teams.size();
		Restful restful = new Restful();
					
					
		//打包JSON数据
		JSONObject json_data = new JSONObject();               			
				
		//POST投票信息到URL
		try {
			json_data.put("limit", limit);
			JSONArray team = new JSONArray();		
			if(length!=0)
			{
				for(int i = 0; i < length; i++ )
				{
					JSONObject ateam = new JSONObject();
					ateam.put("team_name", teams.get(i).get("team_name"));
					ateam.put("team_info", teams.get(i).get("team_info"));
					team.put(ateam);
				}
				json_data.put("teams", team);
			}
			JSONObject result = restful.post("activity/"+act_id+"/vote/", token+":", json_data.toString());
			String code = result.getString("code");
											
			if (code.equals("200"))      //创建成功
				return 200;
			if (code.equals("420"))      //创建失败
				return 420;
			if (code.equals("404"))      //未登录
				return 404;

		}catch (JSONException e) {
				// TODO Auto-generated catch block
		e.printStackTrace();
		}			
		return 0;
	}
	
	//查看一个活动的投票(管理员)
	public List<Team> seeVoteAdmin(String token, String act_id){
					
		String url = "http://pyfun.sinaapp.com/act/vote/list";   
		Restful restful = new Restful();

		List<Team> teamlistArrayList = new ArrayList<Team>();
							
		try {
			JSONObject result = restful.get("activity/"+act_id+"/vote/", token+":");
			String code = result.getString("code");
			if(code.equals("200"))
			{			
				String team = result.getString("teams");
				String limit = result.getString("limit");
				if(team.equals("[]"))
				{
					Team teamlist;
					teamlist = new Team();
					teamlist.setCode("null");
					teamlistArrayList.add(teamlist);
				}
				else{
						
				JSONArray jsonArray = new JSONArray(result.getString("teams"));
				Team teamlist;
				
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					teamlist = new Team();

					teamlist.setCode(code);
					teamlist.setLimit(limit);
					teamlist.setTeam_name(jsonObj.getString("team_name"));
					teamlist.setTeam_info(jsonObj.getString("team_info"));
					teamlist.setTicket(jsonObj.getInt("tickets"));
					teamlist.setVote_id(jsonObj.getString("team_id"));
					teamlist.setSupporter(jsonObj.getString("supporter"));

					teamlistArrayList.add(teamlist);
				}

				}
			}
			else if(code.equals("412")||code.equals("431"))
			{
				Team teamlist;
				teamlist = new Team();
				teamlist.setCode(code);
				teamlistArrayList.add(teamlist);
			}

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return teamlistArrayList;
	}
	
	//查看一个活动的投票(用户)
	public List<Team> seeVoteUser(String token, String act_id){
						
		String url = "http://pyfun.sinaapp.com/act/vote/list";   
		Restful restful = new Restful();
		List<Team> teamlistArrayList = new ArrayList<Team>();
								
		try {
			JSONObject result = restful.get("activity/"+act_id+"/voting/", token+":");
			String code = result.getString("code");
			if(code.equals("200"))
			{			
				String team = result.getString("teams");
				String limit = result.getString("limit");
				if(team.equals("[]"))
				{
					Team teamlist;
					teamlist = new Team();
					teamlist.setCode("null");
					teamlistArrayList.add(teamlist);
				}
				else{
						
				JSONArray jsonArray = new JSONArray(result.getString("teams"));
				Team teamlist;
					
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					teamlist = new Team();

					teamlist.setCode(code);
					teamlist.setLimit(limit);
					teamlist.setTeam_name(jsonObj.getString("team_name"));
					teamlist.setTeam_info(jsonObj.getString("team_info"));
					teamlist.setTicket(jsonObj.getInt("tickets"));
					teamlist.setVote_id(jsonObj.getString("team_id"));
					teamlist.setRest_tickets(result.getString("rest_tickets"));
					teamlistArrayList.add(teamlist);
				}
				}
			}
			else if(code.equals("431")||code.equals("404"))
			{
				Team teamlist;
				teamlist = new Team();
				teamlist.setCode(code);
				teamlistArrayList.add(teamlist);
			}

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return teamlistArrayList;
	}
	
	//投一票
	public int postVote(String token, String act_id, String team_id){
				
		String url = "http://pyfun.sinaapp.com/act/vote/ticket";	
		Restful restful = new Restful();
						
		try {

			JSONObject result = restful.post("activity/"+act_id+"/voting/"+team_id, token+":", "");
			String code = result.getString("code");	
							
			if (code.equals("200"))      //投票成功
				return 200;
			if (code.equals("430"))      //票数已经用完
				return 430;

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	return 0;
	}
	
	//删除投票(管理员)
	public int delVote(String token, String act_id){
					
		String url = "http://pyfun.sinaapp.com/act/vote/del";	
		Restful restful = new Restful();
							
		try {

			JSONObject result = restful.delete("activity/"+act_id+"/vote/", token+":");
			String code = result.getString("code");	
						
			if (code.equals("200"))      //删除成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;

		}catch (JSONException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}					
		return 0;
	}
	
		
	//修改密码
	public int changePass(String token, String old_pass, String new_pass){
							
		String url = "http://pyfun.sinaapp.com/usr/update/password";
		Restful restful = new Restful();
									
		try {
			JSONObject json_data = new JSONObject();
			json_data.put("old_password", old_pass);
			json_data.put("new_password", new_pass);
			JSONObject result = restful.put("users/password", token+":", json_data.toString());
			String code = result.getString("code");	

			if (code.equals("200"))      //修改成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("441"))      //原密码错误
				return 441;
								
		}catch (JSONException e) {
									// TODO Auto-generated catch block
			e.printStackTrace();
		}
									
		return 0;
	}
	
	//修改个人信息
	public int updateInfo(String token, Map<String,String> info){
				
		String url = "http://pyfun.sinaapp.com/usr/update/info";
		Restful restful = new Restful();					
		//打包JSON数据  
		JSONObject json_data = new JSONObject();                			
						
		try {

			if(!info.get("name").toString().equals("null"))
				json_data.put("name", info.get("name").toString());
			if(!info.get("sno").toString().equals("null"))
				json_data.put("sno", info.get("sno").toString());
			if(!info.get("mailbox").toString().equals("null"))
				json_data.put("mailbox", info.get("mailbox").toString());
			if(!info.get("grade").toString().equals("null"))
				json_data.put("grade", info.get("grade").toString());
			if(!info.get("phone").toString().equals("null"))
				json_data.put("tel", info.get("phone").toString());
			if(!info.get("qq").toString().equals("null"))
				json_data.put("qq", info.get("qq").toString());
			if(!info.get("about_me").toString().equals("null"))
				json_data.put("about_me", info.get("about_me").toString());
			
			JSONObject result = restful.put("users/info", token+":", json_data.toString());
			String code = result.getString("code");
							
			if (code.equals("200"))      //修改成功
				return 200;
			if (code.equals("440"))      //有无效的列名
				return 440;
			if (code.equals("404"))      //未登录
				return 404;

		}catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return 0;
	}
	
	//获取个人信息
	public Map<String,String> getInfo(String token){
					
		String url = "http://pyfun.sinaapp.com/usr/info/list";
		Restful restful = new Restful();

		Map<String,String> info = new HashMap<String,String>();

		try {

			JSONObject result = restful.get("users/"+application.getInstance().getName(), token+":");
			String code = result.getString("code");		
				
			if(code.equals("200"))
			{			
				String qq = result.getString("qq");
				String name = result.getString("name");
				String grade = result.getString("grade");
				String sno = result.getString("sno");
				String mailbox = result.getString("mailbox");
				String phone = result.getString("tel");
				String about_me = result.getString("about_me");
				info.put("qq", qq);
				info.put("about_me", about_me);
				info.put("name", name);
				info.put("grade", grade);
				info.put("sno", sno);
				info.put("mailbox", mailbox);
				info.put("phone", phone);
				info.put("code", code);
				return info;
			}
			
			else if(code.equals("412"))
			{
				info.put("code", code);
				return info;
			}

		}catch (JSONException e) {					// TODO Auto-generated catch block
			e.printStackTrace();
		}						
		return info;
	}
	
	//创建报名
	public int createSignUp(String token, String act_id, String limit, JSONArray info){
								
		String url = "http://pyfun.sinaapp.com/act/enroll/create";
		Restful restful = new Restful();

		try {
			JSONObject json_data = new JSONObject();
			json_data.put("limit", Integer.parseInt(limit));
			json_data.put("default_property_names", info);
			
			JSONObject result = restful.post("activity/"+act_id+"/registration/", token+":", json_data.toString());
			//Log.i("res0",result.toString());
			String code = result.getString("code");				
										
			if (code.equals("200"))      //创建成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("451"))      //已创建过报名
				return 451;
			if (code.equals("454"))      //info存在无效值
				return 454;

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}								
		return 0;
	}
		
	//查看报名
	public List<Enroll> seeEnroll(String token, String act_id){
			
		String url = "http://pyfun.sinaapp.com/act/enroll/list";
								
		Restful restful = new Restful();
		List<Enroll> enrolllistArrayList = new ArrayList<Enroll>();

		try {

			JSONObject result = restful.get("activity/"+act_id+"/registration/", token+":");
			//Log.i("res",result.toString());
			String code = result.getString("code");	
						
			if (code.equals("200"))      		//成功
			{
				String num = result.getString("num");
				String limit = result.getString("limit");
				String enroll = result.getString("registration_user_list");
				if(enroll.equals("[]"))
				{
					Enroll enrolllist;
					enrolllist = new Enroll();
					enrolllist.setCode("null");
					enrolllistArrayList.add(enrolllist);
				}
				else{				
					JSONArray jsonArray = new JSONArray(result.getString("registration_user_list"));
					Enroll enrolllist;
					
					for (int i = 0; i < jsonArray.length(); i++) {

						JSONObject jsonObj = jsonArray.getJSONObject(i);
						enrolllist = new Enroll();

						enrolllist.setCode(code);
						enrolllist.setLimit(limit);		
						enrolllist.setName(jsonObj.getString("username"));
						enrolllist.setGrade(jsonObj.getString("grade"));
						enrolllist.setMailbox(jsonObj.getString("mailbox"));
						enrolllist.setNum(num);
						enrolllist.setPhone(jsonObj.getString("tel"));
						enrolllist.setQQ(jsonObj.getString("qq"));
						//enrolllist.setUser_id(jsonObj.getString("user_id"));
						enrolllist.setSno(jsonObj.getString("sno"));

						enrolllistArrayList.add(enrolllist);
					}

					}
				}
			else if(code.equals("453"))				 //没有创建报名
			{
				Enroll enrolllist;
				enrolllist = new Enroll();
				enrolllist.setCode(code);
				enrolllistArrayList.add(enrolllist);
			}

			}catch (JSONException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return enrolllistArrayList;
	}
		
	//报名
	public int signUp(String token, String act_id){
										
		String url = "http://pyfun.sinaapp.com/act/enroll/add";	
		Restful restful = new Restful();

		try {
			JSONObject result = restful.post("activity/"+act_id+"/registration/enroll/", token + ":", "");
			String code = result.getString("code");	
																	
			if (code.equals("200"))      //报名成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("450"))      //已报过名
				return 450;
			if (code.equals("452"))      //名额已满
				return 452;

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}											
		return 0;
	}
	
	//删除一个活动
	public int deleteAct(String token, String act_id){
											
		String url = "http://pyfun.sinaapp.com/act/del/one/all";	
		Restful restful = new Restful();

		try {

			JSONObject result = restful.delete("activity/"+act_id , token+":");
			String code = result.getString("code");	
																		
			if (code.equals("200"))      //删除成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("432"))      //不是活动的创建者
				return 432;

		}catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}												
		return 0;
	}
	
	//删除一个活动的全部报名
	public int deleteEnroll(String token, String act_id){
												
		String url = "http://pyfun.sinaapp.com/act/del/one/enroll/all";	
		Restful restful = new Restful();

		try {
			JSONObject result = restful.delete("activity/"+act_id+"/registration/", token+":");
			String code = result.getString("code");	
																		
			if (code.equals("200"))      //删除成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("432"))      //不是活动的创建者
				return 432;

		}catch (JSONException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}											
		return 0;
	}
	
	//取消报名
	public int cancelSignUp(String token, String act_id){
													
			String url = "http://pyfun.sinaapp.com/act/del/one/enroll/all";	
			Restful restful = new Restful();

			try {
				JSONObject result = restful.delete("activity/"+act_id+"/registration/enroll/", token+":");
				String code = result.getString("code");	
																			
				if (code.equals("200"))      //删除成功
					return 200;
				if (code.equals("404"))      //未登陆
					return 404;
				if (code.equals("432"))      //不是活动的创建者
					return 432;

			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}											
			return 0;
		}
	
	//删除一个报名的一项
	public int deleteOneSignUp(String token, String act_id,String user_id){
													
		String url = "http://pyfun.sinaapp.com/act/del/enroll/one";	
		Restful restful = new Restful();
															
		try {

			JSONObject result = restful.delete("activity/"+act_id+"/registration/enroll/", token+":");
			Log.i("res1",result.toString());
			String code = result.getString("code");	
																				
			if (code.equals("200"))      //删除成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("432"))      //不是活动的创建者
				return 432;
			if (code.equals("455"))      //user_id错误
				return 455;

		}catch (JSONException e) {				// TODO Auto-generated catch block
		e.printStackTrace();
		}													
		return 0;
	}
	
	//查看自己报名的活动
	public List<Act> seeMySignUp(String token){
		
		String url = "http://pyfun.sinaapp.com/act/get/enroll/my";	
		Restful restful = new Restful();
									
		List<Act> actlistArrayList = new ArrayList<Act>();

		try {

			JSONObject result = restful.get("users/registration/", token+":");
			String code = result.getString("code");				
									
			if (code.equals("200"))      //成功
			{
				String res = result.getString("registrations");
				if(res.equals("[]"))
				{
					Act actlist;
					actlist = new Act();
					actlist.setCode("null");
					actlistArrayList.add(actlist);
				}
				else{
						
				JSONArray jsonArray = new JSONArray(res);
				Act actlist;
				
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					actlist = new Act();

					actlist.setCode(code);
					
					actlist.setName(jsonObj.getString("name"));
					actlist.setAct_id(jsonObj.getString("act_id"));
					actlist.setInfo(jsonObj.getString("info"));
					actlist.setRest(Integer.parseInt(jsonObj.getString("limit"))-Integer.parseInt(jsonObj.getString("num")));
					actlist.setTime(jsonObj.getString("time"));

					actlistArrayList.add(actlist);
				}

				}
			}
			else if(code.equals("404"))  //没有创建报名
			{
				Act actlist;
				actlist = new Act();
				actlist.setCode(code);
				actlistArrayList.add(actlist);
			}

		}catch (JSONException e) {
									// TODO Auto-generated catch block
			e.printStackTrace();
		}
									
		return actlistArrayList;
	}
	
	//查看自己投票的活动
	public List<Act> seeMyVote(String token){
			
		String url = "http://pyfun.sinaapp.com/act/get/enroll/my";	
		Restful restful = new Restful();

		List<Act> actlistArrayList = new ArrayList<Act>();

		try {

			JSONObject result = restful.get("users/voting/", token+":");
			String code = result.getString("code");		
										
			if (code.equals("200"))      //成功
			{
				String res = result.getString("activities");
					if(res.equals("[]"))
					{
						Act actlist;
						actlist = new Act();
						actlist.setCode("null");
						actlistArrayList.add(actlist);
					}
					else{
							
					JSONArray jsonArray = new JSONArray(res);
					Act actlist;
					
					for (int i = 0; i < jsonArray.length(); i++) {

						JSONObject jsonObj = jsonArray.getJSONObject(i);
						actlist = new Act();

						actlist.setCode(code);
						
						actlist.setName(jsonObj.getString("name"));
						actlist.setAct_id(jsonObj.getString("act_id"));
						actlist.setInfo(jsonObj.getString("info"));
						actlist.setTime(jsonObj.getString("time"));

						actlistArrayList.add(actlist);
					}

					}
				}
				else if(code.equals("404"))  //没有创建投票
				{
					Act actlist;
					actlist = new Act();
					actlist.setCode(code);
					actlistArrayList.add(actlist);
				}
			}catch (JSONException e) {
										// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
			return actlistArrayList;
		}
	
	//激活验证码功能
	public int actVali(String token, String act_id){

		Restful restful = new Restful();
																
		try {

			JSONObject result = restful.post("activity/"+act_id+"/use-key", token+":","");
			String code = result.getString("code");	
																				
			if (code.equals("200"))      //删除成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("456"))      //未报名
				return 456;

		}catch (JSONException e) {				// TODO Auto-generated catch block
		e.printStackTrace();
		}													
		return 0;
	}
	
	//激活验证码功能
	public int valiCode(String token, JSONObject data){

		Restful restful = new Restful();
																	
		try {
			JSONObject json_data = new JSONObject();
			json_data.put("username", data.get("username"));
			json_data.put("value", data.get("value"));
			JSONObject result = restful.put("activity/"+data.getString("act_id")+"/key/", token+":",json_data.toString());
			Log.i("res3",result.toString());
			String code = result.getString("code");	
																					
				if (code.equals("200"))      //验证成功
					return 200;
				else if (code.equals("461"))      //无效key
					return 461;

			}catch (JSONException e) {				// TODO Auto-generated catch block
				e.printStackTrace();
			}													
			return 0;
		}
	
	//生成验证码
	public Code genCode(String token, String act_id, String username){

		Restful restful = new Restful();
		Code vali_code = new Code();
																
		try {
			JSONObject json_data = new JSONObject();
			json_data.put("username", username);
			JSONObject result = restful.post("activity/"+act_id+"/key/", token+":",json_data.toString());
			//Log.i("res2",result.toString());
			String code = result.getString("code");	
																			
			if (code.equals("200"))      //成功
			{
				vali_code.setCode(result.getString("value"));
				vali_code.setStatus(result.getString("status"));
				vali_code.setCode(result.getString("code"));
				return vali_code;
			}
			if (code.equals("460"))      //未激活验证码功能
			{
				vali_code.setCode(result.getString("code"));
				return vali_code;
			}

		}catch (JSONException e) {				// TODO Auto-generated catch block
			e.printStackTrace();
		}													
		return vali_code;
	}
	
	//查看自己的验证码
	public List<Code> seeCode(String token){

		Restful restful = new Restful();
		List<Code> listCode = new ArrayList<Code>();
																	
		try {

			JSONObject result = restful.get("users/keys/", token+":");
			Log.i("res2",result.toString());
			String code = result.getString("code");	
																				
			if (code.equals("200"))      //成功
			{
				String res = result.getString("keys");
				if(res.equals("[]"))
				{
					Code codelist;
					codelist = new Code();
					codelist.setCode("null");
					listCode.add(codelist);
				}
				else{
						
				JSONArray jsonArray = new JSONArray(res);
				Code codelist;
				
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					codelist = new Code();

					codelist.setCode(code);
					
					codelist.setName(jsonObj.getString("name"));
					codelist.setAct_id(jsonObj.getString("act_id"));
					codelist.setInfo(jsonObj.getString("info"));
					codelist.setValue(jsonObj.getString("value"));

					listCode.add(codelist);
					}

				}
				return listCode;
			}
			else if (code.equals("460"))      
			{
				Code codelist;
				codelist = new Code();
				codelist.setCode(code);
				listCode.add(codelist);
				return listCode;
				}

			}catch (JSONException e) {				// TODO Auto-generated catch block
				e.printStackTrace();
			}													
			return listCode;
		}
		
	// check the Internet connection
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	

}
