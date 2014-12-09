package edu.fau.group4.donateme;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AddMedia extends Activity {

	ImageView avatar;
	Button savebtn;
	private static final int SELECT_PICTURE = 42;
	private String selectedImagePath;
	ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
	Bundle b;
	byte[] imageData;
	
	
	private MusicService mp3Service;
	private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
	        
	        public void onServiceConnected(ComponentName arg0, IBinder service) {
	            LocalBinder binder = (LocalBinder) service;
	        	mp3Service = binder.getService();
	        	mp3Service.playSong(getBaseContext());
	        	if(!GlobalLayout.soundEnabled) mp3Service.mute(getBaseContext());
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
	
	
	public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		    View v = this.getWindow().getDecorView();
		    v.setBackgroundColor(GlobalLayout.backgroundColor);
		  setContentView(R.layout.addmedia);
		  Intent connectionIntent = new Intent(this, MusicService.class);
	        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
		  b = getIntent().getExtras();
		  savebtn = (Button) findViewById(R.id.savemedia);
		  savebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent i = new Intent(AddMedia.this,Request.class);			
				b.putByteArray("imageData", imageData);			
			i.putExtras(b);
			finish();
			startActivity(i);
			}
		});
		  avatar = (ImageView) findViewById(R.id.muteimageview);
		  avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setType("image/*");
				i.setAction(Intent.ACTION_OPEN_DOCUMENT);
				startActivityForResult(Intent.createChooser(i,
                        "Select Picture"), SELECT_PICTURE);
			}
		});
	  }
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK) {
	        if (requestCode == SELECT_PICTURE) {
	        	Uri selectedImageUri = data.getData();
	            if (Build.VERSION.SDK_INT < 19) {
	                selectedImagePath = getPath(selectedImageUri);
	                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
	              bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageStream);
	              imageData = imageStream.toByteArray();
	              int length = imageData.length;
	  	            if(length > 10000000)
		              {
	            	  Toast.makeText(getApplicationContext(),
	  						"Image must be smaller than 10mb",
	  						Toast.LENGTH_LONG).show();
	            	  Arrays.fill(imageData, (byte)0);
	              }
	            }
	            else {
	                ParcelFileDescriptor parcelFileDescriptor;
	                try {
	                    parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
	                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
	                    Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
	                    parcelFileDescriptor.close();
	                    image.compress(Bitmap.CompressFormat.PNG, 100, imageStream);
	  	              imageData = imageStream.toByteArray();
	  	              int length = imageData.length;
	  	            if(length > 10000000)
		              {
		            	  Toast.makeText(getApplicationContext(),
		  						"Image must be smaller than 10mb",
		  						Toast.LENGTH_LONG).show();
		            	  Arrays.fill(imageData, (byte)0);
		              }

	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	}
	public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
}
}
