package com.hummus;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.os.Bundle;

public class AdmobActivity extends Activity {
	private InterstitialAd interstitial;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admob_activity);
		
		interstitial = new InterstitialAd(AdmobActivity.this);
		
		interstitial.setAdUnitId("ca-app-pub-9264938368122005/1974444974");
		
		AdView adView = (AdView) this.findViewById(R.id.adView);
		
		com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().addTestDevice("3B89A4BF90C2A0646F09A614E4ED0A2F")
		   
		
				.build();
		
//		adView.loadAd(adRequest);
		
		interstitial.loadAd(adRequest);
		
		
		interstitial.setAdListener(new AdListener() {
			
			public void onAdLoaded() {
				// Call displayInterstitial() function
				displayInterstitial();
			}
		});
		
		
	}

	protected void displayInterstitial() {
		// TODO Auto-generated method stub
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}


		
	

	
	
}
