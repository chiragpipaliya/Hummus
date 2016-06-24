package com.hummus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.hummus.share.GPSTracker;
import com.hummus.share.NetworkManager;
import com.hummus.share.Share;


public class HomeActivity extends Activity {

	LinearLayout ll_list, ll_map, ll_search, ll_about, ll_rating, ll_add,
			ll_share;
	TextView tv_list, tv_map, tv_search, tv_about, tv_rating, tv_add, tv_share;
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	ImageView iv_logo;
	TextView t1;
	GPSTracker gpsTracker;
	NetworkManager networkManager;
	CustomDailog customDailog;
	private String share_text = "Check out Hummus+ app! https://play.google.com/store/apps/details?id=com.hummus&hl=en";
	CallbackManager callbackManager;
    com.facebook.share.widget.ShareDialog shareDialog;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		setContentView(R.layout.home_activity2);

		ll_list = (LinearLayout) findViewById(R.id.ll_list);
		ll_search = (LinearLayout) findViewById(R.id.ll_search);
		ll_share = (LinearLayout) findViewById(R.id.ll_share);
		ll_map = (LinearLayout) findViewById(R.id.ll_map);
		ll_about = (LinearLayout) findViewById(R.id.ll_about);
		// share=(Button)findViewById(R.id.sharebutton);
		ll_rating = (LinearLayout) findViewById(R.id.ll_rate);
		ll_add = (LinearLayout) findViewById(R.id.ll_add);
		iv_logo = (ImageView) findViewById(R.id.iv_logo);

		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_list = (TextView) findViewById(R.id.tv_list);
		tv_map = (TextView) findViewById(R.id.tv_map);
		tv_rating = (TextView) findViewById(R.id.tv_rate);
		tv_search = (TextView) findViewById(R.id.tv_search);
		tv_share = (TextView) findViewById(R.id.tv_share);
		networkManager = new NetworkManager();
		customDailog = new CustomDailog();
		gpsTracker = new GPSTracker(this);
		Share.slat = gpsTracker.getLatitude();
		Share.slong = gpsTracker.getLongitude();
		Share.screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		final String device_id = Secure.getString(
				HomeActivity.this.getContentResolver(), Secure.ANDROID_ID);
		Share.s_deviceid = device_id;
		
		 callbackManager = CallbackManager.Factory.create();
	        shareDialog = new com.facebook.share.widget.ShareDialog(this);
	        
	        PackageInfo pInfo;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				String version = pInfo.versionName;
				int verCode = pInfo.versionCode;
				Log.e("TAG","version name: "+version);
				Log.e("TAG","version code: "+verCode);
				
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		// iv_logo.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (NetworkManager.isInternetConnected(HomeActivity.this)) {
		// Intent intent = new Intent(HomeActivity.this,
		// AboutActivity.class);
		// startActivity(intent);
		// } else {
		// CustomDailog.showAlertDialog(HomeActivity.this,
		// "Network Connection", "Internet not available");
		// }
		// }
		// });
		
		
		Typeface font = Typeface.createFromAsset(this.getAssets(),
				"font/HTOWERTI.TTF");

		tv_add.setTypeface(font);
		tv_list.setTypeface(font);
		tv_map.setTypeface(font);
		tv_rating.setTypeface(font);
		tv_search.setTypeface(font);

		ll_list.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					Intent intent = new Intent(HomeActivity.this,
							ListActivity.class);
					startActivity(intent);
				} else {
					CustomDailog.showAlertDialog(HomeActivity.this,
							"Network Connection", "Internet not available");
				}
			}
		});

		ll_search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					Intent intent = new Intent(HomeActivity.this,
							SearchActivity.class);
					startActivity(intent);
				} else {
					CustomDailog.showAlertDialog(HomeActivity.this,
							"Network Connection", "Internet not available");
				}

			}
		});

		ll_map.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					Intent intent = new Intent(HomeActivity.this,
							MapActivity.class);
					startActivity(intent);
				} else {
					CustomDailog.showAlertDialog(HomeActivity.this,
							"Network Connection", "Internet not available");
				}

			}
		});
		ll_add.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					Intent intent = new Intent(HomeActivity.this,
							AddresturantActivity.class);
					startActivity(intent);
				} else {
					CustomDailog.showAlertDialog(HomeActivity.this,
							"Network Connection", "Internet not available");
				}
			}
		});

		ll_about.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					Intent intent = new Intent(HomeActivity.this,
							AboutActivity.class);
					startActivity(intent);
				} else {
					CustomDailog.showAlertDialog(HomeActivity.this,
							"Network Connection", "Internet not available");
				}
			}
		});

		ll_rating.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					Intent intent = new Intent(HomeActivity.this,
							RateActivity.class);
					startActivity(intent);
				} else {
					CustomDailog.showAlertDialog(HomeActivity.this,
							"Network Connection", "Internet not available");
				}

			}
		});
		ll_share.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					// Intent intent = new Intent(HomeActivity.this,
					// ShareActivity.class);
					// startActivity(intent);
					ShareDialog();
				} else {
					CustomDailog.showAlertDialog(HomeActivity.this,
							"Network Connection", "Internet not available");
				}
			}
		});
	}

	@SuppressLint("InflateParams")
	protected void ShareDialog() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(HomeActivity.this);
		LayoutInflater inflater = getLayoutInflater();

		final View view = inflater.inflate(R.layout.dialog_activity, null);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		Button btn_msg = (Button) view.findViewById(R.id.btn_message);
		Button btn_mail = (Button) view.findViewById(R.id.btn_email);
		Button btn_fb = (Button) view.findViewById(R.id.btn_facebook);
		Button btn_insta = (Button) view.findViewById(R.id.btn_instagram);
		Button btn_whatsapp = (Button) view.findViewById(R.id.btn_whatsapp);

		dialog.setContentView(view);

		btn_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("sms:"));
					intent.putExtra("sms_body", share_text);
					startActivity(intent);
			}
		});
		dialog.show();

		btn_mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("message/rfc822");
					intent.putExtra(Intent.EXTRA_SUBJECT, "Hummus+");
					intent.putExtra(Intent.EXTRA_TEXT, share_text);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			}
		});

		btn_fb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					if (com.facebook.share.widget.ShareDialog.canShow(ShareLinkContent.class)) {
						ShareLinkContent linkContent = new ShareLinkContent.Builder()
						.setContentTitle("Hummus")
						.setContentDescription(
								"I have been using Hummus App on my Android device and I like it.You should check it out.Just click the link below and it will redirect you to Google Playstore where you can download it")
								.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.hummus&hl=en"))
								.build();
						
						shareDialog.show(linkContent);
					}
				}
				
			}
		});

		btn_insta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// startNewActivity("com.instagram.android");
				Log.e("Home", "insta click");
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					try {
						
						PackageInfo packageInfo = getPackageManager()
								.getPackageInfo("com.instagram.android", 0);
						String getPackageName = packageInfo.toString();
						if (getPackageName.equals("com.instagram.android")) {
							// Toast.makeText(HomeActivity.this,
							// "Instagram App is installed on device!",
							// Toast.LENGTH_LONG).show();
							Log.e("Home", "insta installed");
							
						} else {
							Intent shareIntent = new Intent(Intent.ACTION_SEND);
							shareIntent.setType("image/*");
							Uri uri = Uri
									.parse("android.resource://com.hummus/drawable/bg_home_main");
							Log.e("Home", "URI: " + uri);
							shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
							shareIntent.setPackage("com.instagram.android");
							startActivity(shareIntent);
						}
						
					} catch (PackageManager.NameNotFoundException e) {
						Toast.makeText(HomeActivity.this,
								"Instagram App not found on device!",
								Toast.LENGTH_LONG).show();
						
					}
				}

			}
		});

		btn_whatsapp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NetworkManager.isInternetConnected(HomeActivity.this)) {
					startNewActivity("com.whatsapp", "Whatsapp");
				}
			}
		});

	}

	protected void startNewActivity(String packageName, String appname) {
		// TODO Auto-generated method stub
		Intent intent = HomeActivity.this.getPackageManager()
				.getLaunchIntentForPackage(packageName);
		if (intent != null) {
			// we found the activity
			// now start the activity
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.setPackage(packageName);
			intent.putExtra(Intent.EXTRA_TEXT, share_text);
			startActivity(intent);

		} else {
			
			Toast.makeText(HomeActivity.this,
					appname + " App not found on device!", Toast.LENGTH_LONG)
					.show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								HomeActivity.this.finish();
							}
						}).setNegativeButton("No", null).show();
	}
}