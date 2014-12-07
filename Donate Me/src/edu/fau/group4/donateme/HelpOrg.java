package edu.fau.group4.donateme;

import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HelpOrg extends Activity implements OnClickListener
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orgpage);
		
		 View help = findViewById(R.id.BTN_howToHelp);
		 help.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) 
	{
    	Intent i;
		switch (v.getId()) 
		{
			case R.id.BTN_howToHelp:
				i = new Intent(v.getContext(), Howtohelp.class);

				boolean gpsProvided = true;
				LatLng ll = new LatLng(26.37376,-80.1061); //organization's gps if provided
				
				i.putExtra("gpsProvided", gpsProvided);
				i.putExtra("longitude", ll.longitude);
				i.putExtra("latitude", ll.latitude);
			    startActivity(i);
				break;
		}
	}
    
    public void onBack(View v) 
	{

		Intent i = new Intent(v.getContext(), Tab.class);	         
		startActivity(i);
		
	}
	
	public void onLogout(View v) 
	{
		Intent i = new Intent(v.getContext(), Welcomepage.class);	         
		startActivity(i);
	}
   
	
}