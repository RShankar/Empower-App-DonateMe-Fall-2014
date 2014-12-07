package edu.fau.group4.donateme;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Request extends Activity
{
	 Spinner type;
	 Spinner request;
     	 
	 private String[] state_type = { "Research", "For Profit", "Not For Profit"};
	 private String[] state_request = { "Money","Clothes","Food"};
	 EditText username;
	 TextView text_type;
	 TextView text_request;
	 ParseUser currentUser;
	 String isOrg;
	  @Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.request);
		 
		    View add_media = findViewById(R.id.loginbutton);
		    //add_media.setOnClickListener(this);
		    View submit = findViewById(R.id.signupsubmit);
		    //submit.setOnClickListener(this);
		    currentUser = ParseUser.getCurrentUser();
		    username.setText(currentUser.get("orgName").toString());
	  }

		
		
		
}