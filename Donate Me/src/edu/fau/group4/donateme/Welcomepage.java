package edu.fau.group4.donateme;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Welcomepage extends Activity implements OnClickListener{

	private MusicService mp3Service;
	private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
	        
	        public void onServiceConnected(ComponentName arg0, IBinder service) {
	            LocalBinder binder = (LocalBinder) service;
	        	mp3Service = binder.getService();
	        	mp3Service.playSong(getBaseContext());
	        }
	 
	        @Override
	        public void onServiceDisconnected(ComponentName arg0) {
	 
	        }
	 
	    };
	    @Override
	    protected void onDestroy() {
	        unbindService(this.mp3PlayerServiceConnection);
	        super.onDestroy();
	    }
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    View v = this.getWindow().getDecorView();
	    Intent connectionIntent = new Intent(this, MusicService.class);
        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
		setContentView(R.layout.welcome);
		
		((Button) findViewById(R.id.loginbutton)).setTextSize(GlobalLayout.buttonFontSize);
		((Button) findViewById(R.id.signupsubmit)).setTextSize(GlobalLayout.buttonFontSize);
	    ((TextView) findViewById(R.id.welcometxtview)).setTextSize(GlobalLayout.labelFontSize);
		
		Intent i = getIntent();
		
		String s = i.getStringExtra("backColor");
	    if(s == null) 	GlobalLayout.backgroundColor = getResources().getColor(R.color.back_green) | 0xff000000; //get default value
	    else 			GlobalLayout.backgroundColor = Color.parseColor(s) | 0xff000000;
	    
	    float[] fa = i.getFloatArrayExtra("filterDistance");
	    if(fa != null)
		    for(int index = 0; index < fa.length; index++)
		    {
		    	GlobalLayout.filterDistance.add(fa[index]);
		    }
	    GlobalLayout.buttonFontSize = i.getFloatExtra("buttonFontSize", 20);

		
	        
		// Set up click listeners for all the buttons		
	    View button1 = findViewById(R.id.loginbutton);
	    button1.setOnClickListener(this);
	    View button2 = findViewById(R.id.signupsubmit);
	    button2.setOnClickListener(this);
    }

   //comment
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	      case R.id.signupsubmit:
	    	 Intent i = new Intent(Welcomepage.this, Login.class);	         
			 startActivity(i);
	         break;
	         // ...
	      case R.id.loginbutton:
	         Intent i1 = new Intent(this, Signup.class);
	         startActivity(i1);
	         break;	      
		}
	}
}
