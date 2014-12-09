package edu.fau.group4.donateme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
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
	 Spinner request;
	 ListView lv;
	 Context thisContext;
	 Location mCurrentLocation;
	 ParseGeoPoint currentGeo;
	 LocationClient mLocationClient;
	 ArrayList<RequestObject> requestArray = new ArrayList<RequestObject>();
	 ArrayAdapter<RequestObject> adapter;
	 byte[] imageArray;
	
	 private String[] state_type = { "All Types", "Research", "For Profit", "Not For Profit"};
	 private String[] state_request = { "All Requests", "Money","Clothes","Food"};
	 private String[] state_distance = {"Any Distance", "5 Miles", "15 Miles", "50 Miles", "100 Miles"};
	 
	 TextView text_type;
	 TextView text_distance;
	 
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
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		    View v = this.getWindow().getDecorView();
		    v.setBackgroundColor(GlobalLayout.backgroundColor);
		  setContentView(R.layout.browse);
		  
		  startService(new Intent(this, MusicService.class));
		  Intent connectionIntent = new Intent(this, MusicService.class);
	        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
		  
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
	        request = (Spinner) findViewById(R.id.spinner3);
	        ArrayAdapter requestadapter = new ArrayAdapter(this,
	            android.R.layout.simple_spinner_dropdown_item, state_request);
	        request.setAdapter(requestadapter);
	        request.setOnItemSelectedListener(new OnItemSelectedListener(){
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
	  double getDistance(ParseGeoPoint currentGeo2, ParseGeoPoint geo)
		{
			double R = 6371; // radius of Earth (km)
			double x1 = currentGeo2.getLatitude() * Math.PI/180.0;
			double x2 = geo.getLatitude() * Math.PI/180.0;
			double y1 = currentGeo2.getLongitude() * Math.PI/180.0;
			double y2 = geo.getLongitude() * Math.PI/180.0;
			double z1 = x2-x1;
			double z2 = y2-y1;

			double a = Math.sin(z1/2) * Math.sin(z1/2) + Math.cos(x1) * Math.cos(x2) * Math.sin(z2/2) * Math.sin(z2/2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			
			return R * c;
		}
	  private void getRequests()
	  {
		 ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("request");
		  query.findInBackground(new FindCallback<ParseObject>() {

			        
			        public void done(List<ParseObject> objects, ParseException e) {
			            if (e == null) {
			             
			                for (final ParseObject requestsObject : objects) {
			                	final ParseGeoPoint geo = (ParseGeoPoint)requestsObject.get("geoPoint");
			                	{
			                	ParseFile imageFile = (ParseFile)requestsObject.get("orgImage");
			            		imageFile.getDataInBackground(new GetDataCallback() {
			            			
			            			@Override
			            			public void done(byte[] imageData, ParseException arg1) {
			            				// TODO Auto-generated method stub
			            				if(arg1 == null){
			            					imageArray = imageData;
			            				}
			            				double distance = getDistance(currentGeo,geo);
					                	distance = Math.round(distance*100.0)/100.0;
					                   requestArray.add(new RequestObject( requestsObject.get("orgName").toString(),
					                		   requestsObject.get("orgType").toString(),
					                		   requestsObject.get("requestType").toString(),
					                		   requestsObject.get("whatFor").toString(),
					                		   requestsObject.get("description").toString(),
					                		   requestsObject.get("website").toString(),
					                		   requestsObject.get("goal").toString(),requestsObject.getObjectId(),
					                		   geo,distance,requestsObject.get("howToHelp").toString(),imageArray));
					                   updateRequestList();
			            			}
			            		});
			                	
			                		
			                }
			              
			            }} else {
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
         String requestfilter = request.getSelectedItem().toString();
         ArrayList<RequestObject> updatedArray = new ArrayList<RequestObject>();
         for(RequestObject req : requestArray)
         {
         	double orgdistance = req.distance;
         	String orgtype = req.orgType;
         	String orgrequest = req.requestType;
         if(typefilter.contains(orgtype)||typefilter.contains("All Types"))
         {if(requestfilter.contains(orgrequest)|| requestfilter.contains("All Requests"))
         {
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
         }
         Collections.sort(updatedArray, new CustomComparator());
         adapter = new RequestAdapter(thisContext,R.layout.list,updatedArray,currentGeo);			                
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
		  if(mCurrentLocation != null)
			  currentGeo = new ParseGeoPoint(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
		  else
		  {
			  Toast.makeText(getBaseContext(), "no valid gps", Toast.LENGTH_SHORT).show();
			  currentGeo = new ParseGeoPoint(0,0);
		  }
		  getRequests();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
		
}