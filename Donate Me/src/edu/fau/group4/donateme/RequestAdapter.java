package edu.fau.group4.donateme;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;





public class RequestAdapter extends ArrayAdapter<RequestObject> {
	Context context;
    int layoutResourceId;
    LinearLayout linearMain;
    ArrayList<RequestObject> data = new ArrayList<RequestObject>();
    
    public RequestAdapter(Context context, int layoutResourceId, ArrayList<RequestObject> data){
    	super(context, layoutResourceId, data);
    	this.layoutResourceId = layoutResourceId;
    	this.context = context;
    	this.data = data;
    }
    String orgName;
	String orgType;
	String requestType;
	String whatFor;
	String description;
	String website;
	String goal;
	String objectId;
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	View row = convertView;
    	if (row == null) {
    		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
    		row = inflater.inflate(layoutResourceId,parent,false);
    		
    		linearMain = (LinearLayout) row.findViewById(R.id.linearMain);
    		RequestObject request = data.get(position);
    		TextView label1 = new TextView(context);
    		TextView label2 = new TextView(context);
    		TextView label3 = new TextView(context);
    		TextView label4 = new TextView(context);
    		
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
    		
    	}
    	return row;
    }
}
