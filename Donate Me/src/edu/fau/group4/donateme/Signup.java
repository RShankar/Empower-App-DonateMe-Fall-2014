package edu.fau.group4.donateme;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

public class Signup extends Activity{

	EditText username;
	EditText password;
	EditText firstname;
	EditText lastname;
	EditText paypal;
	EditText email;
	String emailtxt;
	String paypaltxt;
	String firstnametxt;
	String lastnametxt;
	String usernametxt;
	String passwordtxt;
	CheckBox repeatChkBx;
	EditText orgnameedit;
	Spinner orgtypespinner;
	private String[] arraySpinner;
	
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
	    View v = this.getWindow().getDecorView();
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
		setContentView(R.layout.signup);
		
	    ((Button) findViewById(R.id.loginbutton)).setTextSize(GlobalLayout.buttonFontSize);
	    ((Button) findViewById(R.id.signupsubmit)).setTextSize(GlobalLayout.buttonFontSize);
	    
		Intent connectionIntent = new Intent(this, MusicService.class);
	    bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		email = (EditText) findViewById(R.id.emailedit);
		firstname = (EditText) findViewById(R.id.firstedit);
		lastname = (EditText) findViewById(R.id.lastedit);
		paypal = (EditText) findViewById(R.id.paypaledit);
		
		// Set up click listeners for all the buttons	    
		Button submit = (Button) findViewById(R.id.signupsubmit);
    
		repeatChkBx = ( CheckBox ) findViewById( R.id.isorgcheckbox);
		repeatChkBx.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		        if ( isChecked )
		        {
		        	TextView orgname = (TextView) findViewById(R.id.orgnametxtview);
		            orgname.setVisibility(View.VISIBLE);
		            TextView orgtype = (TextView) findViewById(R.id.orgtypetxtview);
		            orgtype.setVisibility(View.VISIBLE);
		            orgnameedit = (EditText) findViewById(R.id.orgname);
		            orgnameedit.setVisibility(View.VISIBLE);
		            orgtypespinner = (Spinner) findViewById(R.id.orgtypespinner);
		            orgtypespinner.setVisibility(View.VISIBLE);
		            TextView paypallabel =(TextView) findViewById(R.id.paypaltxtview);
		            paypallabel.setVisibility(View.VISIBLE);
		            paypal = (EditText) findViewById(R.id.paypaledit);
		            paypal.setVisibility(View.VISIBLE);

		        }
		        else
		        {
		        	TextView orgname = (TextView) findViewById(R.id.orgnametxtview);
		            orgname.setVisibility(View.INVISIBLE);
		            TextView orgtype = (TextView) findViewById(R.id.orgtypetxtview);
		            orgtype.setVisibility(View.INVISIBLE);
		            orgnameedit = (EditText) findViewById(R.id.orgname);
		            orgnameedit.setVisibility(View.INVISIBLE);
		            orgtypespinner = (Spinner) findViewById(R.id.orgtypespinner);
		            orgtypespinner.setVisibility(View.INVISIBLE);
		            TextView paypallabel =(TextView) findViewById(R.id.paypaltxtview);
		            paypallabel.setVisibility(View.INVISIBLE);
		            paypal = (EditText) findViewById(R.id.paypaledit);
		            paypal.setVisibility(View.INVISIBLE);

		        }

		    }
		});
		
		 View back = findViewById(R.id.loginbutton);
		    back.setOnClickListener(new OnClickListener()
		    {
		    	public void onClick(View arg0)
		    	{
		    		 Intent i1 = new Intent(Signup.this, Welcomepage.class);	         
					 startActivity(i1);
		    		
		    	}
		    });
		    
		    this.arraySpinner = new String[] {
		            "Research", "For Profit", "Not For Profit"
		        };
		    orgtypespinner = (Spinner) findViewById(R.id.orgtypespinner);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_dropdown_item, arraySpinner);
		        orgtypespinner.setAdapter(adapter);
		    
		    
    submit.setOnClickListener(new OnClickListener() {
    	 
		public void onClick(View arg0) {
			// Retrieve the text entered from the EditText
			usernametxt = username.getText().toString();
			passwordtxt = password.getText().toString();
			emailtxt = email.getText().toString();
			firstnametxt = firstname.getText().toString();
			lastnametxt = lastname.getText().toString();
			paypaltxt = paypal.getText().toString();
			// Force user to fill up the form
			if (usernametxt.equals("") || passwordtxt.equals("") || emailtxt.equals("") || firstnametxt.equals("") || lastnametxt.equals("")) {
				Toast.makeText(getApplicationContext(),
						"Please complete the sign up form",
						Toast.LENGTH_LONG).show();

			} else {
				// Save new user data into Parse.com Data Storage
				ParseUser user = new ParseUser();
				user.setUsername(usernametxt);
				user.setPassword(passwordtxt);
				user.setEmail(emailtxt); 
				user.put("firstName", firstnametxt);
				user.put("lastName", lastnametxt);
				user.put("address", "");
				user.put("city", "");
				user.put("state", "");
				if(repeatChkBx.isChecked())
				{
					user.put("isOrg", "true");
					user.put("orgName", orgnameedit.getText().toString());
					user.put("orgType", orgtypespinner.getSelectedItem().toString());
					user.put("paypalEmail", paypaltxt);
				}
				else
				{
					user.put("isOrg", "false");
				}
				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {
						if (e == null) {
							// Show a simple Toast message upon successful registration
							Toast.makeText(getApplicationContext(),
									"Successfully Signed up.",
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent(
									Signup.this,
									Tab.class);
							startActivity(intent);
						} else {
							Toast.makeText(getApplicationContext(),
									"Sign up Error", Toast.LENGTH_LONG)
									.show();
						}
					}
				});
			}

		}
	});
}
}