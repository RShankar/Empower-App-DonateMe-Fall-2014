package com.alexandre.onetraining;

import myPackage.Car;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



public class MainActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    TextView tv =  (TextView) findViewById(R.id.textView1);  
    
	    String text = Car.getNoOfCars()+ " cars \n\n"; 

		Car myCar = new Car();
		text = text + myCar.printToString();
		text = text + Car.getNoOfCars()+ " cars \n\n";	
					     	
		Car yourCar = new Car("Porsche", "Cayman", 2007, "Blue", 10000, 2);
		text = text + yourCar.printToString();
		text = text + Car.getNoOfCars()+ " cars \n\n";

		Car myNextCar = new Car("Porsche", "Cayman");
		text = text + myNextCar.printToString();
		text = text + Car.getNoOfCars()+ " cars \n\n";

		Car.noOfCars = 2;
		myCar.paint("GREEN");     //will work because method paint is public
		
		tv.setText(text);
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


