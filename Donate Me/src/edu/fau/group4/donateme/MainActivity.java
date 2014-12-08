package edu.fau.group4.donateme;


import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class MainActivity extends Activity implements OnClickListener{
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
	    View button1 = findViewById(R.id.loginbutton);
	    button1.setOnClickListener(this);   
	     
    }  

   
	@Override
	public void onClick(View v) {	
		
		switch (v.getId()) {
	      case R.id.loginbutton:
	    	 	         
	    	  Intent intent;
	  		if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
	  			// If user is anonymous, send the user to LoginSignupActivity.class
	  			intent = new Intent(MainActivity.this,
	  					Welcomepage.class);
	  			startActivity(intent);
	  			finish();
	  		} else {
	  			// If current user is NOT anonymous user
	  			// Get current user data from Parse.com
	  			ParseUser currentUser = ParseUser.getCurrentUser();
	  			if (currentUser != null) {
	  				// Send logged in users to Welcome.class
	  				intent = new Intent(MainActivity.this, Tab.class);
	  				startActivity(intent);
	  				finish();
	  			} else {
	  				// Send user to LoginSignupActivity.class
	  				intent = new Intent(MainActivity.this,
	  						Welcomepage.class);
	  				startActivity(intent);
	  				finish();
	  			}
	  		}
	         break;	          
		}
	}
	  
   
}


