package com.funtouch.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

public class Restful {
	private String host = "http://funoftouch.sinaapp.com/api/v1.0/";
	
	private String CONTENT_TYPE = "application/json";
	
	private HttpClient httpClient = new DefaultHttpClient();
	
	
	/**
	 * 
	 * @info 使用http GET方法
	 * @param path:资源路径
	 * @param auth:认证信息
	 * @return json对象封装的返回结果
	 */
	public JSONObject get(String path, String auth){
		
		JSONObject res = new JSONObject();
		
		String url = host + path; 
		
		HttpGet httpGet = new HttpGet(url);
		
		httpGet.setHeader("Authorization", "Basic " + getb64(auth));
		httpGet.setHeader("Content-Type", CONTENT_TYPE);
		httpGet.setHeader("Accept", CONTENT_TYPE);

		HttpResponse httpResponse;
		
		try {
			httpResponse = httpClient.execute(httpGet);
			
			HttpEntity httpEntity = httpResponse.getEntity();

			String json_str = EntityUtils.toString(httpEntity);
			
			//int code = httpResponse.getStatusLine().getStatusCode();
	
			res = new JSONObject(json_str);
			
			//res.put("code", String.valueOf(code));
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public JSONObject post(String path, String auth, String json_data){
		
		JSONObject res = new JSONObject();
		
		String url = host + path; 
		
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeader("Authorization", "Basic " + getb64(auth));
		httpPost.setHeader("Content-Type", CONTENT_TYPE);
		httpPost.setHeader("Accept", CONTENT_TYPE);

		HttpResponse httpResponse;
		
		try {
			
			StringEntity entity = new StringEntity(json_data, "utf-8");
			
			httpPost.setEntity(entity);
			
			httpResponse = httpClient.execute(httpPost);
			
			HttpEntity httpEntity = httpResponse.getEntity();

			String json_str = EntityUtils.toString(httpEntity);
			
			//int code = httpResponse.getStatusLine().getStatusCode();
			
			res = new JSONObject(json_str);
			
			//res.put("code", String.valueOf(code));
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public JSONObject put(String path, String auth, String json_data){
		
		JSONObject res = new JSONObject();
		
		String url = host + path; 
		
		HttpPut httpPut = new HttpPut(url);
		
		httpPut.setHeader("Authorization", "Basic " + getb64(auth));
		httpPut.setHeader("Content-Type", CONTENT_TYPE);
		httpPut.setHeader("Accept", CONTENT_TYPE);

		HttpResponse httpResponse;
		
		try {
			
			StringEntity entity = new StringEntity(json_data, "utf-8");
			
			httpPut.setEntity(entity);
			
			httpResponse = httpClient.execute(httpPut);
			
			HttpEntity httpEntity = httpResponse.getEntity();

			String json_str = EntityUtils.toString(httpEntity);
			
			//int code = httpResponse.getStatusLine().getStatusCode();
			
			res = new JSONObject(json_str);
			
			//res.put("code", String.valueOf(code));
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	public JSONObject delete(String path, String auth){
		
		JSONObject res = new JSONObject();
		
		String url = host + path; 
		
		HttpDelete httpDelete = new HttpDelete(url);
		
		httpDelete.setHeader("Authorization", "Basic " + getb64(auth));
		httpDelete.setHeader("Content-Type", CONTENT_TYPE);
		httpDelete.setHeader("Accept", CONTENT_TYPE);

		HttpResponse httpResponse;
		
		try {
			httpResponse = httpClient.execute(httpDelete);
			
			HttpEntity httpEntity = httpResponse.getEntity();

			String json_str = EntityUtils.toString(httpEntity);
			
			//int code = httpResponse.getStatusLine().getStatusCode();
			
			res = new JSONObject(json_str);
			
			//res.put("code", String.valueOf(code));
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}


	
	public String getb64(String str){
		return Base64.encodeToString(str.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP);
	}
	
	

}
