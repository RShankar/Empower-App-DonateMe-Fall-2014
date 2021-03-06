package edu.fau.group4.donateme;

import com.google.android.maps.GeoPoint;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity{

	EditText addressedit;
	EditText cityedit;
	EditText stateedit;
	EditText paypaledit;
	TextView paypal;
	String paypalEmail;
	String address;
	String city;
	String state;
	ParseUser currentUser;
	String isOrg;
	ImageView muteIcon;
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
	
	
	 public void onCreate(Bundle savedInstanceState)
	  {
		  	super.onCreate(savedInstanceState);
	        setContentView(R.layout.settings);
		    View v = this.getWindow().getDecorView();
		    v.setBackgroundDrawable(GlobalLayout.gradient);
		    Intent connectionIntent = new Intent(this, MusicService.class);
	        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);

		    ((Button) findViewById(R.id.settingsback)).setTextSize(GlobalLayout.buttonFontSize);
		    ((Button) findViewById(R.id.logoutButton)).setTextSize(GlobalLayout.buttonFontSize);
		    ((Button) findViewById(R.id.settingsave)).setTextSize(GlobalLayout.buttonFontSize);		    
		    ((TextView) findViewById(R.id.settingstxtview)).setTextSize(GlobalLayout.labelFontSize);
		  currentUser = ParseUser.getCurrentUser();
		  isOrg = currentUser.get("isOrg").toString();
		  
		  addressedit = (EditText) findViewById(R.id.addressedit);
		  
		  cityedit = (EditText) findViewById(R.id.cityedit);
		  stateedit = (EditText) findViewById(R.id.stateedit);
		  if(isOrg.equals("true")){
		  stateedit.setText(currentUser.get("state").toString());
		  cityedit.setText(currentUser.get("city").toString());
		  addressedit.setText(currentUser.get("address").toString());
		  }
		  TextView addresstxtview = (TextView) findViewById(R.id.addresstxtview);
		  TextView citytxtview = (TextView) findViewById(R.id.citytxtview);
		  TextView statetxtview = (TextView) findViewById(R.id.statetxtview);
		  paypaledit = (EditText) findViewById(R.id.paypalemailedit);
		  paypal = (TextView) findViewById(R.id.paypaltxtview);
		  if(currentUser.has("paypalEmail"))paypaledit.setText(currentUser.get("paypalEmail").toString());
		  Button save = (Button) findViewById(R.id.settingsave);
		  muteIcon = (ImageView) findViewById(R.id.muteimageview);
		  muteIcon.setOnClickListener(new OnClickListener() 
		  {			
			@Override
			public void onClick(View v) {
				mp3Service.toggleMuteMedia(getBaseContext());
				
			}
		});
		  save.setOnClickListener(new OnClickListener(){
			 public void onClick(View arg0)
			 {
				 address = addressedit.getText().toString();
				 city = cityedit.getText().toString();
				 state = stateedit.getText().toString();
				 if(address.equals("") || city.equals("") || state.equals(""))
				 {
					 Toast.makeText(getApplicationContext(),
								"Please enter a complete address.",
								Toast.LENGTH_LONG).show();
				 }
				 else
				 {
				 Address getAddress = new Address();
				 GeoPoint p1 = null;
				 p1 = getAddress.getLocationFromAddress(address+","+city+","+state,Settings.this);
				 if(p1 != null)
				 {
				 currentUser.put("address", address);
				 currentUser.put("city",city);
				 currentUser.put("state", state);
				 ParseGeoPoint geo = new ParseGeoPoint(p1.getLatitudeE6()/1E6,p1.getLongitudeE6()/1E6);
				 paypalEmail = paypaledit.getText().toString();
				 if(!paypalEmail.equals(""))currentUser.put("paypalEmail", paypalEmail);
				 currentUser.put("geoPoint",geo);
				 
				 currentUser.saveInBackground( new SaveCallback(){
						public void done(ParseException e) {
							if (e == null) {
								Toast.makeText(getApplicationContext(),
										"Successfully updated address.",
										Toast.LENGTH_LONG).show();
								Intent intent = new Intent(
										Settings.this,
										Tab.class);
								startActivity(intent);
							}
							else
							{
								
								Toast.makeText(getApplicationContext(),
										"Failed to save address. Error: " + e.toString(), Toast.LENGTH_LONG)
										.show();
							}
						}
					});
				 }
				 else
				 {
					 Toast.makeText(getApplicationContext(),
								"Unable to get GeoLocation for supplied address. Please double check the address and try again. ", Toast.LENGTH_LONG)
								.show();
				 }
				}
			 }
		  });
		  if(isOrg.equals("true"))
		  {
			  addressedit.setVisibility(View.VISIBLE);
			  cityedit.setVisibility(View.VISIBLE);
			  stateedit.setVisibility(View.VISIBLE);
			  addresstxtview.setVisibility(View.VISIBLE);
			  citytxtview.setVisibility(View.VISIBLE);
			  statetxtview.setVisibility(View.VISIBLE);
			  save.setVisibility(View.VISIBLE);
			  paypal.setVisibility(View.VISIBLE);
			  paypaledit.setVisibility(View.VISIBLE);
		  }
		  else
		  {
			  addressedit.setVisibility(View.INVISIBLE);
			  cityedit.setVisibility(View.INVISIBLE);
			  stateedit.setVisibility(View.INVISIBLE);
			  addresstxtview.setVisibility(View.INVISIBLE);
			  citytxtview.setVisibility(View.INVISIBLE);
			  statetxtview.setVisibility(View.INVISIBLE);
			  save.setVisibility(View.INVISIBLE);
			  paypal.setVisibility(View.INVISIBLE);
			  paypaledit.setVisibility(View.INVISIBLE);
		  }
		  Button bt = (Button) findViewById(R.id.settingsback);
		  bt.setOnClickListener(new OnClickListener(){
			 public void onClick(View arg0)
			 {
				finish();
			 }
		  });
		  Button logout = (Button) findViewById(R.id.logoutButton);
		  logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser.logOut();
	    		finish();
	    		 Intent i1 = new Intent(Settings.this, MainActivity.class);	         
				 startActivity(i1);
			}
		});
		  
	  }
	
}
