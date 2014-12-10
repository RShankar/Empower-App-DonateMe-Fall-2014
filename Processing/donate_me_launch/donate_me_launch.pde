import android.os.Environment;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.net.Uri;

//globals
String backgroundColor;
String backgroundEndGradient;
String headerFontColor;
float headerFontSize;
String headerFontStyle;
String labelFontColor;
float labelFontSize;
String labelFontStyle;
String buttonFontColor;
float buttonFontSize;
String buttonFontStyle;
boolean soundEnabled;
int songSelect;
List<Float> filterDistance = new ArrayList<Float>();
List<String> filterType = new ArrayList<String>();
List<String> filterRequest = new ArrayList<String>();
List<String> filterGoal;
Bitmap image;

float[] array;
String[] filterTypeArray; 
String[] filterRequestArray;

void setup()
{
  backgroundColor = "#000000";
  backgroundEndGradient = "#777777";
  
  filterDistance.add(1.0f);
  filterDistance.add(5.0f);
  filterDistance.add(10.0f);
  filterDistance.add(15.0f);
  filterDistance.add(20.0f);
  filterDistance.add(50.0f);
  
  songSelect = 1; //out of 5
  
  filterType.add("reaserch");
  filterType.add("monetary");
  filterType.add("non-monetary");
  
  filterRequest.add("Money");
  filterRequest.add("Food");
  filterRequest.add("Clothes");
  filterRequest.add("Labor");
  
  soundEnabled = true;
  songSelect = 3;
  
  headerFontSize = 90.0f;
  labelFontSize = 30.0f;
  buttonFontSize = 30.0f;
    
  
  
  
  
  array = new float[filterDistance.size()];
  for(int i = 0; i < filterDistance.size(); i++)
  {
    array[i] = filterDistance.get(i);
  }
  
  filterTypeArray = new String[filterType.size()];
  for(int i = 0; i < filterType.size(); i++)
  {
    filterTypeArray[i] = filterType.get(i);
  }
  
  filterRequestArray = new String[filterRequest.size()];
  for(int i = 0; i < filterRequest.size(); i++)
  {
    filterRequestArray[i] = filterRequest.get(i);
  }
  
  startActivity();
}


void startActivity()
{
  //println(array.length);
  try
  {
    Intent i = getPackageManager().getLaunchIntentForPackage("edu.fau.group4.donateme");
    Bundle b = new Bundle();
    b.putString("backColor", backgroundColor);
    b.putString("endGradient", backgroundEndGradient);
    b.putString("headFontColor", headerFontColor);
    b.putFloat("headFontSize", headerFontSize);
    b.putString("headFontStyle", headerFontStyle);
    b.putString("labelFontColor", labelFontColor);
    b.putFloat("labelFontSize", labelFontSize);
    b.putString("labelFontStyle", labelFontStyle);
    b.putString("buttonFontColor", buttonFontColor);
    b.putFloat("buttonFontSize", buttonFontSize);
    b.putString("buttonFontStyle", buttonFontStyle);
    b.putBoolean("soundEnabled", soundEnabled);
    b.putInt("songSelect", songSelect);
    b.putFloatArray("filterDistance", array);
    b.putStringArray("filterType", filterTypeArray);
    b.putStringArray("filterRequest", filterRequestArray);
    i.putExtras(b);
    
    startActivity(i);
  }
  catch(Exception ex)
  {
    System.out.println("could not find package");
  }
}
