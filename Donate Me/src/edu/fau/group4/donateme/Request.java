package edu.fau.group4.donateme;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
import android.widget.Toast;

public class Request extends Activity
{
	 Spinner type;
	 Spinner request;
     	 
	 private String[] state_type = { "Research", "For Profit", "Not For Profit"};
	 private String[] state_request = { "Money","Clothes","Food"};
	 EditText username;
	 TextView text_type;
	 TextView text_request;
	 TextView goallabel;
	 EditText goal;
	 EditText whatfor;
	 EditText description;
	 EditText website;
	 ParseUser currentUser;
	 String isOrg;
	 Spinner orgtypespinner;
	 Spinner requesttypespinner;
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.request);
		  username = (EditText) findViewById(R.id.username);
		  whatfor = (EditText) findViewById(R.id.editText3);
		  description = (EditText) findViewById(R.id.editText4);
		  website = (EditText) findViewById(R.id.editText5);
		    View add_media = findViewById(R.id.loginbutton);		    
		    View submit = findViewById(R.id.signupsubmit);
		    //submit.setOnClickListener(this);
		    goallabel = (TextView) findViewById(R.id.textView4);
		    goal = (EditText) findViewById(R.id.password);
		    
		    orgtypespinner = (Spinner) findViewById(R.id.spinner1);
		        ArrayAdapter adapter = new ArrayAdapter(this,
		            android.R.layout.simple_spinner_dropdown_item, state_type);
		        orgtypespinner.setAdapter(adapter);
		        
		    requesttypespinner = (Spinner) findViewById(R.id.spinner2);
		        ArrayAdapter adapter1 = new ArrayAdapter(this,
		            android.R.layout.simple_spinner_dropdown_item, state_request);
		        requesttypespinner.setAdapter(adapter1);
		    requesttypespinner.setOnItemSelectedListener(new OnItemSelectedListener(){
		    	@Override
		    	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
		    		String value = (String) parentView.getItemAtPosition(position);
		    		if(value.equals("Money"))
		    		{
		    			goallabel.setVisibility(View.VISIBLE);
		    			goal.setVisibility(View.VISIBLE);
		    		}
		    		else
		    		{
		    			goallabel.setVisibility(View.INVISIBLE);
		    			goal.setVisibility(View.INVISIBLE);
		    		}
		    	}
		    	@Override
		        public void onNothingSelected(AdapterView<?> parentView) {
		            // your code here
		        }

		    });
		    
		    currentUser = ParseUser.getCurrentUser();
		    username.setText(currentUser.get("orgName").toString());
		    submit.setOnClickListener(new OnClickListener(){
		    	public void onClick(View arg0)
		    	{
		    		if (username.equals("") || whatfor.equals("") || description.equals("") || website.equals("")) {
						Toast.makeText(getApplicationContext(),
								"Please complete the request form",
								Toast.LENGTH_LONG).show();

					} else {
						ParseObject requestData = new ParseObject("request");
						requestData.put("orgName",username.getText().toString());
						requestData.put("orgType",orgtypespinner.getSelectedItem());
						requestData.put("requestType",requesttypespinner.getSelectedItem());
						requestData.put("goal",goal.getText().toString());
						requestData.put("whatFor", whatfor.getText().toString());
						requestData.put("description", description.getText().toString());
						requestData.put("website", website.getText().toString());
						requestData.saveInBackground( new SaveCallback(){
							public void done(ParseException e) {
								if (e == null) {
									Toast.makeText(getApplicationContext(),
											"Successfully submitted request.",
											Toast.LENGTH_LONG).show();
									Intent intent = new Intent(
											Request.this,
											Tab.class);
									startActivity(intent);
								}
								else
								{
									
									Toast.makeText(getApplicationContext(),
											"Request submit failed, please try again. Error: " + e.toString(), Toast.LENGTH_LONG)
											.show();
								}
							}
						});
					}
		    	}
		    });
	  }

		
		
		
}