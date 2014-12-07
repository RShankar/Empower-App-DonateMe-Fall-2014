package edu.fau.group4.donateme;


import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Welcomepage extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		// Set up click listeners for all the buttons		
	    View button1 = findViewById(R.id.loginbutton);
	    button1.setOnClickListener(this);
	    View button2 = findViewById(R.id.signupsubmit);
	    button2.setOnClickListener(this);
    }

   //comment
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	      case R.id.signupsubmit:
	    	 Intent i = new Intent(Welcomepage.this, Login.class);	         
			 startActivity(i);
	         break;
	         // ...
	      case R.id.loginbutton:
	         Intent i1 = new Intent(this, Signup.class);
	         startActivity(i1);
	         break;	      
		}
	}


	private Intent intent(Welcomepage welcomepage, Class<Login> class1) {
		// TODO Auto-generated method stub
		return null;
	}
}
