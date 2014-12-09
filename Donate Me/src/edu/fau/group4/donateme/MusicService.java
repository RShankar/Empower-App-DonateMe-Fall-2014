package edu.fau.group4.donateme;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service 
{
    public final IBinder localBinder = new LocalBinder();
    private MediaPlayer mplayer;
    private boolean created = false;
    private boolean isMuted = false;
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }
     
 
    public class LocalBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
 
    public void playSong(Context c) 
    {
        if(!created)
        {
        	switch(GlobalLayout.songSelect)
        	{
        	case 1:
        		this.mplayer = MediaPlayer.create(c, R.raw.test);    
        		break;
        	case 2:
        		this.mplayer = MediaPlayer.create(c, R.raw.metalica);    
        		break;
        	default:
        		this.mplayer = MediaPlayer.create(c, R.raw.test);    
    		
        	}
                 
            this.mplayer.setLooping(true);
            created = true;
        }
        this.mplayer.start();
 
    }
    
    
	public void toggleMuteMedia(Context c)
	{
		if(isMuted)
		{
			this.mplayer.setVolume(1, 1);
			isMuted = false;
		}
		else
		{
			this.mplayer.setVolume(0, 0);
			isMuted = true;
		}
	
	}
	
	public void mute(Context c)
	{
		this.mplayer.setVolume(0, 0);
		isMuted = true;
	}

	public boolean getMuteStatus()
	{
		return isMuted;
	}
	
	public void pauseSong(Context c) 
	{
		this.mplayer.pause();
	}
}
 