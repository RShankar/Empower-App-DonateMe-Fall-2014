package edu.fau.group4.donateme;

import edu.fau.group4.donateme.MusicService.LocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;



public class Thanks extends Activity {
	
	private MusicService mp3Service;
	private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
	        
	        public void onServiceConnected(ComponentName arg0, IBinder service) {
	            LocalBinder binder = (LocalBinder) service;
	        	mp3Service = binder.getService();
	        	mp3Service.playSong(getBaseContext());
	        }
	 
	        @Override
	        public void onServiceDisconnected(ComponentName arg0) {
	 
	        }
	 
	    };
	    @Override
	    protected void onDestroy() {
	        unbindService(this.mp3PlayerServiceConnection);
	        super.onDestroy();
	    }
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent connectionIntent = new Intent(this, MusicService.class);
        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
	    View v = this.getWindow().getDecorView();
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.thanks);
    }
    
    public void finishThanks(View v) {
        Thanks.this.finish();
    }
}