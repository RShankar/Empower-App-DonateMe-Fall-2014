package edu.fau.group4.donateme;

import java.math.BigDecimal;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.View.OnClickListener;

public class Paypal extends Activity implements OnClickListener {
	  //** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	PayPal ppObj = PayPal.initWithAppID(this.getBaseContext(), "APP-80W284485P519543T", PayPal.ENV_NONE);

	CheckoutButton launchPayPalButton = ppObj.getCheckoutButton(this, PayPal.BUTTON_152x33, CheckoutButton.TEXT_PAY);
	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	params.addRule(RelativeLayout.CENTER_HORIZONTAL);

	launchPayPalButton.setLayoutParams(params);
	launchPayPalButton.setOnClickListener(this);

	((RelativeLayout) findViewById(R.id.button1)).addView(launchPayPalButton);
	    }

	@Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
	PayPalPayment newPayment = new PayPalPayment();
	char val[] = { '5', '0' };
	BigDecimal obj_0 = new BigDecimal(val);
	newPayment.setSubtotal(obj_0);
	newPayment.setCurrencyType("USD");
	newPayment.setRecipient("my@email.com");
	newPayment.setMerchantName("My Company");
	Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
	this.startActivityForResult(paypalIntent, 1);
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