package edu.fau.group4.donateme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;



public class Thanks extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    View v = this.getWindow().getDecorView();
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.thanks);
    }
    
    public void finishThanks(View v) {
        Thanks.this.finish();
    }
}