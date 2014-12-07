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
         String welcomeName = "Welcome ";
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

                    intent = new Intent(this, Browse.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    spec = tabHost.newTabSpec("First").setIndicator("Browse")
                                  .setContent(intent);
                    tabHost.addTab(spec);
                    
                    if(isOrg.equals("true"))
                    {
                    	intent = new Intent(this, Request.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    	spec = tabHost.newTabSpec("Second").setIndicator("Request")
                    		.setContent(intent);
                    	tabHost.addTab(spec);
                    	welcomeName += currentUser.get("orgName").toString();
                    }
                    else
                    {
                    	welcomeName += currentUser.get("firstName").toString()+" "+currentUser.get("lastName").toString();
                    }
                    welcome.setText(welcomeName);
                    View logout = findViewById(R.id.logoutbutton);
        		    logout.setOnClickListener(new OnClickListener()
        		    {
        		    	public void onClick(View arg0)
        		    	{	
        		    		
        		    		 Intent i1 = new Intent(Tab.this, Settings.class);	         
        					 startActivity(i1);
        		    		
        		    	}
        		    });
                   
            }
} 
