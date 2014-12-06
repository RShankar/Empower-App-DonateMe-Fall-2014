package edu.fau.group4.donateme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Howtohelp extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.howtohelp);
		
		// Set up click listeners for all the buttons	    
		View logout = findViewById(R.id.loginbutton);
	    logout.setOnClickListener(this);
	   
	    View back = findViewById(R.id.signupsubmit);
	    back.setOnClickListener(this);
	    
	    View donate = findViewById(R.id.button3);
	    donate.setOnClickListener(this);
    }
   
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	      case R.id.loginbutton:
	    	 Intent i = new Intent(Howtohelp.this, Welcomepage.class);	         
			 startActivity(i);
	         break;
	      case R.id.signupsubmit:
		    	 Intent i1 = new Intent(Howtohelp.this, Tab.class);	         
				 startActivity(i1);
		         break;
	      case R.id.button3:
		    	 Intent i2 = new Intent(Howtohelp.this, Thanks.class);	         
				 startActivity(i2);
		         break;
		         
		}
}
}