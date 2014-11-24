package edu.fau.group4.donateme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Signin extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		// Set up click listeners for all the buttons	    
		View submit = findViewById(R.id.button1);
	    submit.setOnClickListener(this);
	   
	    View back = findViewById(R.id.button2);
	    back.setOnClickListener(this);
    }
   
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	      case R.id.button2:
	    	 Intent i = new Intent(Signin.this, Tab.class);	         
			 startActivity(i);
	         break;
	      case R.id.button1:
		    	 Intent i1 = new Intent(Signin.this, Welcomepage.class);	         
				 startActivity(i1);
		         break;
		}
}
}