package edu.fau.group4.donateme;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
	private Bitmap bmp;
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;
	ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
	Bundle b;
	byte[] imageData;
	public void onCreate(Bundle savedInstanceState)
	  {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.addmedia);
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
		  avatar = (ImageView) findViewById(R.id.imageView1);
		  avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, SELECT_PICTURE);
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
	              if(imageData.length > 1000000)
	              {
	            	  Toast.makeText(getApplicationContext(),
	  						"Image must be smaller than 1mb",
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
	  	            if(imageData.length > 1000000)
		              {
		            	  Toast.makeText(getApplicationContext(),
		  						"Image must be smaller than 1mb",
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
