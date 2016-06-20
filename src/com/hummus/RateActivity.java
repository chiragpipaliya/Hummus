package com.hummus;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class RateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent=new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://details?id=com.hummus"));
		try {
			startActivity(intent);
			
		} catch (ActivityNotFoundException e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "please install the market app.", Toast.LENGTH_LONG).show();
			
			Intent intent2=new Intent(RateActivity.this,HomeActivity.class);
			startActivity(intent2);
			
		}
	
	}
	public void onBackPressed() {
	  
		Intent mainActivity = new Intent(RateActivity.this,HomeActivity.class);
		ActivityCompat.finishAffinity(this);
	     startActivity(mainActivity);
	}

}
