package edu.fau.group4.donateme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Signin extends Activity implements OnItemSelectedListener,OnClickListener{
	
	Spinner type;
	private String[] state_type = { "Charity"};
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		// Set up click listeners for all the buttons	    
		View submit = findViewById(R.id.button1);
	    submit.setOnClickListener(this);
	   
	    View back = findViewById(R.id.button2);
	    back.setOnClickListener(this);
	    System.out.println(state_type.length);
		//text_type = (TextView) findViewById(R.id.textView1);
		  
		  type = (Spinner) findViewById(R.id.spinner1);
		  ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, state_type);
		  adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  type.setAdapter(adapter_type);
		  type.setOnItemSelectedListener(this);	  
		  
    }
	@Override
	public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
		Spinner type = (Spinner)parent;
        Spinner distance = (Spinner)parent;
        if(type.getId() == R.id.spinner1)
        {
        	type.setSelection(position);
  		  	String type_state = (String) type.getSelectedItem();
  		// 	text_type.setText("Spinner1:  " + type_state);    
  		}        		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
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