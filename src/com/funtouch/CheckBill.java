package com.funtouch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBill extends MenuHavingActivity {
	public Cookie application ;
	private Button btnCheck = null;
	private TextView tv_bill = null;
	private EditText edt_doornum = null;
	private DataRetriever dataRetriever = new DataRetriever();
	String cookie = application.getInstance().getCookie();
	private String bill = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_bill);
		
		btnCheck = (Button) findViewById(R.id.btn_check);
		tv_bill = (TextView) findViewById(R.id.tv_bill);
		edt_doornum = (EditText) findViewById(R.id.edt_doornum);
		
		btnCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!edt_doornum.getText().toString().isEmpty())
				{
					bill = dataRetriever.getBill(Integer.parseInt(edt_doornum.getText().toString()), cookie);
					if (!bill.equals("failed"))
					{
						tv_bill.setText(bill+"");
					}
					else
						showToast("查询失败!");
				}
			}
		});
		
		
	}
	
	//提示类
  	public void showToast(String msg){
  		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
  	}
}
