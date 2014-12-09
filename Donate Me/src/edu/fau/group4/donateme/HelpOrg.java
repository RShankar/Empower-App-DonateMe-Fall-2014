package edu.fau.group4.donateme;


import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseUser;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpOrg extends Activity implements OnClickListener
{

	ParseUser currentUser;
	ImageView profile;
	String orgName;
	String whatFor;
	String description;
	String website;
	double dlat;
	double dlong;
	double clat;
	double clong;
	TextView welcome;
	TextView whatview;
	TextView descview;
	String orgId;
	String howtohelptxt;
	String orgType;
	String requestType;
	byte[] imageData;
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
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    View v = this.getWindow().getDecorView();
	    Intent connectionIntent = new Intent(this, MusicService.class);
        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
		setContentView(R.layout.orgpage);
		Bundle b = getIntent().getExtras();
		orgName = b.getString("orgName");
		whatFor = b.getString("whatFor");
		description = b.getString("description");
		website = b.getString("website");
		dlat = b.getDouble("dlat");
		dlong = b.getDouble("dlong");
		clat = b.getDouble("clat");
		clong = b.getDouble("clong");
		howtohelptxt = b.getString("howToHelp");
		orgType = b.getString("orgType");
		requestType = b.getString("requestType");
		imageData = b.getByteArray("imageData");
		welcome = (TextView) findViewById(R.id.welcometxtview);
		welcome.setText(orgName);		
		whatview = (TextView) findViewById(R.id.lasttxtview);
		whatview.setText(whatFor);		
		descview = (TextView) findViewById(R.id.textView3);
		descview.setText(description);
		profile = (ImageView) findViewById(R.id.muteimageview);
		orgId = b.getString("orgId");		
		 View help = findViewById(R.id.BTN_howToHelp);
		 help.setOnClickListener(this);
		 View websiteButton = findViewById(R.id.websiteButton);
		 websiteButton.setOnClickListener(this);
		 View back = findViewById(R.id.settingsBack);
		 back.setOnClickListener(this);
		 Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
		 profile.setImageBitmap(imageBitmap);
			
		
    }
    
    @Override
	public void onClick(View v) 
	{
    	Intent i;
    	
		switch (v.getId()) 
		{
			case R.id.BTN_howToHelp:
				i = new Intent(v.getContext(), Howtohelp.class);
				
				
				boolean gpsProvided = true;
				LatLng ll = new LatLng(dlat,dlong); //organization's gps if provided
				Bundle b = new Bundle();
				boolean isMonetary = false;
				if(requestType.equals("Money"))
				{
					isMonetary = true;
				}
				b.putBoolean("isMonetary",isMonetary);
				b.putBoolean("gpsProvided", gpsProvided);
				b.putDouble("longitude", ll.longitude);
				b.putDouble("latitude", ll.latitude);
				b.putString("name", orgName);
				b.putDouble("currentLat", clat);
				b.putDouble("currentLong", clong);
				b.putString("orgId", orgId);
				b.putString("howToHelp", howtohelptxt);
				b.putByteArray("imageArray", imageData);
				i.putExtras(b);
			    startActivity(i);
				break;
			case R.id.websiteButton:
				String url = website;
				i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				break;
			case R.id.settingsback:
				finish();
				break;
		}
	}
    
    public void onBack(View v) 
	{

		Intent i = new Intent(v.getContext(), Tab.class);	         
		startActivity(i);
		
	}
	
	public void onLogout(View v) 
	{
		Intent i = new Intent(v.getContext(), Welcomepage.class);	         
		startActivity(i);
	}
   
	
}