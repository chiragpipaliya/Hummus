package com.hummus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends Activity {
	LinearLayout ll_back, ll_share;
	
	TextView t1;
	String share;
	WebView web_about;
	ImageView iv_about;
	Context context;
	String mimeType = "text/html";
	String encoding = "utf-8";
	String htmlText = "<p>One of the best parts of living abroad is getting to know the local kitchen and taste new flavors, however once in a while we all crave our traditional home dishes.</br></p>As an Israeli living abroad you're always being asked where can one find the BEST hummus in town.<br/></p>"
			+ "<p>Beyond hummus, we crave Israeli breakfast, falafel, burekas pastries, schnitzel, and typical ingredients and condiments you usually can't find in supermarkets.</br></p>I built this app trying to map the different places in LA where you can find Israeli food, ingredients, condiments and sometimes atmosphere..</br></p> Since I haven't tried all of them, the idea is for people to add their reviews, comments, ratings and photos to the different places.<br/></p>"
			+ "<p>If you know of a place that should be here and isn't, please send me your additions and suggestions.<br/></p>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);

		ll_back = (LinearLayout) findViewById(R.id.l1);
		ll_share = (LinearLayout) findViewById(R.id.ll_share);
		web_about = (WebView) findViewById(R.id.web_about);

		context = this;
		ll_share.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Share();
			}
		});
		ll_back.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutActivity.this,
						HomeActivity.class);
				startActivity(intent);

			}
		});
		// web_about.getSettings().setJavaScriptEnabled(true);
		// web_about.loadDataWithBaseURL("file:///android_asset/",
		// "<html><head>  <style> body{margin:0 7px;text-align:justify;} i</style></head><center><img src=file:///android_res/drawable/ic_about_us_logo.png/></center></html>"+htmlText,
		// "text/html", "utf-8", null);
		//
		web_about.loadData("<head><style>*{text-align:justify}</style></head>"
				+ htmlText, mimeType, encoding);
		// share=Html.fromHtml(htmlText).toString();
		// share=(String) t1.getText();

	}

	protected void Share() {
		// TODO Auto-generated method stub
		try {

			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			shareIntent.putExtra(Intent.EXTRA_SUBJECT,
					"Never go hummus-less again! The best app for LA's Israeli and Mediterranean food!");
			shareIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.hummus");
//			shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("https://play.google.com/store/apps/details?id=com.hummus"));
//			String shareurl="https://play.google.com/store/apps/details?id=com.hummus";
//			shareIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shareurl));
//			
			

			shareIntent.setType("*/*");
			startActivity(Intent.createChooser(shareIntent, "send"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(AboutActivity.this,
				HomeActivity.class);
		ActivityCompat.finishAffinity(this);
		startActivity(intent);
		
	}

	

}
