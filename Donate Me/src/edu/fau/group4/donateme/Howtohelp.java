package edu.fau.group4.donateme;

import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Howtohelp extends Activity
{
	private GoogleMap googleMap;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		
	    boolean isMonetary = false;
	    
	    if(isMonetary)
	    {
	    	setContentView(R.layout.howtohelp);
			
			// Set up click listeners for all the buttons	
	    }
	    else
	    {
	    	Intent i = getIntent();
	    	setContentView(R.layout.non_monetary);
	    	boolean gpsProvided = i.getBooleanExtra("gpsProvided", false);
	    	if(true)
	    	{
		    	LatLng ll = new LatLng(i.getDoubleExtra("latitude", 0.0),i.getDoubleExtra("longitude", 0.0));
		    	String name = i.getStringExtra("name");
		    	drawMap(ll,name);
	    	}
	    }
    }
   
	public void onBack(View v) 
	{

		Intent i = new Intent(v.getContext(), HelpOrg.class);	         
		startActivity(i);
		
	}
	
	public void onLogout(View v) 
	{
		Intent i = new Intent(v.getContext(), Welcomepage.class);	         
		startActivity(i);
	}
	
	public void onDonate(View v) 
	{
		Intent i = new Intent(v.getContext(), Thanks.class);	         
		startActivity(i);
	}
	
	private void drawMap(LatLng loc, String name)
	{
		
		if (googleMap == null) 
		{
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			// check if map is created successfully or not
			if (googleMap == null) 
			{
				Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}
			else
			{
				googleMap.setOnCameraChangeListener(new OnCameraChangeListener() 
				{
			        @Override
			        public void onCameraChange(CameraPosition cameraPosition)
			        {
			            //update map here
			        }
			    });
				LatLng gps = getGPS();
				CameraUpdate center = CameraUpdateFactory.newLatLng(gps);
				float distance = (float) getDistance(gps, loc);
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(13.8f);

				googleMap.moveCamera(center);
				googleMap.animateCamera(zoom);
				googleMap.addMarker(new MarkerOptions()
		        	.position(loc)
		        	.title(name + "\nDistance: " + String.format("%.2f", distance) + " km"));
				
				//(getDistance(gps, loc))
				
				//30 km, 10.5
				//10.1 km, 12.1
				//3.14 km, 13.8
				
			}
		}
	}
	
	
	//gets distance between 2 GPS coordinates
	double getDistance(LatLng gps1, LatLng gps2)
	{
		double R = 6371; // radius of Earth (km)
		double φ1 = gps1.latitude * Math.PI/180.0;
		double φ2 = gps2.latitude * Math.PI/180.0;
		double λ1 = gps1.longitude * Math.PI/180.0;
		double λ2 = gps2.longitude * Math.PI/180.0;
		double Δφ = φ2-φ1;
		double Δλ = λ2-λ1;

		double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) + Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ/2) * Math.sin(Δλ/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return R * c;
	}
	
	private LatLng getLocation() 
	{
	    // Get the location manager
		double lat,lon;
	    LocationManager locationManager = (LocationManager) 
	            getSystemService(LOCATION_SERVICE);
	    Criteria criteria = new Criteria();
	    String bestProvider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(bestProvider);
	    try 
	    {
	        lat = location.getLatitude();
	        lon = location.getLongitude();
	    } catch (Exception e) 
	    {
	        lat = 0;
	        lon = 0;
	    }
	    return new LatLng(lat,lon);
	}
	
	private LatLng getGPS() 
	{
		 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		 List<String> providers = lm.getProviders(true);

		 Location l = null;

		 for (int i = providers.size()-1; i >= 0; i--) 
		 {
			 l = lm.getLastKnownLocation(providers.get(i));
			 if (l != null) break;
		 }

		 if (l != null) 
		 {
			 return new LatLng(l.getLatitude(), l.getLongitude());
		 }
		 else
		 {
			 return new LatLng(0,0);
		 }	 
	}
}

