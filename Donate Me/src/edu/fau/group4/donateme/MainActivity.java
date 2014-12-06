package edu.fau.group4.donateme;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class MainActivity extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		// Set up click listeners for all the buttons
	    View button1 = findViewById(R.id.button1);
	    button1.setOnClickListener(this);
	   
    }

   
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	      case R.id.button1:
	    	 Intent i = new Intent(MainActivity.this, Welcomepage.class);	         
			 startActivity(i);
	         break;	          
		}
	}   
   
}


