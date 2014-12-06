package edu.fau.group4.donateme;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

public class Login extends Activity{
	EditText username;
	EditText password;
	String usernametxt;
	String passwordtxt;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);	
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		// Set up click listeners for all the buttons	    
		Button loginbutton = (Button) findViewById(R.id.loginbutton);
    
		loginbutton.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View arg0) {
				// Retrieve the text entered from the EditText
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();
 
				// Send data to Parse.com for verification
				ParseUser.logInInBackground(usernametxt, passwordtxt,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									// If user exist and authenticated, send user to Welcome.class
									Intent intent = new Intent(
											Login.this,
											Tab.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"Successfully Logged in",
											Toast.LENGTH_LONG).show();
									finish();
								} else {
									Toast.makeText(
											getApplicationContext(),
											"You have entered an invalid username or password.",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});
		
	    
	    View back = findViewById(R.id.signupsubmit);
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
