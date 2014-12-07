package edu.fau.group4.donateme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Browse extends Activity
{
	 Spinner type;
	 Spinner distance;
	 
	 private String[] state_type = { "Community Service", "Financial", "Goods", "Goodwill", "Services"};
	 private String[] state_distance = { "5 miles", "15 miles", "50 miles", "100 miles"};
	 
	 TextView text_type;
	 TextView text_distance;

	  @Override
	 public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.browse);
		 

	  }

		
}