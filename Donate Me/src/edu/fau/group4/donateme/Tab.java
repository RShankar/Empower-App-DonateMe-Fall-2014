package edu.fau.group4.donateme;


import com.parse.ParseUser;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Tab extends TabActivity
{
            /** Called when the activity is first created. */
            @SuppressWarnings("deprecation")
            
            ParseUser currentUser;
       	 String isOrg;
         String welcomeName;
			@Override
            public void onCreate(Bundle savedInstanceState)
            {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.tab);
                    TextView welcome = (TextView) findViewById(R.id.welcometxtview);
                    currentUser = ParseUser.getCurrentUser();
        		    isOrg = currentUser.get("isOrg").toString();
        		    
                    TabHost tabHost = getTabHost();
                    TabHost.TabSpec spec;
                    Intent intent;

                    intent = new Intent().setClass(this, Browse.class);
                    spec = tabHost.newTabSpec("First").setIndicator("Browse")
                                  .setContent(intent);
                    tabHost.addTab(spec);
                    
                    if(isOrg == "true")
                    {
                    	intent = new Intent().setClass(this, Request.class);
                    	spec = tabHost.newTabSpec("Second").setIndicator("Request")
                    		.setContent(intent);
                    	tabHost.addTab(spec);
                    	welcomeName = currentUser.get("orgName").toString();
                    }
                    else
                    {
                    	welcomeName = currentUser.get("firstName").toString()+" "+currentUser.get("lastName").toString();
                    }
                    View logout = findViewById(R.id.logoutbutton);
        		    logout.setOnClickListener(new OnClickListener()
        		    {
        		    	public void onClick(View arg0)
        		    	{	
        		    		ParseUser.logOut();
        		    		finish();
        		    		 Intent i1 = new Intent(Tab.this, MainActivity.class);	         
        					 startActivity(i1);
        		    		
        		    	}
        		    });
                   
            }
} 
