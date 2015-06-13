package com.funtouch;


import com.funtouch.util.CustomDialog;
import com.funtouch.util.LogUtil;
import com.funtouch.util.MyConstant;
import com.funtouch.util.nfc.BobNdefMessage;
import com.google.common.base.Preconditions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class NfcTagWriter extends MenuHavingActivity {
    private static final String TAG_ASSIST = "[MainActivity]-";

    // NFC Declarations
    private NfcAdapter mNfcAdapter = null;
    private IntentFilter[] mFilters = null;
    private PendingIntent mPendingIntent = null;
    private String[][] mTechLists = null;
    private Context mContext;
    private NdefMessage NDEFMsg2Write = null;

    // UI
    private EditText mEditText1 = null;
    private EditText mEditText2 = null;
    private TextView mTextView1 = null;
    private TextView mTextView2 = null;
    private TextView content = null;
    private Spinner mSpinner = null;
    private ArrayAdapter mAdapter = null;
    private Button writeUrlButton, saveUrlButton, exitButton;
    private static final String[] spinnerStr = {
             "文本信息(Text)", "网站地址(URL)", 
    };
    private String payloadStr = "";
    private String typeStr = "";
    private int tagTypeFlag = 0;//
    private AlertDialog alertDialog = null;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_tag_writer);
        mContext = this;

        checkNFCFunction(); // NFC Check
        initUI();// Init UI
        initNFC();// Init NFC
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        // item clicked
        {
            case R.id.nfc_setting:
                // Intent setnfc = new
                // Intent(Settings.ACTION_WIRELESS_SETTINGS);
                Intent setnfc = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(setnfc);
                break;
            case R.id.wifi_setting:
                Intent setwifi = new Intent(Settings.ACTION_SETTINGS);
                startActivity(setwifi);
                break;

        }
        return true;
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_notice));
        alertDialog = builder.create();
        builder.setCancelable(true);// back
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            LogUtil.i(MyConstant.TAG, TAG_ASSIST + "ACTION_TECH_DISCOVERED");
            // get NFC object
            Tag detectTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            // validate that this tag can be written
            if (supportedTechs(detectTag.getTechList())) {
                switch (tagTypeFlag) {
                  
                    case 0:// RTD_TEXT
                        NDEFMsg2Write = BobNdefMessage.getNdefMsg_from_RTD_TEXT(payloadStr, false,
                                false);
                        // writeNdefMessageToTag(NDEFMsg2Write, detectTag);
                        new WriteTask(this, NDEFMsg2Write, detectTag).execute();
                        break;
                    case 1:// RTD_URI
                        NDEFMsg2Write = BobNdefMessage.getNdefMsg_from_RTD_URI(payloadStr, (byte)0x01,
                                false);
                        // writeNdefMessageToTag(NDEFMsg2Write, detectTag);
                        new WriteTask(this, NDEFMsg2Write, detectTag).execute();
                        break;

                    default:// RTD_URI
                        NDEFMsg2Write = BobNdefMessage.getNdefMsg_from_RTD_URI(payloadStr, (byte)0x01,
                                false);
                        // writeNdefMessageToTag(NDEFMsg2Write, detectTag);
                        new WriteTask(this, NDEFMsg2Write, detectTag).execute();
                        break;
                }
            } else {
                Toast.makeText(mContext, "This tag type is not supported", Toast.LENGTH_SHORT).show();
            }
        } 
    }

    @Override
    public void onNewIntent(Intent intent) {
        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "Discovered tag with intent: " + intent);
        setIntent(intent);
        //if(flag == 1)
        	processIntent(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (alertDialog != null)
            alertDialog.cancel();
        disableForegroundDispatch();
        
    }

    private void initUI() {
        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "into initUI");
        // TODO Auto-generated method stub
        mEditText1 = (EditText)this.findViewById(R.id.write_ndef_tag_et1);
        content = (TextView)findViewById(R.id.tv_content);
        mEditText2 = (EditText)this.findViewById(R.id.write_ndef_tag_et2);
        mTextView1 = (TextView)findViewById(R.id.write_ndef_tag_tv1);
        mTextView2 = (TextView)findViewById(R.id.write_ndef_tag_tv2);

        saveUrlButton = (Button)findViewById(R.id.write_ndef_tag_saveBtn);
        saveUrlButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeUrlButton.setEnabled(true);
                switch (tagTypeFlag) {
                    
                    case 0:// RTD_TEXT
                        payloadStr = mEditText1.getText().toString();
                        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "payloadStr=" + payloadStr);
                        mTextView1.setText("(接触标签以写入文本信息)\n " + payloadStr);
                        mTextView1.setTextColor(Color.BLUE);
                        break;
                    case 1:// RTD_URI
                        payloadStr = mEditText1.getText().toString();
                        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "payloadStr=" + payloadStr);
                        mTextView1.setText("(接触标签以写入URL)\n http://www."
                                + payloadStr);
                        mTextView1.setTextColor(Color.BLUE);
                        break;
                   
                    default:// RTD_URI
                        payloadStr = mEditText1.getText().toString();
                        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "payloadStr=" + payloadStr);
                        mTextView1.setText("(接触标签以写入URL)\n http://www." + payloadStr);
                        mTextView1.setTextColor(Color.BLUE);
                        break;
                }
            }
        });

        writeUrlButton = (Button)this.findViewById(R.id.write_ndef_tag_writeBtn);
        writeUrlButton.setEnabled(false);
        writeUrlButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Write to a tag for as long as the dialog is shown.
                enableForegroundDispatch();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("接触并写入标签").setOnCancelListener(
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                LogUtil.i(MyConstant.TAG, TAG_ASSIST + "mTagWriter - onCancel");
                                disableForegroundDispatch();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });

        exitButton = (Button)findViewById(R.id.write_ndef_tag_exitBtn);
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        mSpinner = (Spinner)findViewById(R.id.write_ndef_tag_sp);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerStr);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
        mSpinner.setVisibility(View.VISIBLE);
        mSpinner.setSelection(0, true);
    }

    class SpinnerXMLSelectedListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            LogUtil.i(MyConstant.TAG,
                    TAG_ASSIST + "What you have selected=" + mAdapter.getItem(arg2) + ",position="
                            + arg2);
            saveUrlButton.setEnabled(true);
            writeUrlButton.setEnabled(false);
            switch (arg2) {
                
                case 0:// RTD TEXT
                    tagTypeFlag = 0;
                    mTextView1.setText("请输入一段文本信息 ,例如: 这是一段信息");
                    mEditText1.setText("这是一段信息");
                    mTextView1.setTextColor(Color.RED);
                    mEditText2.setVisibility(View.GONE);
                    mTextView2.setVisibility(View.GONE);
                    break;
                case 1: // RTD URI
                    tagTypeFlag = 1;
                    mTextView1.setText("请输入一个域名地址 ,例如: baidu.com");
                    mEditText1.setText("baidu.com");
                    mTextView1.setTextColor(Color.RED);
                    mEditText2.setVisibility(View.GONE);
                    mTextView2.setVisibility(View.GONE);
                    break;
                default:// RTD_URI
                    tagTypeFlag = 1;
                    mTextView1.setText("请输入一个域名地址 ,例如: baidu.com");
                    mEditText1.setText("baidu.com");
                    mTextView1.setTextColor(Color.RED);
                    mEditText2.setVisibility(View.GONE);
                    mTextView2.setVisibility(View.GONE);
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    private void initNFC() {
        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "into initNFC");
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // FLAG_ACTIVITY_SINGLE_TOP: not creating multiple instances of the same
        // application.
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        // ndef.addDataScheme("http");
        // Intent filters for writing to a tag
        mFilters = new IntentFilter[] {
            ndefDetected,
        };// just trying to find a tag,not ndef or tech

        mTechLists = new String[][] {
                new String[] {
                    Ndef.class.getName()
                }, new String[] {
                    NdefFormatable.class.getName()
                }
        };
    }

    boolean writeNdefMessageToTag(NdefMessage message, Tag detectedTag) {
        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "into writeNdefMessageToTag");
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(detectedTag);
            if (ndef != null) {
                ndef.connect();

                if (!ndef.isWritable()) {
                    Toast.makeText(this, "Tag is read-only.", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    Toast.makeText(
                            this,
                            "The data cannot written to tag, tag capacity is " + ndef.getMaxSize()
                                    + " bytes, message is " + size + " bytes.", Toast.LENGTH_LONG)
                            .show();
                    return false;
                }

                ndef.writeNdefMessage(message);
                ndef.close();
                String str = "标签已成功写入, message=" + message;
                Toast.makeText(this, str, Toast.LENGTH_LONG).show();
                return true;
            } else {
                NdefFormatable ndefFormat = NdefFormatable.get(detectedTag);
                if (ndefFormat != null) {
                    try {
                        ndefFormat.connect();
                        ndefFormat.format(message);
                        ndefFormat.close();
                        Toast.makeText(this, "标签已成功写入!", Toast.LENGTH_SHORT)
                                .show();
                        return true;
                    } catch (IOException e) {
                        Toast.makeText(this, "Failed to format tag", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "NDEF is not supported", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } catch (Exception e) {
            //Toast.makeText(this, "Write opreation is failed", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public static boolean supportedTechs(String[] techs) {
        LogUtil.i(MyConstant.TAG, TAG_ASSIST + "into supportedTechs");
        for (String s : techs) {
            LogUtil.i(MyConstant.TAG, TAG_ASSIST + "all supportedTechs = " + s);
        }
        boolean ultralight = false;
        boolean nfcA = false;
        boolean ndef = false;
        for (String tech : techs) {
            if (tech.equals("android.nfc.tech.MifareUltralight")) {
                ultralight = true;
                LogUtil.i(MyConstant.TAG, TAG_ASSIST + "supportedTechs is:ultralight");
            } else if (tech.equals("android.nfc.tech.NfcA")) {
                nfcA = true;
                LogUtil.i(MyConstant.TAG, TAG_ASSIST + "supportedTechs is:NfcA");
            } else if (tech.equals("android.nfc.tech.Ndef")
                    || tech.equals("android.nfc.tech.NdefFormatable")) {
                ndef = true;
                LogUtil.i(MyConstant.TAG, TAG_ASSIST + "supportedTechs is:Ndef/NdefFormatable");
            } else if (tech.equals("android.nfc.tech.MifareClassic")) {
                LogUtil.i(MyConstant.TAG, TAG_ASSIST + "supportedTechs is:MifareClassic");
            }
        }
        if (ultralight && nfcA && ndef) {
            return true;
        } else {
            return false;
        }
    }

    private void enableForegroundDispatch() {
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
        }
    }

    private void disableForegroundDispatch() {
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    private void checkNFCFunction() {
        // TODO Auto-generated method stub
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // check the NFC adapter first
        if (mNfcAdapter == null) {
            // mTextView.setText("NFC apdater is not available");
            Dialog dialog = null;
            CustomDialog.Builder customBuilder = new CustomDialog.Builder(mContext);
            customBuilder
                    .setTitle(getString(R.string.inquire))
                    .setMessage(getString(R.string.nfc_notice2))
                    .setIcon(R.drawable.dialog_icon2)
                    .setNegativeButton(getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                    .setPositiveButton(getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
            dialog = customBuilder.create();
            dialog.setCancelable(false);// back
            dialog.setCanceledOnTouchOutside(false);
            SetDialogWidth(dialog).show();
            return;
        } else {
            if (!mNfcAdapter.isEnabled()) {
                Dialog dialog = null;
                CustomDialog.Builder customBuilder = new CustomDialog.Builder(mContext);
                customBuilder
                        .setTitle(getString(R.string.inquire))
                        .setMessage(getString(R.string.nfc_notice3))
                        .setIcon(R.drawable.dialog_icon2)
                        .setNegativeButton(getString(R.string.no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                })
                        .setPositiveButton(getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent setnfc = new Intent(
                                                Settings.ACTION_WIRELESS_SETTINGS);
                                        // Intent setnfc = new
                                        // Intent(Settings.ACTION_NFC_SETTINGS);
                                        startActivity(setnfc);
                                    }
                                });
                dialog = customBuilder.create();
                dialog.setCancelable(false);// back
                dialog.setCanceledOnTouchOutside(false);
                SetDialogWidth(dialog).show();
                return;
            }
        }
    }

    private Dialog SetDialogWidth(Dialog dialog) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        if (screenWidth > screenHeight) {
            params.width = (int)(((float)screenHeight) * 0.875);

        } else {
            params.width = (int)(((float)screenWidth) * 0.875);
        }
        dialog.getWindow().setAttributes(params);

        return dialog;
    }

    static class WriteTask extends AsyncTask<Void, Void, Void> {
        Activity host = null;

        NdefMessage msg = null;

        Tag tag = null;

        String text = null;

        WriteTask(Activity host, NdefMessage msg, Tag tag) {
            LogUtil.i(MyConstant.TAG, TAG_ASSIST + "into WriteTask AsyncTask");
            this.host = host;
            this.msg = msg;
            this.tag = tag;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            int size = msg.toByteArray().length;

            try {
                Ndef ndef = Ndef.get(tag);

                if (ndef == null) {
                    NdefFormatable formatable = NdefFormatable.get(tag);
                    if (formatable != null) {
                        try {
                            formatable.connect();
                            try {
                                formatable.format(msg);
                            } catch (Exception e) {
                                text = "Failed to format tag! Tag refused to format";
                            }
                        } catch (Exception e) {
                            text = "Failed to connect tag! Tag refused to connect";
                        } finally {
                            formatable.close();
                        }
                    } else {
                        text = "NDEF is not supported in this Tag";
                    }
                } else {
                    ndef.connect();

                    try {
                        if (!ndef.isWritable()) {
                            text = "Tag is read-only";
                        } else if (ndef.getMaxSize() < size) {
                            text = "The data cannot written to tag! Message is too big for tag! Tag capacity is "
                                    + ndef.getMaxSize() + " bytes, message is " + size + " bytes.";
                        } else {
                            ndef.writeNdefMessage(msg);
                            text = "标签已成功写入!";
                        }
                    } catch (Exception e) {
                        text = "Tag refused to connect";
                    } finally {
                        ndef.close();
                    }
                }
            } catch (Exception e) {
                //text = "Write opreation is failed! General exception: " + e.getMessage();
                LogUtil.i(MyConstant.TAG, TAG_ASSIST
                        + "Exception when writing tag! Write opreation is failed" + text);
            }

            return (null);
        }

        @Override
        protected void onPostExecute(Void unused) {
            if (text != null) {
                Toast.makeText(host, text, Toast.LENGTH_SHORT).show();
            }

        }
    }
    
    
    
    public static boolean isText(NdefRecord record)
    {
    	if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN)
    	{
    		if(Arrays.equals(record.getType(), NdefRecord.RTD_TEXT))
    			return true;
    		else
    			return false;
    	}
    	else if (record.getTnf() == NdefRecord.TNF_ABSOLUTE_URI)
    		return true;
    	else
    		return false;
    }
    
    
    void processIntent(Intent intent) {
    	String action = intent.getAction();
    	if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
    	{
    		NdefMessage[] messages = null;
    		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
    		if(rawMsgs != null)
    		{
    			messages = new NdefMessage[rawMsgs.length];
    			for (int i = 0; i < rawMsgs.length; i++)
    			{
    				messages[i] = (NdefMessage) rawMsgs[i];
    			}
    		}else
    		{
    			byte[] empty = new byte[]{};
    			NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,empty,empty,empty);
    			NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
    			messages = new NdefMessage[] { msg };
    		}
    		processNDEFMsg(messages);
    	}
    }
    
    //处理NDEF消息
    void processNDEFMsg (NdefMessage[] messages)
    {
    	if (messages == null || messages.length == 0)
    	{
    		return;
    	}
    	for (int i = 0; i < messages.length; i++)
    	{
    		int length = messages[i].getRecords().length;
    		NdefRecord[] records = messages[i].getRecords();
    		for (int j = 0; j < length; j++)
    		{
    			for(NdefRecord record : records)
    			{
    				if(isText(record))
    				{
    					parseTextRecord(record);
    				}
    			}
    		}
    	}
    }
    
    private void parseTextRecord(NdefRecord record)
    {
    	short tnf = record.getTnf();
    	if (tnf == NdefRecord.TNF_WELL_KNOWN)
    		parseWellKnownTextRecord(record);
    }
    
    private void parseWellKnownTextRecord(NdefRecord record)
    {
    	Preconditions.checkArgument(record.getTnf() == NdefRecord.TNF_WELL_KNOWN);
    	Preconditions.checkArgument(Arrays.equals(record.getType(), NdefRecord.RTD_TEXT));
    	String payloadStr = "";
        byte[] payload = record.getPayload();
        Byte statusByte = record.getPayload()[0];

        String textEncoding = ((statusByte & 0200) == 0) ? "UTF-8" : "UTF-16";// 0x80=0200
        int languageCodeLength = statusByte & 0077; // & 0x3F=0077(bit 5 to 0)
        String languageCode = new String(payload, 1, languageCodeLength, Charset.forName("UTF-8"));
        try {
            payloadStr = new String(payload, languageCodeLength + 1, payload.length
                    - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	//byte[] fullUri = Bytes.concat(Arrays.copyOfRange(payload,0, payload.length));
    	Uri uri = Uri.parse(payloadStr);
    	//Log.i("b",uri.toString());
    	uiControl(uri);
    }
    
    private void uiControl(final Uri uri)
    {
    	content.setText(uri.toString());
  
    }
    
    
}
