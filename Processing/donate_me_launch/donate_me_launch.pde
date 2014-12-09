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
int songSelect;
List<Float> filterDistance = new ArrayList<Float>();
List<String> filterType = new ArrayList<String>();
List<String> filterRequest;
List<String> filterGoal;
Bitmap image;

void setup()
{
 

  backgroundColor = "#00ff00";
  
  filterDistance.add(5.0f);
  filterDistance.add(10.0f);
  filterDistance.add(20.0f);
  filterDistance.add(50.0f);
  
  songSelect = 1; //out of 5
  
  filterType.add("reaserch");
  filterType.add("monetary");
  filterType.add("non-monetary");
  
  soundEnabled = true;
  songSelect = 2;
  
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
    i.putExtra("songSelect", songSelect);
    i.putExtra("filterDistance", filterDistance.toArray());
    i.putExtra("filterType", filterType.toArray());
    
    startActivity(i);
  }
  catch(Exception ex)
  {
    System.out.println("could not find package");
  }
}
