package edu.fau.group4.donateme;
import java.util.ArrayList;

import com.parse.ParseGeoPoint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;





public class RequestAdapter extends ArrayAdapter<RequestObject> {
	Context context;
    int layoutResourceId;
    RelativeLayout linearMain;
    ParseGeoPoint geo;
    ArrayList<RequestObject> data = new ArrayList<RequestObject>();
    
    public RequestAdapter(Context context, int layoutResourceId, ArrayList<RequestObject> data,ParseGeoPoint geo){
    	super(context, layoutResourceId, data);
    	this.layoutResourceId = layoutResourceId;
    	this.context = context;
    	this.data = data;
    	this.geo = geo;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	View row = convertView;
    	if (row == null) {
    		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
    		row = inflater.inflate(layoutResourceId,parent,false);
    		final RequestObject request = data.get(position);
    		final String requestString = request.requestType;
    		linearMain = (RelativeLayout) row.findViewById(R.id.linearMain);
    		linearMain.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(context,HelpOrg.class);
					Bundle b = new Bundle();
					b.putString("orgName",request.orgName);
					b.putString("whatFor", request.whatFor);
					b.putString("description", request.description);
					b.putString("website", request.website);
					b.putDouble("dlat", request.geo.getLatitude());
					b.putDouble("dlong", request.geo.getLongitude());
					b.putDouble("clat", geo.getLatitude());
					b.putDouble("clong", geo.getLongitude());
					b.putString("orgId", request.objectId);
					b.putString("orgType", request.orgType);
					b.putString("howToHelp", request.howToHelp);
					b.putString("requestType", request.requestType);
					b.putByteArray("imageData", request.imageArray);
					i.putExtras(b);
					context.startActivity(i);
					
				}
    		
				

			});
    		ImageView image1 = new ImageView(context);
    		image1.setId(1);
    		image1.setMaxHeight(100);
    		image1.setMaxWidth(100);
    		TextView label1 = new TextView(context);
    		label1.setId(2);
    		TextView label2 = new TextView(context);
    		label2.setId(3);
    		TextView label3 = new TextView(context);
    		label3.setId(4);
    		TextView label = new TextView(context);
    		label.setId(5);
    		TextView label4 = new TextView(context);
    		label4.setId(6);
    		TextView label5 = new TextView(context);
    		label5.setId(7);
    		
    		if(request.imageArray != null)
    		{
    		Bitmap imageBitmap = BitmapFactory.decodeByteArray(request.imageArray, 0, request.imageArray.length);
    		imageBitmap = Bitmap.createScaledBitmap(imageBitmap,300,300,true);
			image1.setImageBitmap(imageBitmap);			
    		}
    		linearMain.addView(image1);
    		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    		lp1.addRule(RelativeLayout.RIGHT_OF,1);
    		lp1.setMargins(30,0,0,0);
    		label1.setText("Organization Name: "+request.orgName);
    		linearMain.addView(label1,lp1);
    		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    		lp2.addRule(RelativeLayout.BELOW,2);
    		lp2.addRule(RelativeLayout.RIGHT_OF,1);
    		lp2.setMargins(30,0,0,0);
    		label2.setText("Organization Type: "+request.orgType);
    		linearMain.addView(label2,lp2);
    		RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    		lp3.addRule(RelativeLayout.BELOW,3);
    		lp3.addRule(RelativeLayout.RIGHT_OF,1);
    		lp3.setMargins(30,0,0,0);
    		label3.setText("Request Type: "+request.requestType);
    		linearMain.addView(label3,lp3);
    		RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    		if(request.requestType.equals("Money"))
    		{
    			label.setText("Request Amount: "+request.goal);
    			RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        		lp4.addRule(RelativeLayout.BELOW,4);
        		lp4.addRule(RelativeLayout.RIGHT_OF,1);
        		lp4.setMargins(30,0,0,0);
        		linearMain.addView(label,lp4);
        		
        		lp5.addRule(RelativeLayout.BELOW,5);
        		lp5.addRule(RelativeLayout.RIGHT_OF,1);
        		lp5.setMargins(30,0,0,0);
    		}
    		else
    		{
    			
        		lp5.addRule(RelativeLayout.BELOW,4);
        		lp5.addRule(RelativeLayout.RIGHT_OF,1);
        		lp5.setMargins(30,0,0,0);
    		}
    		    		
    		label4.setText("Description: "+request.description);
    		linearMain.addView(label4,lp5);
    		RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    		lp6.addRule(RelativeLayout.BELOW,6);
    		lp6.addRule(RelativeLayout.RIGHT_OF,1);
    		lp6.setMargins(30,0,0,0);
    		label5.setText("Distance: "+request.distance);
    		linearMain.addView(label5,lp6);
    	}
    	return row;
    }
}
