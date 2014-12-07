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
import android.widget.Spinner;

public class Request extends Activity implements OnItemSelectedListener,OnClickListener
{
	 Spinner type;
	 Spinner request;
	 
	 private String[] state_type = { "Community Service", "Financial", "Goods", "Goodwill", "Services"};
	 private String[] state_request = { "Money","Clothes"};
	 
	 TextView text_type;
	 TextView text_request;
	 ParseUser currentUser;
	 String isOrg;
	  @Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.request);
		  System.out.println(state_type.length);
		//  text_type = (TextView) findViewById(R.id.textView1);
		  
		  type = (Spinner) findViewById(R.id.spinner1);
		  ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, state_type);
		  adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  type.setAdapter(adapter_type);
		  type.setOnItemSelectedListener(this);
		  
		 // System.out.println(state_distance.length);
		//  text_distance = (TextView) findViewById(R.id.textView2);
		  
		//  distance = (Spinner) findViewById(R.id.spinner2);
		//  ArrayAdapter<String> adapter_distance = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, state_distance);
		//  adapter_distance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//  distance.setAdapter(adapter_distance);
		//  distance.setOnItemSelectedListener(this);

		// Set up click listeners for all the buttons
		    View add_media = findViewById(R.id.loginbutton);
		    add_media.setOnClickListener(this);
		    View submit = findViewById(R.id.signupsubmit);
		    submit.setOnClickListener(this);
		    currentUser = ParseUser.getCurrentUser();
		    isOrg = currentUser.get("isOrg").toString();
	  }

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
			Spinner type = (Spinner)parent;
	        Spinner distance = (Spinner)parent;
	        if(type.getId() == R.id.spinner1)
	        {
	        	type.setSelection(position);
	  		  	String type_state = (String) type.getSelectedItem();
	  		  	text_type.setText("Spinner1:  " + type_state);    
	  		}
	        if(distance.getId() == R.id.spinner2)
	        {
	        	distance.setSelection(position);
	        	String distance_state = (String) distance.getSelectedItem();
	        //	text_distance.setText("Spinner2:  " + distance_state);        
	        }
	        
			// TODO Auto-generated method stub			
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
		      case R.id.signupsubmit:
		    	 Intent i = new Intent(Request.this, Howtohelp.class);	         
				 startActivity(i);
		         break;		
		         }	 	
		}
}