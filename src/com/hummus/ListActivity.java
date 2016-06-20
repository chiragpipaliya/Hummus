package com.hummus;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hummus.adapter.TabPagerAdapter;

@SuppressLint("NewApi")
public class ListActivity extends FragmentActivity{
	
	ViewPager Tab;
    TabPagerAdapter TabAdapter;
	ActionBar actionBar;
	InterstitialAd interstitial;
	ImageView search_iamge,iv_back;
	LinearLayout ll_search;
	ViewPager v_pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);
		v_pager=(ViewPager)findViewById(R.id.pager);
		
		AdView adView = (AdView) ListActivity.this.findViewById(R.id.adView);
		final String device_id = Secure.getString(
				ListActivity.this.getContentResolver(), Secure.ANDROID_ID);
		interstitial = new InterstitialAd(ListActivity.this);
		interstitial.setAdUnitId("ca-app-pub-9264938368122005/5058898575");
		
		com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(device_id)
				.build();
		adView.loadAd(adRequest);
		
//		AdSize customAdSize = new AdSize(getActivity().getWindowManager().getDefaultDisplay().getWidth(), adView.getHeight());
//		adView.setAdSize(AdSize.SMART_BANNER);
		
	
		
		interstitial.loadAd(adRequest);
		
		ViewGroup actionlayout=(ViewGroup)this.getLayoutInflater().inflate(R.layout.actionbar, null);
		iv_back=(ImageView)actionlayout.findViewById(R.id.backimage);
		actionBar=getActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setCustomView(actionlayout);
		if(android.os.Build.VERSION.SDK_INT<19){
			actionBar.setDisplayShowHomeEnabled(true);
			iv_back.setVisibility(View.GONE);
			actionBar.setIcon(R.drawable.ic_header_back);
		}
		else{
			iv_back.setVisibility(View.VISIBLE);
		}
		search_iamge=(ImageView)actionlayout.findViewById(R.id.image_search);
		ll_search=(LinearLayout)findViewById(R.id.ll_search);
	
		ll_search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ListActivity.this,SearchActivity.class);
				startActivity(intent);
			}
		});
		LinearLayout  l_back=(LinearLayout)actionlayout.findViewById(R.id.l1);
		l_back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				
			}
		});
		
		TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
		 Tab = (ViewPager)findViewById(R.id.pager);
		 Tab.setOnPageChangeListener(new OnPageChangeListener() {
			
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar = getActionBar();
            	actionBar.setSelectedNavigationItem(position);
            	Tab.getChildAt(0).setBackgroundResource(R.color.main);
			}
			
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		 
		    Tab.setAdapter(TabAdapter);
	        actionBar = getActionBar();
	        //Enable Tabs on Action Bar
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        ActionBar.TabListener tb=new TabListener() {
				
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
				}
				
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
				     Tab.setCurrentItem(tab.getPosition());
				}
				
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Tab selected", 2000).show();
				}
			};
			actionBar.addTab(actionBar.newTab().setText("By Name").setTabListener(tb));
			actionBar.addTab(actionBar.newTab().setText("By Distance").setTabListener(tb));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		  Intent mainActivity = new Intent(ListActivity.this,HomeActivity.class);
		  ActivityCompat.finishAffinity(this);
		     startActivity(mainActivity);
	}

}
