package edu.fau.group4.donateme;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tab);
	         
	        TabHost tabHost = getTabHost();
	         
	        // Tab for Photos
	        TabSpec photospec = tabHost.newTabSpec("Photos");
	        // setting Title and Icon for the Tab
	        photospec.setIndicator("Photos");
	        Intent photosIntent = new Intent(this, Browse.class);
	        photospec.setContent(photosIntent);
	         
	        // Tab for Songs
	        TabSpec songspec = tabHost.newTabSpec("Songs");        
	        songspec.setIndicator("Songs");
	        Intent songsIntent = new Intent(this, Request.class);
	        songspec.setContent(songsIntent);
	         
	        
	         
	        // Adding all TabSpec to TabHost
	        tabHost.addTab(photospec); // Adding photos tab
	        tabHost.addTab(songspec); // Adding songs tab
	       
	    }

}
