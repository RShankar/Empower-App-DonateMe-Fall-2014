package edu.fau.group4.donateme;


import java.util.ArrayList;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings.Global;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.fau.group4.donateme.MusicService.LocalBinder;


public class MainActivity extends Activity implements OnClickListener{
	 
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
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		setGlobalLayout();
		startService(new Intent(this, MusicService.class));
	    Intent connectionIntent = new Intent(this, MusicService.class);
	    bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
	       
	    View button1 = findViewById(R.id.loginButton);
	    button1.setOnClickListener(this); 
	    
	    View v = this.getWindow().getDecorView();
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
	    ((Button) findViewById(R.id.loginButton)).setTextSize(GlobalLayout.buttonFontSize);
	    ((TextView) findViewById(R.id.welcometxtview)).setTextSize(GlobalLayout.headerFontSize);
    } 
    
    
    
	@Override
	public void onClick(View v) {	
		
		switch (v.getId()) {
	      case R.id.loginButton:
	    	 	         
	    	  Intent intent;
	  		if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
	  			// If user is anonymous, send the user to LoginSignupActivity.class
	  			intent = new Intent(MainActivity.this,
	  					Welcomepage.class);
	  			startActivity(intent);
	  			//finish();
	  		} else {
	  			// If current user is NOT anonymous user
	  			// Get current user data from Parse.com
	  			ParseUser currentUser = ParseUser.getCurrentUser();
	  			if (currentUser != null) {
	  				// Send logged in users to Welcome.class
	  				intent = new Intent(MainActivity.this, Tab.class);
	  				startActivity(intent);
	  				//finish();
	  			} else {
	  				// Send user to LoginSignupActivity.class
	  				intent = new Intent(MainActivity.this,
	  						Welcomepage.class);
	  				startActivity(intent);
	  				//finish();
	  			}
	  		}
	         break;	          
		}
	}
	
	private void setGlobalLayout()
	{
		Intent i = getIntent();
	    
	    String s = i.getStringExtra("backColor");
	    if(s == null) 	GlobalLayout.backgroundColor = getResources().getColor(R.color.back_green) | 0xff000000; //get default value
	    else 			GlobalLayout.backgroundColor = Color.parseColor(s) | 0xff000000;
	    
	    float[] fa = i.getFloatArrayExtra("filterDistance");
	    GlobalLayout.filterDistance = new ArrayList<Float>();
	    if(fa != null)
		    for(int index = 0; index < fa.length; index++)
		    {
		    	GlobalLayout.filterDistance.add(fa[index]);
		    }
	    else
	    {
	    	GlobalLayout.filterDistance.add(1.0f);
	    	GlobalLayout.filterDistance.add(3.0f);
	    	GlobalLayout.filterDistance.add(4.0f);
	    }
	    GlobalLayout.labelFontSize = i.getFloatExtra("labelFontSize", 20.0f);
	    GlobalLayout.headerFontSize = i.getFloatExtra("headFontSize", 70.0f);
	    GlobalLayout.buttonFontSize = i.getFloatExtra("buttonFontSize", 30.0f);
	}
	  
   
}


