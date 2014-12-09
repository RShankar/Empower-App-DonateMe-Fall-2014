package edu.fau.group4.donateme;


import com.parse.ParseUser;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class Tab extends TabActivity
{
            /** Called when the activity is first created. */
            
            
            ParseUser currentUser;
       	 String isOrg;
         String welcomeName = "Welcome ";
         
         private MusicService mp3Service;
     	private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() 
     	{
     	        
     	        public void onServiceConnected(ComponentName arg0, IBinder service) 
     	        {
     	            LocalBinder binder = (LocalBinder) service;
     	        	mp3Service = binder.getService();
     	        	mp3Service.playSong(getBaseContext());
     	        }
     	 
     	        @Override
     	        public void onServiceDisconnected(ComponentName arg0) 
     	        {
     	 
     	        }
     	 
 	    };
 	    @Override
 	    protected void onDestroy() 
 	    {
 	        unbindService(this.mp3PlayerServiceConnection);
 	        super.onDestroy();
 	    }
     	
         
			@Override
            public void onCreate(Bundle savedInstanceState)
            {
                    super.onCreate(savedInstanceState);
            	    View v = this.getWindow().getDecorView();
            	    Intent connectionIntent = new Intent(this, MusicService.class);
        	        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
            	    v.setBackgroundColor(GlobalLayout.backgroundColor);
                    setContentView(R.layout.tab);
                    
            	    ((Button) findViewById(R.id.logoutbutton)).setTextSize(GlobalLayout.buttonFontSize);
            	    ((TextView) findViewById(R.id.welcometxtview)).setTextSize(GlobalLayout.labelFontSize);
                    
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
