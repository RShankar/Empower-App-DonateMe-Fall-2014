package edu.fau.group4.donateme;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
    public final IBinder localBinder = new LocalBinder();
    private MediaPlayer mplayer;
    private boolean created = false;
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }
     
 
    public class LocalBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
 
    public void playSong(Context c) {
        if(!created){
            this.mplayer = MediaPlayer.create(c, R.raw.metalica);
            created = true;
        }
            this.mplayer.start();
         
    }
 
    public void pauseSong(Context c) {
        this.mplayer.pause();
    }
}
 