package edu.fau.group4.donateme;

import org.apache.commons.validator.UrlValidator;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressWarnings("deprecation")
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
	 Bundle b;
	 TextView howtohelptxtview;
	 EditText howtohelpedit;
	 String howtohelptxt;
	 
	 private MusicService mp3Service;
		private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
		        
		        public void onServiceConnected(ComponentName arg0, IBinder service) {
		            LocalBinder binder = (LocalBinder) service;
		        	mp3Service = binder.getService();
		        	mp3Service.playSong(getBaseContext());
		        	if(!GlobalLayout.soundEnabled) mp3Service.mute(getBaseContext());
		        }
		 
		        @Override
		        public void onServiceDisconnected(ComponentName arg0) 
		        {
		 
		        }
		 
		    };
		    @Override
		    protected void onDestroy() {
		        unbindService(this.mp3PlayerServiceConnection);
		        super.onDestroy();
		    }
		
	 
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		    View v = this.getWindow().getDecorView();
		    v.setBackgroundColor(GlobalLayout.backgroundColor);
		    Intent connectionIntent = new Intent(this, MusicService.class);
	        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
		  setContentView(R.layout.request);
		  

		    ((Button) findViewById(R.id.addMediaButton)).setTextSize(GlobalLayout.buttonFontSize);
		    ((Button) findViewById(R.id.signupSubmit)).setTextSize(GlobalLayout.buttonFontSize);
		    ((TextView) findViewById(R.id.welcometxtview)).setTextSize(GlobalLayout.headerFontSize);
		  
		  currentUser = ParseUser.getCurrentUser();
		  username = (EditText) findViewById(R.id.username);
		  whatfor = (EditText) findViewById(R.id.stateedit);
		  description = (EditText) findViewById(R.id.editText4);
		  website = (EditText) findViewById(R.id.editText5);
		  howtohelpedit = (EditText) findViewById(R.id.howtohelpedit);
		  howtohelptxtview = (TextView) findViewById(R.id.howtohelptxtview);
		  b = getIntent().getExtras();
		  
		    View add_media = findViewById(R.id.addMediaButton);
		    add_media.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				Intent i = new Intent(Request.this,AddMedia.class);
				Bundle b = new Bundle();
				b.putString("orgName",username.getText().toString());
				b.putString("orgType",(String) orgtypespinner.getSelectedItem());
				b.putString("requestType",(String) requesttypespinner.getSelectedItem());
				b.putString("goal",goal.getText().toString());
				b.putString("whatFor", whatfor.getText().toString());
				b.putString("description", description.getText().toString());
				b.putString("website", website.getText().toString());
				b.putString("howToHelp",howtohelpedit.getText().toString());
				i.putExtras(b);
				finish();
				startActivity(i);
					
				}
			});
		    View submit = findViewById(R.id.signupSubmit);
		    //submit.setOnClickListener(this);
		    goallabel = (TextView) findViewById(R.id.textView4);
		    goal = (EditText) findViewById(R.id.password);
		    
		    orgtypespinner = (Spinner) findViewById(R.id.spinner1);
		        ArrayAdapter adapter = new ArrayAdapter(this,
		            android.R.layout.simple_spinner_dropdown_item, state_type);
		        orgtypespinner.setAdapter(adapter);
		        int orgpos = adapter.getPosition(currentUser.get("orgType")); 
		      orgtypespinner.setSelection(orgpos);
		      
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
		    			howtohelpedit.setVisibility(View.INVISIBLE);
		    			howtohelptxtview.setVisibility(View.INVISIBLE);
		    		}
		    		else
		    		{
		    			goallabel.setVisibility(View.INVISIBLE);
		    			goal.setVisibility(View.INVISIBLE);
		    			howtohelpedit.setVisibility(View.VISIBLE);
		    			howtohelptxtview.setVisibility(View.VISIBLE);
		    		}
		    	}
		    	@Override
		        public void onNothingSelected(AdapterView<?> parentView) {
		            // your code here
		        }

		    });
		    
		    
		    Boolean hasAddress = currentUser.has("geoPoint");
		    if(!hasAddress)
		    {
		    	Toast.makeText(getApplicationContext(),
						"Before you can make a request, you must add an address.",
						Toast.LENGTH_LONG).show();
		    	Intent i = new Intent(Request.this,Settings.class);
		    	startActivity(i);
		    }
		    username.setText(currentUser.get("orgName").toString());
		    submit.setOnClickListener(new OnClickListener(){
		    	public void onClick(View arg0)
		    	{
		    		String usernametxt = username.getText().toString();
		    		String whatfortxt = whatfor.getText().toString();
		    		String descriptiontxt = description.getText().toString();
		    		String websitetxt = website.getText().toString();
		    		String howtohelptxt = howtohelpedit.getText().toString();
		    		String requesttypetxt = requesttypespinner.getSelectedItem().toString();
		    		if (usernametxt.equals("") || whatfortxt.equals("") || descriptiontxt.equals("") || websitetxt.equals("")  || ( howtohelptxt.equals("") && !requesttypetxt.equals("Money"))) {
						Toast.makeText(getApplicationContext(),
								"Please complete the request form",
								Toast.LENGTH_LONG).show();

					}
		    		
		    		else {
						
						UrlValidator urlValid = new UrlValidator();
						if(urlValid.isValid(websitetxt))
						{
							if(requesttypetxt.equals("Money") && !currentUser.has("paypalEmail"))
							{
								Toast.makeText(getApplicationContext(),
										"To make a monetary request, please add a paypal email in the settings.", Toast.LENGTH_LONG)
										.show();
							}
							else
							{
						ParseObject requestData = new ParseObject("request");
						requestData.put("orgName",usernametxt);
						requestData.put("orgType",orgtypespinner.getSelectedItem());
						requestData.put("requestType",requesttypetxt);
						requestData.put("goal",goal.getText().toString());
						requestData.put("whatFor", whatfortxt);
						requestData.put("description", descriptiontxt);
						requestData.put("website", websitetxt);
						requestData.put("geoPoint",currentUser.get("geoPoint"));
						requestData.put("howToHelp",howtohelptxt);
						if(requesttypetxt.equals("Money"))
						{
							requestData.put("paypalEmail",currentUser.get("paypalEmail"));
						}
						byte[] imageBytes = null;
						if(b != null)
						{
						imageBytes = (byte[]) b.get("imageData");
						
						}if(imageBytes == null)
						{
							imageBytes = new byte[] {0};
						}
						ParseFile imageFile = new ParseFile("image.png",imageBytes);
						requestData.put("orgImage",imageFile);
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
		    		else
		    		{
		    			Toast.makeText(getApplicationContext(),
								"Please submit a valid url. E.g. http://google.com", Toast.LENGTH_LONG)
								.show();
		    		}
		    	}}
		    });
		    if(b != null)
			  {
		    	username.setText(b.getString("orgName"));
				orgtypespinner.setSelection(adapter.getPosition(b.getString("orgType")));
				requesttypespinner.setSelection(adapter.getPosition(b.getString("requestType")));
				goal.setText(b.getString("goal"));
				whatfor.setText(b.getString("whatFor"));
				description.setText(b.getString("description"));
				website.setText(b.getString("website"));
				howtohelpedit.setText(b.getString("howToHelp"));
			  }
	  }

		
		
		
}