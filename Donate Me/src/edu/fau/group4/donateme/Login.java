package edu.fau.group4.donateme;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity{
	EditText username;
	EditText password;
	String usernametxt;
	String passwordtxt;
	private MusicService mp3Service;
	private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
	        
	        public void onServiceConnected(ComponentName arg0, IBinder service) {
	            LocalBinder binder = (LocalBinder) service;
	        	mp3Service = binder.getService();
	        	mp3Service.playSong(getBaseContext());
	        	if(!GlobalLayout.soundEnabled) mp3Service.mute(getBaseContext());
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
		  Intent connectionIntent = new Intent(this, MusicService.class);
	        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
	    View v = this.getWindow().getDecorView();
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
		setContentView(R.layout.login);	

	    ((Button) findViewById(R.id.loginButton)).setTextSize(GlobalLayout.buttonFontSize);
	    ((Button) findViewById(R.id.backButton)).setTextSize(GlobalLayout.buttonFontSize);
	    ((TextView) findViewById(R.id.welcometxtview)).setTextSize(GlobalLayout.LabelFontSize);
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		// Set up click listeners for all the buttons	    
		Button loginbutton = (Button) findViewById(R.id.loginButton);
    
		loginbutton.setOnClickListener(new OnClickListener() 
		{
			 
			public void onClick(View arg0) 
			{
				// Retrieve the text entered from the EditText
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();
 
				// Send data to Parse.com for verification
				ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() 
				{
							public void done(ParseUser user, ParseException e) 
							{
								if (user != null) 
								{
									// If user exist and authenticated, send user to Welcome.class
									Intent intent = new Intent(Login.this, Tab.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"Successfully Logged in",
											Toast.LENGTH_LONG).show();
									finish();
								} 
								else 
								{
									Toast.makeText(
											getApplicationContext(),
											"You have entered an invalid username or password.",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});
		
	    
	    View back = findViewById(R.id.backButton);
	    back.setOnClickListener(new OnClickListener()
	    {
	    	public void onClick(View arg0)
	    	{
	    		 Intent i1 = new Intent(Login.this, Welcomepage.class);	         
				 startActivity(i1);
	    		
	    	}
	    });
    }
   
}
