package edu.fau.group4.donateme;
import java.util.ArrayList;

import com.parse.ParseGeoPoint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;





public class RequestAdapter extends ArrayAdapter<RequestObject> {
	Context context;
    int layoutResourceId;
    LinearLayout linearMain;
    RequestObject request;
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
    		request = data.get(position);
    		final String requestString = request.requestType;
    		linearMain = (LinearLayout) row.findViewById(R.id.linearMain);
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
					i.putExtras(b);
					context.startActivity(i);
					
				}
    		
				

			});
    		
    		TextView label1 = new TextView(context);
    		TextView label2 = new TextView(context);
    		TextView label3 = new TextView(context);
    		TextView label4 = new TextView(context);
    		TextView label5 = new TextView(context);
    		
    		label1.setText("Organization Name: "+request.orgName);
    		linearMain.addView(label1);
    		label2.setText("Organization Type: "+request.orgType);
    		linearMain.addView(label2);
    		label3.setText("Request Type: "+request.requestType);
    		linearMain.addView(label3);
    		if(request.requestType.equals("Money"))
    		{
    			TextView label = new TextView(context);
    			label.setText("Request Amount: "+request.goal);
        		linearMain.addView(label);
    		}
    		label4.setText("Description: "+request.description);
    		linearMain.addView(label4);
    		label5.setText("Distance: "+request.distance);
    		linearMain.addView(label5);
    	}
    	return row;
    }
}
