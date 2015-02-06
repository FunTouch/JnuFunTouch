package com.funtouch;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

public class UpdateInfo extends Activity{
	public Cookie application ; 
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectAll()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
     StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
     
  // TODO Auto-generated method stub
  		super.onCreate(savedInstanceState);
  		setContentView(R.layout.update_info);
  		
  		
  		
  		
  		
	}
	

}
