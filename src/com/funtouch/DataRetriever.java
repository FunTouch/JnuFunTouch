package com.funtouch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.funtouch.Speaker;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.funtouch.Cookie;

public class DataRetriever extends Activity{
	public Cookie application ;            //application为全局变量	

	/*public List<Speaker> retrieveAllSpeakers() {
		String url = "http://pyfun.sinaapp.com/test/get";
		List<Speaker> speakerArrayList = new ArrayList<Speaker>();
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();

		try {
			//HttpResponse httpResponse = httpClient.execute(httpGet);
			//HttpEntity httpEntity = httpResponse.getEntity();
			String jsonString = EntityUtils.toString(httpEntity);
			JSONArray jsonArray = new JSONArray(jsonString);
			Speaker speaker;
			
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObj = jsonArray.getJSONObject(i);
				speaker = new Speaker();

				speaker.setName(jsonObj.getString("name"));
				speaker.setPhone(jsonObj.getString("phone"));
				speaker.setValue(jsonObj.getInt("value"));

				speakerArrayList.add(speaker);

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

		return speakerArrayList;
	}*/
	
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
			
			Log.i("b", code);	
			
			if (code.equals("200"))      //注册成功
				return 200;
			if (code.equals("401"))      //用户名已存在
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
				application.getInstance().setCookie(res);     //把cookie信息保存到application
				application.getInstance().setName(name);
				return 200;
			}		
			//JSONObject jsonObj = jsonArray.getJSONObject(1);
			//String res = jsonArray.getString(0);
			Log.i("login", code);	
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
	
	//创建活动
		public int createAct(String cookie,String name, String info, String time, String place,String type,String org,String actor,String limit){
	
			String url = "http://pyfun.sinaapp.com/act/create";
			
			//打包JSON字符串
			StringBuffer sb = new StringBuffer();  	
			sb.append("{"+"\"cookie\":"+"\""+cookie+"\""+","+"\"data\":");
			sb.append("{"+"\"name\":"+"\""+name+"\""+","+"\"info\":"+"\""+info+"\""+","+"\"time\":"+"\""+time+"\""+","
					+"\"place\":"+"\""+place+"\""+","+"\"type\":"+"\""+type+"\""+","+"\"org\":"+"\""+org+"\""+
					","+"\"actor\":"+"\""+actor+"\""+","+"\"limit\":"+"\""+limit+"\""+"}");
			sb.append("}");                 			
			
			//POST活动信息到URL
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
				
				Log.i("act", code);	
				
				if (code.equals("200"))      //创建成功
					return 200;
				if (code.equals("420"))      //创建失败
					return 420;
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
		
		//获取活动信息并添加到Speaker
		public List<Speaker> retrieveAllAct(String cookie) {
			String url = "http://pyfun.sinaapp.com/act/myact";
			List<Speaker> speakerArrayList = new ArrayList<Speaker>();
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
						Speaker speaker;
						speaker = new Speaker();
						speaker.setCode("null");
						speakerArrayList.add(speaker);
					}
					else{
					JSONArray jsonArray = new JSONArray(object.getString("result"));
					Speaker speaker;
				
					for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonObj = jsonArray.getJSONObject(i);
					speaker = new Speaker();

					speaker.setCode(code);
					speaker.setName(jsonObj.getString("name"));
					speaker.setInfo(jsonObj.getString("info"));
					speaker.setTime(jsonObj.getString("time"));
					speaker.setPlace(jsonObj.getString("place"));
					speaker.setType(jsonObj.getString("type"));
					speaker.setOrg(jsonObj.getString("org"));
					speaker.setActor(jsonObj.getString("actor"));
					speaker.setLimit(jsonObj.getString("limit"));

					speakerArrayList.add(speaker);
					}

					}
				}
				else if(code.equals("420")||code.equals("404"))
				{
					Speaker speaker;
					speaker = new Speaker();
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
