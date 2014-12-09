import android.os.Environment;
import android.content.Context;
import android.content.Intent;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.net.Uri;

//globals
String backgroundColor;
String headerFontColor;
String headerFontSize;
String headerFontStyle;
String labelFontColor;
String labelFontSize;
String labelFontStyle;
String buttonFontColor;
String buttonFontSize;
String buttonFontStyle;
boolean soundEnabled;
List<Float> filterDistance = new ArrayList<Float>();
List<String> filterType = new ArrayList<String>();
List<String> filterRequest;
List<String> filterGoal;
Bitmap image;

void setup()
{
  
  /*String filepath = "/sdcard/Image/ic_launcher.jpg";
  File imagefile = new File(filepath);
  
  Uri contentUri = data.getData();          
  String[] proj = { MediaStore.Images.Media.DATA };         
  Cursor cursor = managedQuery(contentUri, proj, null, null, null);         
  int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);         
  cursor.moveToFirst();         
  String tmppath = cursor.getString(column_index);           
  image = BitmapFactory.decodeFile(tmppath);*/
  
  /*Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
  ByteArrayOutputStream stream = new ByteArrayOutputStream();
  bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
  byte[] byteArray = stream.toByteArray();*/

  backgroundColor = "#00ff00";
  
  filterDistance.add(5.0f);
  filterDistance.add(10.0f);
  filterDistance.add(20.0f);
  filterDistance.add(50.0f);
  
  filterType.add("reaserch");
  filterType.add("monetary");
  filterType.add("non-monetary");
  
  soundEnabled = false;
  
  headerFontSize = "70sp";
  labelFontSize = "30sp";
  buttonFontSize = "30sp";
    
  startActivity();
}


void startActivity()
{
  try
  {
    Intent i = getPackageManager().getLaunchIntentForPackage("edu.fau.group4.donateme");
    i.putExtra("backColor", backgroundColor);
    i.putExtra("headFontColor", headerFontColor);
    i.putExtra("headFontSize", headerFontSize);
    i.putExtra("headFontStyle", headerFontStyle);
    i.putExtra("labelFontColor", labelFontColor);
    i.putExtra("labelFontSize", labelFontSize);
    i.putExtra("labelFontStyle", labelFontStyle);
    i.putExtra("buttonFontColor", buttonFontColor);
    i.putExtra("buttonFontSize", buttonFontSize);
    i.putExtra("buttonFontStyle", buttonFontStyle);
    i.putExtra("soundEnabled", soundEnabled);
    i.putExtra("filterDistance", filterDistance.toArray());
    i.putExtra("filterType", filterType.toArray());
    
    startActivity(i);
  }
  catch(Exception ex)
  {
    System.out.println("could not find package");
  }
}
