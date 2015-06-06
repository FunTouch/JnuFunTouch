package com.funtouch;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBill extends MenuHavingActivity {
	public Cookie application ;
	private ImageButton btnCheck = null;
	private TextView tv_bill = null;
	private EditText edt_doornum = null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	private String bill = null;
	private static final int TIME_OUT = 0; 
    private static final int SUCCESS = 1; 
    private long temptime = 0;
    // 超时的时限为5秒 
    private static final int TIME_LIMIT = 10000;
    private ProgressDialog pd;
	Timer timer; 
    Thread thread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_bill);
		
		btnCheck = (ImageButton) findViewById(R.id.btn_check);
		tv_bill = (TextView) findViewById(R.id.tv_bill);
		edt_doornum = (EditText) findViewById(R.id.edt_doornum);
		
		btnCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!edt_doornum.getText().toString().isEmpty())
				{
					pd= ProgressDialog.show(CheckBill.this, "查询", "正在查询中，请稍后~");
					thread = new Thread() {  
	                    @Override  
	                    public void run() { 
	                    	 bill = dataRetriever.getBill(Integer.parseInt(edt_doornum.getText().toString()), cookie); 
	                    	 Message msgSuc = new Message(); 
	                         msgSuc.what = SUCCESS; 
	                         handler.sendMessage(msgSuc);
	                    }  
	  
	                };
	                thread.start();
					timer = new Timer(); 
	                timer.schedule(new TimerTask() { 
	                    @Override 
	                    public void run() { 
	                        sendTimeOutMsg(); 
	                    } 
	                }, TIME_LIMIT);
					
					
				}
			}
		});
		
		
	}
	
	private Handler handler =new Handler(){
		   @Override
		   //当有消息发送出来的时候就执行Handler的这个方法
		   public void handleMessage(Message msg){
			   switch (msg.what) { 
			   case TIME_OUT: 
	                //打断线程 
	                thread.interrupt(); 
	                pd.dismiss(); 
	                showToast("查询超时"); 
	                break; 
			   case SUCCESS: 
	                //取消定时器 
	                timer.cancel(); 
	                pd.dismiss(); 
	                if(!bill.equals("failed"))
					{
	                	tv_bill.setText(bill+""+" 度");
					}
	                else
	                	showToast("查询失败!");
	                break; 
	            default: 
	                break; 
			   }
		      super.handleMessage(msg);
		      pd.dismiss();
		      
		   }
	};
	
	//向handler发送超时信息 
    private void sendTimeOutMsg() { 
        Message timeOutMsg = new Message(); 
        timeOutMsg.what = TIME_OUT; 
        handler.sendMessage(timeOutMsg); 
    }
	
	//提示类
  	public void showToast(String msg){
  		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
  	}
}
