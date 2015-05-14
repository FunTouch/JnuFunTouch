package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class firstDisplayActivity extends Activity {
	GestureDetector detector = null;
	ImageView iv_firstDisplay = null;
	boolean isFling = false;
	boolean run = true;
	int currentFrame = 0;
	int[] firstDisplayDrawables = new int[]{R.drawable.bg_welcome,
	R.drawable.bg_welcome1,R.drawable.bg_welcome2, 
	R.drawable.bg_welcome3
	};
	GoNextFrameThread goNextFrameThread = null;
	GoNextFrameHandler goNextFrameHandler = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstdisplay);
		iv_firstDisplay = (ImageView) findViewById(R.id.iv_firstDisplay);
		goNextFrameThread = new GoNextFrameThread();
		goNextFrameHandler = new GoNextFrameHandler();
		goNextFrameThread.start();
		detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				if((e2.getX() - e1.getX()) < 0){
					isFling = true;
					run = false;
					if(currentFrame < firstDisplayDrawables.length-1){
						currentFrame++;
						((BitmapDrawable)(iv_firstDisplay.getDrawable())).getBitmap().recycle();
						iv_firstDisplay.setImageResource(firstDisplayDrawables[currentFrame]);
					}else{
						Intent intent = new Intent(firstDisplayActivity.this,Login.class);
						startActivity(intent);
						run = false;
						goNextFrameThread.interrupt();
						firstDisplayActivity.this.finish();
					}
				}
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return detector.onTouchEvent(event);
	}
	
	class GoNextFrameHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 0x123){
			if(currentFrame < firstDisplayDrawables.length-1){
				currentFrame++;
				((BitmapDrawable)(iv_firstDisplay.getDrawable())).getBitmap().recycle();
				iv_firstDisplay.setImageResource(firstDisplayDrawables[currentFrame]);
				isFling = false;
			}else{
				Intent intent = new Intent(firstDisplayActivity.this,Login.class);
				startActivity(intent);
				run = false;
				goNextFrameThread.interrupt();
				firstDisplayActivity.this.finish();
			}
		}
		}
	}
	
	class GoNextFrameThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			if(run){
			if(!isFling){
				Message goNextMsg = new Message();
				goNextMsg.what = 0x123;
				goNextFrameHandler.sendMessageDelayed(goNextMsg, 1500);
				
			}
		  }
		}
	}
}
