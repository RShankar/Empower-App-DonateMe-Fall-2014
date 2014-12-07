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
	    	LatLng ll = new LatLng(i.getDoubleExtra("latitude", 0.0),i.getDoubleExtra("longitude", 0.0));
	    	drawMap(ll);
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
	
	private void drawMap(LatLng loc)
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
				
				CameraUpdate center = CameraUpdateFactory.newLatLng(getGPS());
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);

				googleMap.moveCamera(center);
				googleMap.animateCamera(zoom);
				googleMap.addMarker(new MarkerOptions()
		        	.position(loc)
		        	.title("location"));
			}
		}
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

