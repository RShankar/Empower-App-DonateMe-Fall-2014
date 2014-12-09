import android.os.Environment;
import android.content.Context;
import android.content.Intent;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//globals
String backgroundColor;
String headerFontColor;
String headerFontSize;
String headerFontStyle;
String labelFontColor;
String labelFontSize;
String labelFontStyle;
List<Float> filterDistance = new ArrayList<Float>();
List<String> filterType = new ArrayList<String>();
List<String> filterRequest;
List<String> filterGoal;

void setup()
{
  
  String filepath = "/sdcard/Image/ic_launcher.jpg";
  File imagefile = new File(filepath);

  backgroundColor = "#00ff00";
  
  filterDistance.add(5.0f);
  filterDistance.add(10.0f);
  filterDistance.add(20.0f);
  filterDistance.add(50.0f);
  
  filterType.add("reaserch");
  filterType.add("monetary");
  filterType.add("non-monetary");
  
  headerFontColor = "#ffffff";
  headerFontSize = "20sp";
  
  labelFontColor = "#000000";
  labelFontSize = "16sp";
    
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
    i.putExtra("filterDistance", filterDistance.toArray());
    i.putExtra("filterType", filterType.toArray());
    
    startActivity(i);
  }
  catch(Exception ex)
  {
    System.out.println("could not find package");
  }
}
