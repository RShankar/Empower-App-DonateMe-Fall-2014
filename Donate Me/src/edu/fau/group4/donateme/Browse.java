package edu.fau.group4.donateme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.parse.FindCallback;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class Browse extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener
{
	 Spinner type;
	 Spinner distance;
	 ListView lv;
	 Context thisContext;
	 Location mCurrentLocation;
	 ParseGeoPoint currentGeo;
	 LocationClient mLocationClient;
	 ArrayList<RequestObject> requestArray = new ArrayList<RequestObject>();
	 ArrayAdapter<RequestObject> adapter;
	 
	
	 private String[] state_type = { "All Types", "Research", "For Profit", "Not For Profit"};
	 private String[] state_distance = {"Any Distance", "5 Miles", "15 Miles", "50 Miles", "100 Miles"};
	 
	 TextView text_type;
	 TextView text_distance;
	  @Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.browse);
		  mLocationClient = new LocationClient(this,this,this);
		   mLocationClient.connect();
		 
		   distance = (Spinner) findViewById(R.id.spinner1);
	        ArrayAdapter distanceadapter = new ArrayAdapter(this,
	            android.R.layout.simple_spinner_dropdown_item, state_distance);
	        distance.setAdapter(distanceadapter);
	        distance.setOnItemSelectedListener(new OnItemSelectedListener(){
	        	@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
	        		updateRequestList();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}	
			 });
        
	        type = (Spinner) findViewById(R.id.spinner2);
	        ArrayAdapter typeadapter = new ArrayAdapter(this,
	            android.R.layout.simple_spinner_dropdown_item, state_type);
	        type.setAdapter(typeadapter);
	        type.setOnItemSelectedListener(new OnItemSelectedListener(){
	        	@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
	        		updateRequestList();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}	
			 });
		   lv = (ListView) findViewById(R.id.listView1);
		   thisContext = this;
		   
		   
		   
	  }

	  private void getRequests()
	  {
		  ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("request");
		  query.findInBackground(new FindCallback<ParseObject>() {

			        
			        public void done(List<ParseObject> objects, ParseException e) {
			            if (e == null) {
			             
			                for (ParseObject requestsObject : objects) {
			                	ParseGeoPoint geo = (ParseGeoPoint)requestsObject.get("geoPoint");			                	
			                	double distance = currentGeo.distanceInMilesTo(geo);
			                	distance = Math.round(distance*100.0)/100.0;
			                   requestArray.add(new RequestObject( requestsObject.get("orgName").toString(),
			                		   requestsObject.get("orgType").toString(),
			                		   requestsObject.get("requestType").toString(),
			                		   requestsObject.get("whatFor").toString(),
			                		   requestsObject.get("description").toString(),
			                		   requestsObject.get("website").toString(),
			                		   requestsObject.get("goal").toString(),
			                		   requestsObject.getObjectId(),geo,distance));
			                		
			                }
			               updateRequestList();
			            } else {
			            	ArrayList<String> strArr = new ArrayList<String>();
			            	strArr.add("There was an error retrieving requests.\r\nPlease try again");
			            	ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(thisContext, android.R.layout.simple_list_item_2,strArr);
			            	lv.setAdapter(stringAdapter);
			            }
			            
			        }
			    });
	  }
	 public void updateRequestList()
	 {
		 String typefilter = type.getSelectedItem().toString();
         String distancefilter = distance.getSelectedItem().toString();
         ArrayList<RequestObject> updatedArray = new ArrayList<RequestObject>();
         for(RequestObject req : requestArray)
         {
         	double orgdistance = req.distance;
         	String orgtype = req.orgType; 
         if(typefilter.contains(orgtype)||typefilter.contains("All Types")){
     		if(distancefilter.contains("Any Distance")){
     			updatedArray.add(req);
     		}
     		else if(distancefilter.contains("5 Miles"))
     		{
     			if(orgdistance < 5)
     			{
     				updatedArray.add(req);
     			}
     		}
     		else if(distancefilter.contains("15 Miles"))
     		{
     			if(orgdistance < 15)
     			{
     				updatedArray.add(req);
     			}
     		}
     		else if(distancefilter.contains("50 Miles"))
     		{
     			if(orgdistance < 50)
     			{
     				updatedArray.add(req);
     			}
     		}
     		else if(distancefilter.contains("100 Miles"))
     		{
     			if(orgdistance < 100)
     			{
     				updatedArray.add(req);
     			}
     		}
     		}
         }
         
         Collections.sort(updatedArray, new CustomComparator());
         adapter = new RequestAdapter(thisContext,R.layout.list,updatedArray);			                
	 	lv.setAdapter(adapter);
	 	adapter.notifyDataSetChanged();
	 }
	  

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Unable to get GPS location. Please enable your GPS and try again.", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		  mCurrentLocation = mLocationClient.getLastLocation();
		   currentGeo = new ParseGeoPoint(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
		   getRequests();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
		
}