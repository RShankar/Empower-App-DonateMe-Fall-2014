package edu.fau.group4.donateme;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Geocoder;

import com.google.android.maps.GeoPoint;

public class Address {
	
	
	
	public GeoPoint getLocationFromAddress(String strAddress, Context context){

		Geocoder coder = new Geocoder(context);
		List<android.location.Address> address = null;
		GeoPoint p1 = null;

		    try {
				address = coder.getFromLocationName(strAddress,5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if (address == null) {
		        return null;
		    }
		    android.location.Address location = address.get(0);
		    location.getLatitude();
		    location.getLongitude();

		    p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
		                      (int) (location.getLongitude() * 1E6));

		    return p1;
		
	}
}
	

