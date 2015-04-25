package com.funtouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity{
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);  

        Handler x = new Handler();  
        x.postDelayed(new splashhandler(), 2000);  
          
    }  
    class splashhandler implements Runnable{  
  
        public void run() {  
            startActivity(new Intent(getApplication(),Login.class));  
            Splash.this.finish();  
        } 
    }
}
