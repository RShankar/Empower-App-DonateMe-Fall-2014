package edu.fau.group4.donateme;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		profile = (ImageView) findViewById(R.id.imageView1);
		orgId = b.getString("orgId");		
		 View help = findViewById(R.id.BTN_howToHelp);
		 help.setOnClickListener(this);
		 View websiteButton = findViewById(R.id.button4);
		 websiteButton.setOnClickListener(this);
		 View back = findViewById(R.id.settingsback);
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
			case R.id.button4:
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