package com.funtouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CardTransport extends FragmentActivity {
	private Button btn_Edit;
	private List<Contact> listContact = new ArrayList<Contact>(); ;
	private SimpleAdapter adapter0;
	private Button btn_Transport;
	private Button btn_Confirm;
	ViewPager MyPager = null;
	FragmentPagerAdapter adapter = null;
	Fragment myCardFragment = null;
	Fragment contactsFragment = null;
	ArrayList<Fragment> fragments = null;
	ArrayList<TextView> textViews = null;
	ArrayList<EditText> editTexts = null;
	TextView tv_myCard = null;
	TextView tv_contacts = null;
	TextView tv_name,tv_phone = null;
	Map<String, String> tmp = new HashMap<String, String>();
	private List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardtransport);
		init();
		
	}

	private void init() {
			textViews = new ArrayList<TextView>();
			editTexts = new ArrayList<EditText>();
			fragments = new ArrayList<Fragment>();
			
			
			tv_myCard = (TextView)findViewById(R.id.tv_myCard);
			tv_contacts = (TextView)findViewById(R.id.tv_Contacts);
			tv_myCard.setTextColor(Color.GREEN);
			
			myCardFragment = new MyCardFragment();
			contactsFragment = new ContactsFragment();
			fragments.add(myCardFragment);fragments.add(contactsFragment);
			MyPager = (ViewPager)findViewById(R.id.MyViewPager2);
			adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
				
				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return fragments.size();
				}
				
				@Override
				public Fragment getItem(int arg0) {
					// TODO Auto-generated method stub
					return fragments.get(arg0);
				}
			};
			MyPager.setAdapter(adapter);
			MyPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					resetColor();
					switch(arg0){
					case 0:
						tv_myCard.setTextColor(Color.GREEN);break;
					case 1:
						tv_contacts.setTextColor(Color.GREEN);break;
					}
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	}
	
	public void resetColor(){
		tv_myCard.setTextColor(Color.BLACK);
		tv_contacts.setTextColor(Color.BLACK);
	}
	class MyCardFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.fragment_mycard, container, false);
			btn_Edit = (Button)view.findViewById(R.id.btn_edit);
			btn_Transport = (Button)view.findViewById(R.id.btn_transport);
			tv_name = (TextView)view.findViewById(R.id.tv2_name);
			TextView tv_institute = (TextView)view.findViewById(R.id.tv2_institute);
			TextView tv_sno = (TextView)view.findViewById(R.id.tv2_sno);
			tv_phone = (TextView)view.findViewById(R.id.tv2_phone);
			TextView tv_qq = (TextView)view.findViewById(R.id.tv2_qq);
			TextView tv_wechat = (TextView)view.findViewById(R.id.tv2_wechat);
			textViews.add(tv_name);textViews.add(tv_institute);
			textViews.add(tv_sno);textViews.add(tv_phone);
			textViews.add(tv_qq);textViews.add(tv_wechat);
			EditText et_name = (EditText)view.findViewById(R.id.et_name);
			EditText et_institute = (EditText)view.findViewById(R.id.et_institute);
			EditText et_sno = (EditText)view.findViewById(R.id.et_sno);
			EditText et_phone = (EditText)view.findViewById(R.id.et_phone);
			EditText et_qq = (EditText)view.findViewById(R.id.et_qq);
			EditText et_wechat = (EditText)view.findViewById(R.id.et_wechat);
			editTexts.add(et_name);editTexts.add(et_institute);
			editTexts.add(et_sno);editTexts.add(et_phone);
			editTexts.add(et_qq);editTexts.add(et_wechat);
			btn_Edit = (Button)view.findViewById(R.id.btn_edit);
			btn_Confirm = (Button)view.findViewById(R.id.btn_confirm);
			
			btn_Transport.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(CardTransport.this, TransportContact.class);
					intent.putExtra("name", tv_name.getText().toString());
					intent.putExtra("phone", tv_phone.getText().toString());
					startActivity(intent);
				}
				
			});
			btn_Edit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_Edit.setVisibility(View.GONE);
					btn_Confirm.setVisibility(View.VISIBLE);
					for(int i = 0 ; i < textViews.size(); i++){
						textViews.get(i).setVisibility(View.GONE);
						editTexts.get(i).setVisibility(View.VISIBLE);
					}
				}
				
			});
			btn_Confirm.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btn_Edit.setVisibility(View.VISIBLE);
					btn_Confirm.setVisibility(View.GONE);
					String temp = null;
					for(int i = 0; i < textViews.size(); i++){
						if(!((temp = editTexts.get(i).getText().toString()).isEmpty())){
							textViews.get(i).setText(temp);						
							}
						editTexts.get(i).setVisibility(View.GONE);
						textViews.get(i).setVisibility(View.VISIBLE);
					}
				
				}
				
			});
			return view;
		}
	}
	
	class ContactsFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.fragment_contacts, container, false);
			ListView lsvContacts = (ListView)view.findViewById(R.id.lsv_my_contact);
			getContact();
			getData();
			adapter0 = new SimpleAdapter(getActivity(), listData, R.layout.lsv_contact_raw,
					new String[] {"name", "phone"},
					new int[] {R.id.contact_name, R.id.contact_phone});
			lsvContacts.setAdapter(adapter0);
			
			lsvContacts.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view,  
					     int position, long id) {
					ListView listView = (ListView)parent; 
					HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
					String name = map.get("name");
					for (Iterator<Contact> it=listContact.iterator(); it.hasNext(); )
				    {	    	
				    	Contact contact = it.next();
				    	if(name.equals(contact.getName()))
				    	{
				    		Intent intent = new Intent();
							intent.setClass(CardTransport.this, TransportContact.class);
							intent.putExtra("name", name);
							intent.putExtra("phone", contact.getPhone());
							startActivity(intent);
							break;
				    	}
				    }
				}
				});
			
			return view;
		}
	}
	
	private void getData() {
		listData.clear();
		for (Iterator<Contact> it = listContact.iterator(); it.hasNext(); ){
			Map<String, String> tmp = new HashMap<String, String>();
			Contact contact = it.next();		
			tmp.put("name", contact.getName());
			tmp.put("phone", contact.getPhone());
			listData.add(tmp);
		}
	}
	
	public void getContact(){

		//获得所有的联系人   
		Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);   
		
		//循环遍历   
		if (cur.moveToFirst()) {
		int idColumn  = cur.getColumnIndex(ContactsContract.Contacts._ID);   
		int displayNameColumn = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		do {   

		//获得联系人的ID号   
		String contactId = cur.getString(idColumn);

		//获得联系人姓名   
		String disPlayName = cur.getString(displayNameColumn);   

		//查看该联系人有多少个电话号码。如果没有这返回值为0   
		int phoneCount = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));   

		if(phoneCount>0){
		   //获得联系人的电话号码   
		   Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID   
				   + " = " + contactId, null, null);   
		   if(phones.moveToFirst()){
		   do{
			   //遍历所有的电话号码
			   String phoneNumber= phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));  		   
			   Contact contact = new Contact();
			   contact.setName(disPlayName);
			   contact.setPhone(phoneNumber);
			   listContact.add(contact);
		   		}while(phones.moveToNext());
		   }
		}
		   } while (cur.moveToNext());
		}   
	}
}
