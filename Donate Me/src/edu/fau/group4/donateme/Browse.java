package edu.fau.group4.donateme;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


public class Browse extends Activity
{
	 Spinner type;
	 Spinner distance;
	 ListView lv;
	 Context thisContext;
	 ArrayList<RequestObject> requestArray = new ArrayList<RequestObject>();
	 ArrayAdapter<RequestObject> adapter;
	 
	 private String[] state_type = { "Community Service", "Financial", "Goods", "Goodwill", "Services"};
	 private String[] state_distance = { "5 miles", "15 miles", "50 miles", "100 miles"};
	 
	 TextView text_type;
	 TextView text_distance;
	  @Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.browse);
		   getRequests();
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
			                   requestArray.add(new RequestObject( requestsObject.get("orgName").toString(),
			                		   requestsObject.get("orgType").toString(),
			                		   requestsObject.get("requestType").toString(),
			                		   requestsObject.get("whatFor").toString(),
			                		   requestsObject.get("description").toString(),
			                		   requestsObject.get("website").toString(),
			                		   requestsObject.get("goal").toString(),
			                		   requestsObject.getObjectId()));
			                }
			                adapter = new RequestAdapter(thisContext,R.layout.list,requestArray);
					 		lv.setAdapter(adapter); 
			            } else {
			            	ArrayList<String> strArr = new ArrayList<String>();
			            	strArr.add("There was an error retrieving requests.\r\nPlease try again");
			            	ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(thisContext, android.R.layout.simple_list_item_2,strArr);
			            	lv.setAdapter(stringAdapter);
			            }
			            
			        }
			    });
	  }
		
}