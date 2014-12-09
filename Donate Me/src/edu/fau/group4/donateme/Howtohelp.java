package edu.fau.group4.donateme;

import java.util.List;

import java.math.BigDecimal;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalPayment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;

import edu.fau.group4.donateme.MusicService.LocalBinder;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.DialogInterface;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Howtohelp extends Activity implements OnClickListener
{
	private GoogleMap googleMap;
	double clat;
	double clong;
	double longitude;
	double latitude;
	Boolean gpsProvided;
	String name;
	String howtohelptxt;
	Bundle b;
	CheckoutButton launchPayPalButton;
	EditText paypal;
	double paypalAmount;
	String paypalEmail;
	ImageView paymentimageview;
	private ProgressDialog _progressDialog;
	private boolean _paypalLibraryInit = false;
	private boolean _progressDialogRunning = false;
	final static public int PAYPAL_BUTTON_ID = 10001;
	private MusicService mp3Service;
	private ServiceConnection mp3PlayerServiceConnection = new ServiceConnection() {
	        
	        public void onServiceConnected(ComponentName arg0, IBinder service) {
	            LocalBinder binder = (LocalBinder) service;
	        	mp3Service = binder.getService();
	        	mp3Service.playSong(getBaseContext());
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
	
	    public boolean isOnline() {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			}
			return false;
		}
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
	    View v = this.getWindow().getDecorView();
	    v.setBackgroundColor(GlobalLayout.backgroundColor);
	    Intent connectionIntent = new Intent(this, MusicService.class);
        bindService(connectionIntent, mp3PlayerServiceConnection ,Context.BIND_AUTO_CREATE);
	    
		 b = getIntent().getExtras();
		    boolean isMonetary = b.getBoolean("isMonetary");
		    gpsProvided = b.getBoolean("gpsProvided");
			longitude = b.getDouble("longitude");
			latitude = b.getDouble("latitude");
			name = b.getString("name");
			clat = b.getDouble("currentLat");
			clong = b.getDouble("currentLong");		
			howtohelptxt = b.getString("howToHelp");
			paypalEmail = b.getString("paypalEmail");
	    if(isMonetary)
	    {
	    	setContentView(R.layout.howtohelp);
	    	paymentimageview = (ImageView) findViewById(R.id.muteimageview);
	    	if (this.isOnline()) {
				Thread libraryInitializationThread = new Thread() {
					public void run() {
						initLibrary();
					}
				};

				libraryInitializationThread.start();
			}
	    	paypal = (EditText) findViewById(R.id.amount);
	    	
	    	if (_paypalLibraryInit) {
				showPayPalButton();
			} else {
				// Display a progress dialog to the user and start checking for when
				// the initialization is completed
				_progressDialog = new ProgressDialog(this);
				_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				_progressDialog.setMessage("Loading PayPal Payment Library");
				_progressDialog.setCancelable(false);
				_progressDialog.show();
				_progressDialogRunning = true;
				Thread newThread = new Thread(checkforPayPalInitRunnable);
				newThread.start();
			}
	    }
	    else
	    {
	    
	    	setContentView(R.layout.non_monetary);
	    	TextView welcome = (TextView) findViewById(R.id.welcometxtview);
	    	welcome.setText(howtohelptxt);
	    	
	    	if(gpsProvided)
	    	{
		    	LatLng ll = new LatLng(latitude,longitude);		    	
		    	drawMap(ll,name);
	    	}
	    }
    }
   
    public void initLibrary() {
		PayPal pp = PayPal.getInstance();
		// If the library is already initialized, then we don't need to
		// initialize it again.
		if (pp == null) {
			// This is the main initialization call that takes in your Context,
			// the Application ID, and the server you would like to connect to.
			
			pp = PayPal.initWithAppID(this, "APP-80W284485P519543T",
					PayPal.ENV_NONE);

			// -- These are required settings.
			pp.setLanguage("en_US"); // Sets the language for the library.
			// --

			// -- These are a few of the optional settings.
			// Sets the fees payer. If there are fees for the transaction, this
			// person will pay for them. Possible values are FEEPAYER_SENDER,
			// FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and
			// FEEPAYER_SECONDARYONLY.
			pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
			// Set to true if the transaction will require shipping.
			pp.setShippingEnabled(true);
			// Dynamic Amount Calculation allows you to set tax and shipping
			// amounts based on the user's shipping address. Shipping must be
			// enabled for Dynamic Amount Calculation. This also requires you to
			// create a class that implements PaymentAdjuster and Serializable.
			pp.setDynamicAmountCalculationEnabled(false);
			// --
			_paypalLibraryInit = true;
		}
	}
    
    
    final Runnable checkforPayPalInitRunnable = new Runnable() {
		public void run() {
			checkForPayPalLibraryInit();
		}
	};

	// This method is called if the Review page is being loaded but the PayPal
	// Library is not
	// initialized yet.
	private void checkForPayPalLibraryInit() {
		// Loop as long as the library is not initialized
		
		while (_paypalLibraryInit == false) {
			try {
				// wait 1/2 a second then check again
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// Show an error to the user
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Error initializing PayPal Library")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// Could do anything here to handle the
										// error
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		// If we got here, it means the library is initialized.
		// So, add the "Pay with PayPal" button to the screen
		runOnUiThread(showPayPalButtonRunnable);
	}

	final Runnable showPayPalButtonRunnable = new Runnable() {
		public void run() {
			showPayPalButton();
		}
	};
//	  The initLibrary function takes care of all the basic Library
//	  initialization.
//	  
//	  @return The return will be true if the initialization was successful and
//	          false if

    
    
    public void PayPalActivityResult(int requestCode, int resultCode, Intent intent) {
    	switch (resultCode) {
    	// The payment succeeded
    	case Activity.RESULT_OK:
    	String payKey = intent.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
    	this.paymentSucceeded(payKey);
    	break;

    	// The payment was canceled
    	case Activity.RESULT_CANCELED:
    	this.paymentCanceled();
    	break;

    	// The payment failed, get the error from the EXTRA_ERROR_ID and EXTRA_ERROR_MESSAGE
    	case PayPalActivity.RESULT_FAILURE:
    	String errorID = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
    	String errorMessage = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
    	this.paymentFailed(errorID, errorMessage);
    	}
    	}

	public void paymentFailed(String errorID, String errorMessage) {
		// We could let the user know the payment failed here
		loadResultsPage();
		((TextView) findViewById(R.id.welcometxtview)).setText("Failure!");
		((TextView) findViewById(R.id.lasttxtview))
				.setText("We're sorry, but your payment failed.\r\nError: "+errorMessage+"\r\nError ID: "+errorID);
	}

	public void paymentCanceled() {
		// We could tell the user that the payment was canceled
		loadResultsPage();
		((TextView) findViewById(R.id.welcometxtview)).setText("Canceled.");
		((TextView) findViewById(R.id.lasttxtview))
				.setText("Your payment has been canceled.");
		
	}
	
	public void loadResultsPage() {
		setContentView(R.layout.thanks);

	}

	public void paymentSucceeded(String payKey) {
		// We could show the transactionID to the user
		loadResultsPage();
		
		((TextView) findViewById(R.id.welcometxtview)).setText("Success!");
		
			((TextView) findViewById(R.id.lasttxtview))
					.setText("Thanks for your donation!");
	}
    private void showPayPalButton() {

    	// Generate the PayPal checkout button and save it for later use
    	removePayPalButton();
    	PayPal pp = PayPal.getInstance();
    	launchPayPalButton = pp.getCheckoutButton(this, PayPal.BUTTON_278x43, CheckoutButton.TEXT_PAY);

    	// The OnClick listener for the checkout button
    	launchPayPalButton.setOnClickListener(this);

    	// Add the listener to the layout
    	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (LayoutParams.WRAP_CONTENT,
    	LayoutParams.WRAP_CONTENT);
    	params.addRule(RelativeLayout.CENTER_HORIZONTAL);
    	params.addRule(RelativeLayout.CENTER_VERTICAL);
    	params.addRule(RelativeLayout.ABOVE,paymentimageview.getId());
    	params.bottomMargin = 50;
    	launchPayPalButton.setLayoutParams(params);
    	launchPayPalButton.setId(PAYPAL_BUTTON_ID);
    	((RelativeLayout) findViewById(R.id.mRlayout1)).addView(launchPayPalButton);
    	((RelativeLayout) findViewById(R.id.mRlayout1)).setGravity(Gravity.CENTER_HORIZONTAL);
    	if (_progressDialogRunning) {
			_progressDialog.dismiss();
			_progressDialogRunning = false;
		}
    	}
    private void removePayPalButton() {
		// Avoid an exception for setting a parent more than once
		if (launchPayPalButton != null) {
			((RelativeLayout) findViewById(R.id.mRlayout1))
					.removeView(launchPayPalButton);
		}
	}
    
    
    public void PayPalButtonClick(View arg0) {
    	// Create a basic PayPal payment
    	paypalAmount = Double.parseDouble(paypal.getText().toString());
    	if(paypalAmount > 0)
    	{
    	PayPalPayment payment = new PayPalPayment();

    	// Set the currency type
    	payment.setCurrencyType("USD");

    	// Set the recipient for the payment (can be a phone number)
    	payment.setRecipient(paypalEmail);

    	// Set the payment amount, excluding tax and shipping costs
    	payment.setSubtotal(new BigDecimal(paypalAmount));

    	// Set the payment type--his can be PAYMENT_TYPE_GOODS,
    	// PAYMENT_TYPE_SERVICE, PAYMENT_TYPE_PERSONAL, or PAYMENT_TYPE_NONE
    	payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);

    	// PayPalInvoiceData can contain tax and shipping amounts, and an
    	// ArrayList of PayPalInvoiceItem that you can fill out.
    	// These are not required for any transaction.
    	PayPalInvoiceData invoice = new PayPalInvoiceData();
    	// Set the tax amount
    	invoice.setTax(new BigDecimal(0));
    	}
    	else
    	{
    		Toast.makeText(Howtohelp.this,
					"Please enter a valid amount", Toast.LENGTH_LONG)
					.show();
    	}
    	}
	public void onBack(View v) 
	{

		finish();
		
	}
	
	public void onLogout(View v) 
	{
		Intent i = new Intent(v.getContext(), Welcomepage.class);	         
		startActivity(i);
	}
	
	public void onDonate(View v) 
	{
		Intent i = new Intent(v.getContext(), Thanks.class);	         
		startActivity(i);
	}
	
	private void drawMap(LatLng loc, String name)
	{
		
		if (googleMap == null) 
		{
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			// check if map is created successfully or not
			if (googleMap == null) 
			{
				Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
			}
			else
			{
				googleMap.setOnCameraChangeListener(new OnCameraChangeListener() 
				{
			        @Override
			        public void onCameraChange(CameraPosition cameraPosition)
			        {
			            //update map here
			        }
			    });
								
				LatLng gps = new LatLng(clat,clong);
				float distance = (float) getDistance(gps, loc);
								
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13.8f));
				googleMap.addMarker(new MarkerOptions()
		        	.position(loc)
		        	.title(name + "\nDistance: " + String.format("%.2f", distance) + " km"));
				
				//(getDistance(gps, loc))
				
				//30 km, 10.5
				//10.1 km, 12.1
				//3.14 km, 13.8
				
			}
		}
	}
	
	
	//gets distance between 2 GPS coordinates
	double getDistance(LatLng gps1, LatLng gps2)
	{
		double R = 6371; // radius of Earth (km)
		double x1 = gps1.latitude * Math.PI/180.0;
		double x2 = gps2.latitude * Math.PI/180.0;
		double y1 = gps1.longitude * Math.PI/180.0;
		double y2 = gps2.longitude * Math.PI/180.0;
		double z1 = x2-x1;
		double z2 = y2-y1;

		double a = Math.sin(z1/2) * Math.sin(z1/2) + Math.cos(x1) * Math.cos(x2) * Math.sin(z2/2) * Math.sin(z2/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return R * c;
	}
	
	private LatLng getLocation() 
	{
	    // Get the location manager
		double lat,lon;
	    LocationManager locationManager = (LocationManager) 
	            getSystemService(LOCATION_SERVICE);
	    Criteria criteria = new Criteria();
	    String bestProvider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(bestProvider);
	    try 
	    {
	        lat = location.getLatitude();
	        lon = location.getLongitude();
	    } catch (Exception e) 
	    {
	        lat = 0;
	        lon = 0;
	    }
	    return new LatLng(lat,lon);
	}
	
	private LatLng getGPS() 
	{
		 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		 List<String> providers = lm.getProviders(true);

		 Location l = null;

		 for (int i = providers.size()-1; i >= 0; i--) 
		 {
			 l = lm.getLastKnownLocation(providers.get(i));
			 if (l != null) break;
		 }

		 if (l != null) 
		 {
			 return new LatLng(l.getLatitude(), l.getLongitude());
		 }
		 else
		 {
			 return new LatLng(0,0);
		 }	 
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		PayPalPayment newPayment = new PayPalPayment();
		EditText et = (EditText) findViewById(R.id.amount);
		
		float val;
		try
		{
			val = Float.parseFloat(et.getText().toString());
			BigDecimal obj_0 = new BigDecimal(val);
			newPayment.setSubtotal(obj_0);
			newPayment.setCurrencyType("USD");
			newPayment.setRecipient("my@email.com");
			newPayment.setMerchantName(name);
			Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
			this.startActivityForResult(paypalIntent, 1);
		}
		catch(Exception e)
		{
			
			Toast.makeText(getBaseContext(), "you need to enter a value", Toast.LENGTH_SHORT).show();
		}
	
	}

	@SuppressWarnings("unused")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	switch (resultCode) {
	case Activity.RESULT_OK:
	// The payment succeeded
	String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
	
	// Tell the user their payment succeeded
	break;
	case Activity.RESULT_CANCELED:
	// The payment was canceled
	// Tell the user their payment was canceled
	break;
	case PayPalActivity.RESULT_FAILURE:
	// The payment failed -- we get the error from the EXTRA_ERROR_ID
	// and EXTRA_ERROR_MESSAGE
	String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
	String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
	// Tell the user their payment was failed.
	               break;
	       }
	   }

	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
}

