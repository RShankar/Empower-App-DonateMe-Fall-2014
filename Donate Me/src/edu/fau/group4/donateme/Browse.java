package edu.fau.group4.donateme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.parse.FindCallback;
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
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	 Button rakbutton;
	 ArrayList<RequestObject> requestArray = new ArrayList<RequestObject>();
	 ArrayAdapter<RequestObject> adapter;
	 byte[] imageArray;
	
	 private ArrayList<String> state_type = new ArrayList<String>();//{ "All Types", "Research", "For Profit", "Not For Profit"};
	 private String[] state_request = { "All Requests", "Money","Clothes","Food"};
	 private ArrayList<String> state_distance = new ArrayList<String>();
	 
	 TextView text_type;
	 TextView text_distance;
	 
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
		
	@Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		    View v = this.getWindow().getDecorView();
		    v.setBackgroundColor(GlobalLayout.backgroundColor);
		  setContentView(R.layout.browse);
		  
		  rakbutton = (Button) findViewById(R.id.signupsubmit);
		  rakbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		  startService(new Intent(this, MusicService.class));
		  Intent connectionIntent = new Intent(this, MusicService.class);
	        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
		  rakbutton = (Button) findViewById(R.id.signupsubmit);
		  rakbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<RequestObject> reqarr = new ArrayList<RequestObject>();
				for(RequestObject req : requestArray)
		         {
					if(req.requestType.equals("Money")) reqarr.add(req);
		         }
				if(!reqarr.isEmpty())
				{
					Random rng = new Random();
					int index = rng.nextInt(reqarr.size());
					RequestObject requestrak = reqarr.get(index);
					
					Intent i = new Intent(Browse.this,HelpOrg.class);
					Bundle b = new Bundle();
					b.putString("orgName",requestrak.orgName);
					b.putString("whatFor", requestrak.whatFor);
					b.putString("description", requestrak.description);
					b.putString("website", requestrak.website);
					b.putDouble("dlat", requestrak.geo.getLatitude());
					b.putDouble("dlong", requestrak.geo.getLongitude());
					if(currentGeo != null)
					{
						b.putDouble("clat", currentGeo.getLatitude());
						b.putDouble("clong", currentGeo.getLongitude());
					}
					else
					{
						b.putDouble("clat", 0.0);
						b.putDouble("clong", 0.0);
					}
					b.putString("orgId", requestrak.objectId);
					b.putString("orgType", requestrak.orgType);
					b.putString("howToHelp", requestrak.howToHelp);
					b.putString("requestType", requestrak.requestType);
					b.putByteArray("imageData", requestrak.imageArray);
					b.putString(("paypalEmail"), requestrak.paypalEmail);
					i.putExtras(b);
					startActivity(i);
				}
				else
				{
					Toast.makeText(Browse.this,"Sorry, there are no monetary requests available.",Toast.LENGTH_LONG).show();
				}
					
				
				
			}
		});
		  mLocationClient = new LocationClient(this,this,this);
		   mLocationClient.connect();
		 
		   distance = (Spinner) findViewById(R.id.spinner1);
		   
		   state_distance.add("Any Distance");
		   for(int i = 0; i < GlobalLayout.filterDistance.size(); i++)
		   {
			   state_distance.add(GlobalLayout.filterDistance.get(i) + " Miles");
		   }
			ArrayAdapter<String> distanceadapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_spinner_dropdown_item, state_distance);
	        distance.setAdapter(distanceadapter);
	        distance.setOnItemSelectedListener(new OnItemSelectedListener(){
	        	@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	        	{
	        		updateRequestList();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) 
				{
					// TODO Auto-generated method stub
					
				}	
			 });
        
	        type = (Spinner) findViewById(R.id.spinner2);
	        
	        state_type.add("All Types");
	        for(int i = 0; i < GlobalLayout.filterType.size(); i++)
	        {
	        	state_type.add(GlobalLayout.filterType.get(i));
	        }
	        
			ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(this,
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
	        ArrayAdapter<String> requestadapter = new ArrayAdapter<String>(this,
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
			double R = 3963.1676; // radius of Earth (mi)
			//convert (latitude, longitude) to radians (phi, theta)
			double phi1 = currentGeo2.getLatitude() * Math.PI/180.0;
			double phi2 = geo.getLatitude() * Math.PI/180.0;
			double theta1 = currentGeo2.getLongitude() * Math.PI/180.0;
			double theta2 = geo.getLongitude() * Math.PI/180.0;
			double dPhi = phi2-phi1;
			double dTheta = theta2-theta1;

			double halfChordSquare = Math.sin(dPhi/2) * Math.sin(dPhi/2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(dTheta/2) * Math.sin(dTheta/2);
			double distAngular = 2 * Math.atan2(Math.sqrt(halfChordSquare), Math.sqrt(1-halfChordSquare)); //angular arc-length between the two points (radians)
			
			return R * distAngular;
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
			            				double distance;
			            				if(currentGeo != null) distance = getDistance(currentGeo,geo);
			            				else distance = Double.POSITIVE_INFINITY;
					                	distance = Math.round(distance*100.0)/100.0;
					                	String paypalEmail = "";
					                	if(requestsObject.has("paypalEmail")) paypalEmail = requestsObject.get("paypalEmail").toString();
					                   requestArray.add(new RequestObject( requestsObject.get("orgName").toString(),
					                		   requestsObject.get("orgType").toString(),
					                		   requestsObject.get("requestType").toString(),
					                		   requestsObject.get("whatFor").toString(),
					                		   requestsObject.get("description").toString(),
					                		   requestsObject.get("website").toString(),
					                		   requestsObject.get("goal").toString(),requestsObject.getObjectId(),
					                		   geo,distance,requestsObject.get("howToHelp").toString(),imageArray,paypalEmail));
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
	  ArrayList<RequestObject> updatedArray;
	 public void updateRequestList()
	 {
		 String typefilter = type.getSelectedItem().toString();
         String distancefilter = distance.getSelectedItem().toString();
         String requestfilter = request.getSelectedItem().toString();
         updatedArray = new ArrayList<RequestObject>();
         for(RequestObject req : requestArray)
         {
        	 String orgtype = req.orgType;
        	 String orgrequest = req.requestType;
         	 if(orgrequest.equals("Money") && currentGeo == null) req.distance = 0.0f;
         	 double orgdistance = req.distance;
	         if(typefilter.equals(orgtype)||typefilter.equals("All Types"))
	         {
	        	 if(requestfilter.equals(orgrequest)|| requestfilter.equals("All Requests"))
	 
		         {
	        		 if(distancefilter.equals("Any Distance"))
	        		 {
	 	     			updatedArray.add(req);
	        		 }
	        		 else //check the filter list
	        		 {
			        	 for(int i = 0; i < GlobalLayout.filterDistance.size(); i++)
			        	 {
			        		 if(distancefilter.contains(GlobalLayout.filterDistance.get(i) + " Miles") && orgdistance < GlobalLayout.filterDistance.get(i))
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
			  currentGeo = null;//new ParseGeoPoint(0,0);
		  }
		  getRequests();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	public void randomActOfKindness(View v)
	{
		
	}
		
}