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

public class DataRetriever extends Activity{
	public Cookie application ;            

	//查看所有可报名活动简介(GET)
	public List<Act> getEnrollAct() {
		String url = "http://pyfun.sinaapp.com/act/enroll/get/simple/all";
		List<Act> actArrayList = new ArrayList<Act>();
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject object = new JSONObject(jsonString);
			String code = object.getString("code");
			if (code.equals("200"))
			{
				Act speaker;
				String res = object.getString("result");
				JSONArray jsonArray = new JSONArray(res);
				for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObj = jsonArray.getJSONObject(i);
				speaker = new Act();

				speaker.setAct_id(jsonObj.getString("act_id"));
				speaker.setInfo(jsonObj.getString("act_info"));
				speaker.setName(jsonObj.getString("name"));
				speaker.setRest(jsonObj.getString("rest"));
				speaker.setTime(jsonObj.getString("time"));
				speaker.setCode(code);

				actArrayList.add(speaker);

				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actArrayList;
	}
	
	//查看一个可报名活动详情(GET)
	public List<Act> getEnrollActDetail(String act_id) {
			String url = "http://pyfun.sinaapp.com/act/enroll/get/one/act/"+act_id;
			List<Act> actArrayList = new ArrayList<Act>();
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = new DefaultHttpClient();

			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject object = new JSONObject(jsonString);
				String code = object.getString("code");
				if (code.equals("200"))
				{
					Act speaker;
					String res = object.getString("result");
					JSONObject result = new JSONObject(res);
					
					speaker = new Act();

					speaker.setAct_id(result.getString("act_id"));
					speaker.setInfo(result.getString("act_info"));
					speaker.setName(result.getString("name"));
					speaker.setEnrollLimit(result.getString("enroll_limit"));
					speaker.setNeedInfo(result.getString("need_info"));
					speaker.setActor(result.getString("actor"));
					speaker.setNum(result.getString("num"));
					speaker.setPlace(result.getString("place"));
					speaker.setTime(result.getString("time"));
					speaker.setOrg(result.getString("org"));
					speaker.setType(result.getString("type"));
					speaker.setUser_id(result.getString("user_id"));
					speaker.setCode(code);

					actArrayList.add(speaker);

					
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return actArrayList;
		}
	
	
	//查看所有可投票活动简介(GET)
	public List<Act> getVoteAct() {
			String url = "http://pyfun.sinaapp.com/act/vote/get/all";
			List<Act> actArrayList = new ArrayList<Act>();
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = new DefaultHttpClient();

			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject object = new JSONObject(jsonString);
				String code = object.getString("code");
				if (code.equals("200"))
				{
					Act speaker;
					String res = object.getString("result");
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
					speaker.setUser_id(jsonObj.getString("user_id"));
					speaker.setActor(jsonObj.getString("actor"));
					speaker.setVoteLimit(jsonObj.getString("vote_limit"));
					speaker.setCode(code);

					actArrayList.add(speaker);

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return actArrayList;
		}
		
	//注册
	public int regist(String name, String password, String mailbox, String userclass,String phone){
		
		
		String url = "http://pyfun.sinaapp.com/regist";
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("mailbox", mailbox));
		params.add(new BasicNameValuePair("grade", userclass));
		params.add(new BasicNameValuePair("tel", phone));
		
		HttpPost httpPost = new HttpPost(url);
		
		HttpClient httpClient = new DefaultHttpClient();
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject result = new JSONObject(jsonString);
			String code = result.getString("code");
				
			
			if (code.equals("200"))      //注册成功
				return 200;
			if (code.equals("401"))      //注册失败
				return 401;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//登陆
	public int login(String name, String password){
		
		String url = "http://pyfun.sinaapp.com/login";
		
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("password", password));	
			
		HttpPost httpPost = new HttpPost(url);
		
		HttpClient httpClient = new DefaultHttpClient();
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject object = new JSONObject(jsonString);
			String code = object.getString("code");
			if (code.equals("200"))       //登陆成功
			{
				JSONObject result = new JSONObject(object.getString("result"));
				String res = result.getString("cookie");
				application.getInstance().setCookie(res);     //保存cookie到application
				application.getInstance().setName(name);
				return 200;
			}		
			//JSONObject jsonObj = jsonArray.getJSONObject(1);
			//String res = jsonArray.getString(0);
			//Log.i("b", application.getInstance().getCookie());				
			if (code.equals("410"))      //无效用户名
				return 410;
			if (code.equals("411"))      //密码错误
				return 411;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	//创建活动
		public int createAct(String cookie,String name, String info, String time, String place,String type,String org,String actor){
	
			String url = "http://pyfun.sinaapp.com/act/create";
			
			//打包JSON数据
			StringBuffer sb = new StringBuffer();  	
			sb.append("{"+"\"cookie\":"+"\""+cookie+"\""+","+"\"data\":");
			sb.append("{"+"\"name\":"+"\""+name+"\""+","+"\"info\":"+"\""+info+"\""+","+"\"time\":"+"\""+time+"\""+","
					+"\"place\":"+"\""+place+"\""+","+"\"type\":"+"\""+type+"\""+","+"\"org\":"+"\""+org+"\""+
					","+"\"actor\":"+"\""+actor+"\""+"}");
			sb.append("}");                 			
			
			//POST���Ϣ��URL
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("post", sb.toString()));
			
			HttpPost httpPost = new HttpPost(url);
			
			HttpClient httpClient = new DefaultHttpClient();
			try {

				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject result = new JSONObject(jsonString);
				String code = result.getString("code");
				
				
				if (code.equals("200"))      //创建成功
					return 200;
				if (code.equals("420"))      //创建失败
					return 420;
				if (code.equals("404"))      //未登录
					return 404;
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return 0;
		}
		
		//添加活动信息到Speaker
		public List<Act> retrieveAllAct(String cookie) {
			String url = "http://pyfun.sinaapp.com/act/myact";
			
			List<Act> speakerArrayList = new ArrayList<Act>();
			HttpPost httpPost = new HttpPost(url);
			HttpClient httpClient = new DefaultHttpClient();
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("cookie", cookie));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				
				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject object = new JSONObject(jsonString);		
				//String res = object.getString("result");
				String code = object.getString("code");
				//Log.i("Act", code);
				
				if(code.equals("200"))
				{			
					String res = object.getString("result");
					if(res.equals("[]"))
					{
						Act speaker;
						speaker = new Act();
						speaker.setCode("null");
						speakerArrayList.add(speaker);
					}
					else{
					JSONArray jsonArray = new JSONArray(object.getString("result"));
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
					speaker.setUser_id(jsonObj.getString("user_id"));

					speakerArrayList.add(speaker);
					}

					}
				}
				else if(code.equals("420")||code.equals("404"))
				{
					Act speaker;
					speaker = new Act();
					speaker.setCode(code);
					speakerArrayList.add(speaker);
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return speakerArrayList;
		}
		
		//创建投票
		public int createVote(String cookie, String act_id, int limit, ArrayList<Map<String, Object>> teams){
			
			String url = "http://pyfun.sinaapp.com/act/vote/add";
			int length = teams.size();
					
					
			//打包JSON数据
			StringBuffer sb = new StringBuffer();  	
			sb.append("{"+"\"cookie\":"+"\""+cookie+"\""+","+"\"data\":");
			sb.append("{"+"\"act_id\":"+"\""+act_id+"\""+","+"\"limit\":"+"\""+limit+"\""+","+"\"team\":"+"[");
			if(length!=0)
			{
				for(int i = 0; i < length; i++ )
				{
					sb.append("{"+"\"team_name\":"+"\""+teams.get(i).get("team_name")+"\""+","+"\"team_info\":"+"\""+teams.get(i).get("team_info")+"\""+"}");
					if(i < length - 1)
					{
						sb.append(",");
					}
				}
			}
			sb.append("]"+"}"+"}");                 			
					
			//POST投票信息到URL
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("post", sb.toString()));
					
			HttpPost httpPost = new HttpPost(url);
					
			HttpClient httpClient = new DefaultHttpClient();
			try {

				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject result = new JSONObject(jsonString);
				String code = result.getString("code");
							
						
				if (code.equals("200"))      //创建成功
					return 200;
				if (code.equals("420"))      //创建失败
					return 420;
				if (code.equals("404"))      //未登录
					return 404;
				
			} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			return 0;
		}
	
	//查看投票
	public List<Team> seeVote(String cookie, String act_id){
					
		String url = "http://pyfun.sinaapp.com/act/vote/list";                 			
						
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("cookie", cookie));
		params.add(new BasicNameValuePair("act_id", act_id));
		List<Team> teamlistArrayList = new ArrayList<Team>();
							
		HttpPost httpPost = new HttpPost(url);
						
		HttpClient httpClient = new DefaultHttpClient();
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			
			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject object = new JSONObject(jsonString);		
			String code = object.getString("code");
			
				
			if(code.equals("200"))
			{			
				String res = object.getString("result");
				JSONObject result = new JSONObject(res);
				String team = result.getString("team");
				String limit = result.getString("limit");
				if(team.equals("[]"))
				{
					Team teamlist;
					teamlist = new Team();
					teamlist.setCode("null");
					teamlistArrayList.add(teamlist);
				}
				else{
						
				JSONArray jsonArray = new JSONArray(result.getString("team"));
				Team teamlist;
				
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					teamlist = new Team();

					teamlist.setCode(code);
					teamlist.setLimit(limit);
					teamlist.setTeam_name(jsonObj.getString("team_name"));
					teamlist.setTeam_info(jsonObj.getString("team_info"));
					teamlist.setTicket(jsonObj.getInt("ticket"));
					teamlist.setVote_id(jsonObj.getString("vote_id"));

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
				
						
		} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
								// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
								// TODO Auto-generated catch block
			e.printStackTrace();
		}
							
		return teamlistArrayList;
	}
	
	//投一票
	public int postVote(String cookie, String act_id, String vote_id){
				
		String url = "http://pyfun.sinaapp.com/act/vote/ticket";						                 			
						
		//POST投票信息到URL
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("cookie", cookie));
		params.add(new BasicNameValuePair("act_id", act_id));
		params.add(new BasicNameValuePair("vote_id", vote_id));
						
		HttpPost httpPost = new HttpPost(url);
						
		HttpClient httpClient = new DefaultHttpClient();
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject result = new JSONObject(jsonString);
			String code = result.getString("code");	
							
			if (code.equals("200"))      //投票成功
				return 200;
			if (code.equals("430"))      //票数已经用完
				return 430;
					
		} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
							// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
							// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return 0;
	}
	
	//删除投票
		public int delVote(String cookie, String act_id){
					
			String url = "http://pyfun.sinaapp.com/act/vote/del";						                 			
							
			//POST投票信息到URL
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("cookie", cookie));
			params.add(new BasicNameValuePair("act_id", act_id));
							
			HttpPost httpPost = new HttpPost(url);
							
			HttpClient httpClient = new DefaultHttpClient();
			try {

				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject result = new JSONObject(jsonString);
				String code = result.getString("code");	
				
								
				if (code.equals("200"))      //删除成功
					return 200;
				if (code.equals("404"))      //未登陆
					return 404;
						
			} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
								// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
								// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			return 0;
	}
	
		
	//修改密码
	public int changePass(String cookie, String old_pass, String new_pass){
							
		String url = "http://pyfun.sinaapp.com/usr/update/password";						                 			
									
		//POST投票信息到URL
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("cookie", cookie));
		params.add(new BasicNameValuePair("old_password", old_pass));
		params.add(new BasicNameValuePair("new_password", new_pass));
									
		HttpPost httpPost = new HttpPost(url);
									
		HttpClient httpClient = new DefaultHttpClient();
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject result = new JSONObject(jsonString);
			String code = result.getString("code");	
						
										
			if (code.equals("200"))      //修改成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("441"))      //原密码错误
				return 441;
								
		} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
								// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
									// TODO Auto-generated catch block
			e.printStackTrace();
		}
									
		return 0;
	}
	
	//修改个人信息
	public int updateInfo(String cookie, Map<String,String> info){
				
		String url = "http://pyfun.sinaapp.com/usr/update/info";
						
						
		//打包JSON数据
		int flag = 0;
		StringBuffer sb = new StringBuffer();  	
		sb.append("{"+"\"cookie\":"+"\""+cookie+"\""+","+"\"data\":"+"{");
		if(!info.get("name").toString().equals("null"))
		{
			flag++;
			sb.append("\"name\":"+"\""+info.get("name").toString()+"\""+",");
		}
		if(!info.get("sno").toString().equals("null"))
		{
			flag++;
			sb.append("\"sno\":"+"\""+info.get("sno").toString()+"\""+",");
		}
		if(!info.get("mailbox").toString().equals("null"))
		{
			flag++;
			sb.append("\"mailbox\":"+"\""+info.get("mailbox").toString()+"\""+",");
		}
		if(!info.get("grade").toString().equals("null"))
		{
			flag++;
			sb.append("\"grade\":"+"\""+info.get("grade").toString()+"\""+",");
		}
		if(!info.get("phone").toString().equals("null"))
		{
			flag++;
			sb.append("\"phone\":"+"\""+info.get("phone").toString()+"\""+",");
		}
		if(!info.get("qq").toString().equals("null"))
		{
			flag++;
			sb.append("\"qq\":"+"\""+info.get("qq").toString()+"\""+",");
		}
		if(flag!=0)
		{
			sb.deleteCharAt(sb.length()-1);
		}
		
		sb.append("}"+"}");                 			
						

		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("post", sb.toString()));
						
		HttpPost httpPost = new HttpPost(url);
						
		HttpClient httpClient = new DefaultHttpClient();
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject result = new JSONObject(jsonString);
			String code = result.getString("code");
								
							
			if (code.equals("200"))      //修改成功
				return 200;
			if (code.equals("440"))      //有无效的列名
				return 440;
			if (code.equals("404"))      //未登录
				return 404;
					
		} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
						// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
							// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		return 0;
	}
	
	//获取个人信息
	public Map<String,String> getInfo(String cookie){
					
		String url = "http://pyfun.sinaapp.com/usr/info/list";
							                			
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("cookie", cookie));
		Map<String,String> info = new HashMap<String,String>();
							
		HttpPost httpPost = new HttpPost(url);
							
		HttpClient httpClient = new DefaultHttpClient();
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject object = new JSONObject(jsonString);		
			String code = object.getString("code");		
				
			if(code.equals("200"))
			{			
				String res = object.getString("result");
				JSONObject result = new JSONObject(res);
				String qq = result.getString("qq");
				String name = result.getString("name");
				String grade = result.getString("grade");
				String sno = result.getString("sno");
				String mailbox = result.getString("mailbox");
				String phone = result.getString("phone");
				info.put("qq", qq);
				info.put("name", name);
				info.put("grade", grade);
				info.put("sno", sno);
				info.put("mailbox", mailbox);
				info.put("phone", phone);
				info.put("code", code);
				return info;
			}
			
			else if(code.equals("404"))
			{
				info.put("code", code);
				return info;
			}
						
			} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
							// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
								// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
			return info;
		}
	
	//创建报名
		public int createSignUp(String cookie, String act_id, String limit, String info){
								
			String url = "http://pyfun.sinaapp.com/act/enroll/create";						                 			
										
			//POST投票信息到URL
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("cookie", cookie));
			params.add(new BasicNameValuePair("act_id", act_id));
			params.add(new BasicNameValuePair("limit", limit));
			params.add(new BasicNameValuePair("info", info));
										
			HttpPost httpPost = new HttpPost(url);
										
			HttpClient httpClient = new DefaultHttpClient();
			try {

				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject result = new JSONObject(jsonString);
				String code = result.getString("code");	
							
											
				if (code.equals("200"))      //创建成功
					return 200;
				if (code.equals("404"))      //未登陆
					return 404;
				if (code.equals("451"))      //已创建过报名
					return 451;
				if (code.equals("454"))      //info存在无效值
					return 454;
									
			} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
									// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
										// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
			return 0;
		}
		
	//查看报名
		public List<Enroll> seeEnroll(String act_id){
			
			String url = "http://pyfun.sinaapp.com/act/enroll/list";						                 			
										
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("act_id", act_id));
			List<Enroll> enrolllistArrayList = new ArrayList<Enroll>();
										
			HttpPost httpPost = new HttpPost(url);
										
			HttpClient httpClient = new DefaultHttpClient();
			try {

				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				String jsonString = EntityUtils.toString(httpEntity);
				JSONObject object = new JSONObject(jsonString);
				String code = object.getString("code");	
				
										
				if (code.equals("200"))      //成功
				{
					String res = object.getString("result");
					JSONObject result = new JSONObject(res);
					String num = result.getString("num");
					String limit = result.getString("limit");
					String enroll = result.getString("enroll_list");
					if(enroll.equals("[]"))
					{
						Enroll enrolllist;
						enrolllist = new Enroll();
						enrolllist.setCode("null");
						enrolllistArrayList.add(enrolllist);
					}
					else{
							
					JSONArray jsonArray = new JSONArray(result.getString("enroll_list"));
					Enroll enrolllist;
					
					for (int i = 0; i < jsonArray.length(); i++) {

						JSONObject jsonObj = jsonArray.getJSONObject(i);
						enrolllist = new Enroll();

						enrolllist.setCode(code);
						enrolllist.setLimit(limit);
						
						enrolllist.setName(jsonObj.getString("name"));
						enrolllist.setGrade(jsonObj.getString("grade"));
						enrolllist.setMailbox(jsonObj.getString("mailbox"));
						enrolllist.setNum(num);
						enrolllist.setPhone(jsonObj.getString("phone"));
						enrolllist.setQQ(jsonObj.getString("qq"));
						enrolllist.setSno(jsonObj.getString("sno"));
						//Log.i("id",jsonObj.getString("vote_id"));

						enrolllistArrayList.add(enrolllist);
					}

					}
				}
				else if(code.equals("453"))  //没有创建报名
				{
					Enroll enrolllist;
					enrolllist = new Enroll();
					enrolllist.setCode(code);
					enrolllistArrayList.add(enrolllist);
				}
									
			} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
									// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
										// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
			return enrolllistArrayList;
		}
		
	//报名
	public int signUp(String cookie, String act_id){
										
		String url = "http://pyfun.sinaapp.com/act/enroll/add";						                 			
												
		//POST投票信息到URL
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("cookie", cookie));
		params.add(new BasicNameValuePair("act_id", act_id));
												
		HttpPost httpPost = new HttpPost(url);
												
		HttpClient httpClient = new DefaultHttpClient();
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			String jsonString = EntityUtils.toString(httpEntity);
			JSONObject result = new JSONObject(jsonString);
			String code = result.getString("code");	
																	
			if (code.equals("200"))      //报名成功
				return 200;
			if (code.equals("404"))      //未登陆
				return 404;
			if (code.equals("450"))      //已报过名
				return 450;
			if (code.equals("452"))      //名额已满
				return 452;
								
		} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
					// TODO Auto-generated catch block
		e.printStackTrace();
		}
												
		return 0;
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
