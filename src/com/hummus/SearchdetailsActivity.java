package com.hummus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hummus.ListdetailsActivity.JSONrate;
import com.hummus.MapdetailsActivity.JSONADDImage;
import com.hummus.MapdetailsActivity.JSONADDImage1;
import com.hummus.model.ListModel;
import com.hummus.share.NetworkManager;
import com.hummus.share.Share;
import com.imageloader.ImageLoader;
import com.webservices.Webservice;

public class SearchdetailsActivity extends FragmentActivity implements
		OnClickListener {

	LinearLayout ll_back, ll2, ll_call, ll_visit, ll_direc, ll_share, ll_rate;
	TextView tv_address, tv_desc, tv_rating, tv_restitle, tv_showreview;
	ImageLoader imageLoader;
	ImageView iv_icon;
	GoogleMap googlemap;
	WindowManager windowManager;
	HorizontalScrollView scrollView;
	Float ratingliststar;
	RatingBar ratebar;

	ScrollView list_scroll;
	ListModel lm = new ListModel();
	int height;
	int width;
	Webservice webservice;
	EditText et_review;
	Button b_addphoto;
	private static int RESULT_LOAD_IMG = 1;
	String picturePath, review;
	String date;
	String imageUri;
	private Uri fileUri;
	private static File mediaFile;

	CustomDailog customDailog;
	com.facebook.share.widget.ShareDialog shareDialog;
	CallbackManager callbackManager;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchdetails_activity);
		ll_back = (LinearLayout) findViewById(R.id.l1);
		ll2 = (LinearLayout) findViewById(R.id.linear);
		ll_call = (LinearLayout) findViewById(R.id.l_call);
		ll_visit = (LinearLayout) findViewById(R.id.l_visit);
		ll_direc = (LinearLayout) findViewById(R.id.l_getd);
		ll_share = (LinearLayout) findViewById(R.id.ll_share);
		ll_rate = (LinearLayout) findViewById(R.id.ll_rate);
		ratebar = (RatingBar) findViewById(R.id.rb_rating);
		et_review = (EditText) findViewById(R.id.re_edittext);
		b_addphoto = (Button) findViewById(R.id.photo_butoon);
		list_scroll = (ScrollView) findViewById(R.id.list_scroll);
		imageLoader = new ImageLoader(getApplicationContext());

		tv_address = (TextView) findViewById(R.id.list_address);
		tv_desc = (TextView) findViewById(R.id.list_des1);
		tv_rating = (TextView) findViewById(R.id.list_rating);
		tv_showreview = (TextView) findViewById(R.id.tv_review);
		tv_restitle = (TextView) findViewById(R.id.tv_maintitle);
		iv_icon = (ImageView) findViewById(R.id.list_icon);

		callbackManager = CallbackManager.Factory.create();
		shareDialog = new com.facebook.share.widget.ShareDialog(this);

		windowManager = (WindowManager) SearchdetailsActivity.this
				.getSystemService(Context.WINDOW_SERVICE);
		width = windowManager.getDefaultDisplay().getWidth();
		height = windowManager.getDefaultDisplay().getHeight();
		customDailog = new CustomDailog();
		iv_icon.getLayoutParams().width = (int) (width * 0.2);
		iv_icon.getLayoutParams().height = (int) (height * 0.2);
		ll_rate.getLayoutParams().height = (int) (height * 0.1);
		date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		ll_call.setOnClickListener(this);
		ll_visit.setOnClickListener(this);
		ll_direc.setOnClickListener(this);
		ll_share.setOnClickListener(this);
		b_addphoto.setOnClickListener(this);
		tv_showreview.setOnClickListener(this);

		et_review.setOnEditorActionListener(new OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					review = et_review.getText().toString();
					if (review.equals("")) {
						CustomDailog.showAlertDialog(
								SearchdetailsActivity.this, "Message",
								"Please add review");

					} else {
						if (NetworkManager.isInternetConnected(SearchdetailsActivity.this)) {
							
							new JSONADDreview().execute();
						}
					}
				}
				return false;

			}

		});

		ll_back.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();

			}
		});

		tv_address.setText(Share.s_addre);
		SpannableString ss = new SpannableString(Share.s_des.replace(",", ","));
		ss.setSpan("", 0, ss.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		// tv_desc.append("");
		tv_desc.append(ss);
		tv_restitle.setText(Share.s_name);
		imageLoader.clearCache();
		imageLoader.DisplayImage(Share.s_imagefinal, iv_icon);
		// tv_rating.setText(Double.toString(Share.sff_rating));
		ratebar.getLayoutParams().height = (int) (Share.screenWidth * 0.2);
		// ratebar.setRating(Share.sff_rating);

		addGoogleMap();
		if (NetworkManager.isInternetConnected(SearchdetailsActivity.this)) {
			new Jsontask().execute();
			new JSONrate().execute();
		}
		// new Jsonratecheck().execute();

		ratebar.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ShowDialog();
				return false;
			}
		});
	}

	private void ShowDialog() {
		Log.e("TAG", "Search rate dialog");
		final Dialog dialog = new Dialog(SearchdetailsActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		final View ratingView = inflater.inflate(R.layout.row_rating, null);
		Button b_submitrate = (Button) ratingView
				.findViewById(R.id.b_rate_submit);
		Button b_cancelrate = (Button) ratingView
				.findViewById(R.id.b_rate_cancel);
		dialog.setContentView(ratingView);
		final RatingBar r = (RatingBar) ratingView
				.findViewById(R.id.rb_rating1);

		r.getLayoutParams().height = (int) (Share.screenWidth * 0.3);
		r.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				Share.ss_rate = rating;
			}
		});

		b_submitrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (r.getRating() != 0) {
					if (NetworkManager
							.isInternetConnected(SearchdetailsActivity.this)) {
						new JSONrate().execute();
					}
				} else {
					CustomDailog customDailog = new CustomDailog();
					customDailog.showAlertDialog(SearchdetailsActivity.this,
							"Message", "Please give rating");
				}
			}
		});

		b_cancelrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	@SuppressWarnings("deprecation")
	private void addGoogleMap() {
		// TODO Auto-generated method stub
		if (googlemap == null) {

			googlemap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			windowManager = (WindowManager) SearchdetailsActivity.this
					.getSystemService(Context.WINDOW_SERVICE);
			width = windowManager.getDefaultDisplay().getWidth();
			height = windowManager.getDefaultDisplay().getHeight();
			findViewById(R.id.map).getLayoutParams().height = (int) (height * 0.3);
			list_scroll.requestDisallowInterceptTouchEvent(true);
			MarkerOptions mo = new MarkerOptions();
			Double lat = Double.parseDouble(Share.s_lat);
			Double lng = Double.parseDouble(Share.s_long);
			LatLng ltlg = new LatLng(lat, lng);
			mo.position(ltlg);
			mo.title(Share.s_name);
			mo.snippet(Share.s_addre);
			mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin));
			CameraPosition cp = new CameraPosition.Builder().target(ltlg)
					.zoom(12).build();
			googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
			googlemap.addMarker(mo);

		}

	}

	public class Jsontask extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchdetailsActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				response = Webservice
						.get_response("http://israelinla.com/hummus/api/app.php?action=event_list");
				Log.e("tag", "msg response " + response);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			try {
				JSONObject data = new JSONObject(response);
				JSONArray name = data.getJSONArray("event_list");

				for (int index = 0; index < name.length(); index++) {

					JSONObject row = name.getJSONObject(index);
					if (row.getString("event_id").equals(Share.s_id)) {
						JSONArray iarry = row.getJSONArray("event_images");
						for (int index1 = 0; index1 < iarry.length(); index1++) {
							JSONObject row1 = iarry.getJSONObject(index1);

							String photo = row1.getString("sub_image");
							Log.e("tag", "photo" + photo);

							if (photo.equals("")) {

							} else {
								ImageView im = new ImageView(
										SearchdetailsActivity.this);
								im.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										LayoutParams.WRAP_CONTENT));
								im.setScaleType(ScaleType.FIT_XY);
								im.setPadding(2, 2, 2, 2);
								im.getLayoutParams().width = (int) (Share.screenWidth * 0.25);
								im.getLayoutParams().height = (int) (Share.screenWidth * 0.25);
								imageLoader = new ImageLoader(
										SearchdetailsActivity.this);
								imageLoader.DisplayImage(photo, im);
								ll2.addView(im);
							}
						}
					}
				}

				// Collections.sort(name_list,new ListnameAdapter(getActivity(),
				// name_list));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Toast.makeText(SearchdetailsActivity.this, "error",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public class JSONrate extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchdetailsActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("rat", String.valueOf(Share.sf_rate));
			param.put("event_id", String.valueOf(Share.s_id));
			param.put("device_id", String.valueOf(Share.s_deviceid));
			response = Webservice
					.Send_data(
							param,
							"http://israelinla.com/hummus/api/app.php?action=rating&event_id=1&device_id=2563985214&rat=4");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			try {
				JSONObject data = new JSONObject(response);
				if (data.getString("success").equals("1")) {
					float main_rate = data.getLong("rat");
					Double avg_rate = data.getDouble("rating");
					Log.e("TAG", "avg_rating" + avg_rate);
					// CustomDailog.showAlertDialog(ListdetailsActivity.this,
					// "Message", "Your rating saved");
					// Share.sff_rating=Float.parseFloat(avg_rate.toString());
					Log.e("TAG", "Share.sff_rating" + Share.sff_rating);
					tv_rating.setText(Double.toString(avg_rate));
					ratebar.setRating(main_rate);

				}

				if (data.getString("success").equals("0")) {
					customDailog.showAlertDialog(SearchdetailsActivity.this,
							"Message", "Alrady rating");
					Intent intent = new Intent(SearchdetailsActivity.this,
							SearchdetailsActivity.class);
					startActivity(intent);
					// ((DialogInterface) alertDialog).dismiss();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == ll_call) {

			Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ Share.s_telephone));
			startActivity(callIntent);

		}
		if (v == ll_visit) {
			Uri uri = Uri.parse(Share.s_website);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
		if (v == ll_direc) {

			String url = "http://maps.google.com/maps?saddr=" + Share.slat
					+ "," + Share.slong + "&daddr=" + Share.s_lat + ","
					+ Share.s_long + "";
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse(url));
			startActivity(intent);
		}
		if (v == b_addphoto) {
			// Intent intent=new
			// Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			// startActivityForResult(intent, RESULT_LOAD_IMG);
			SelectImage();

		}
		if (v == ll_share) {
			listshare();
		}
		if (v == tv_showreview) {
			Intent intent = new Intent(SearchdetailsActivity.this,
					SearchShowreviewActivity.class);
			startActivity(intent);
		}
	}

	private void SelectImage() {
		// TODO Auto-generated method stub
		final CharSequence[] options = { "Camera", "Photo gallery", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SearchdetailsActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {
				// TODO Auto-generated method stub
				if (options[item].equals("Camera")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					fileUri = getOutputMediaFileUri(); // set the image file
														// name
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
					startActivityForResult(intent, 1);

				} else if (options[item].equals("Photo gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}

		});
		builder.show();
	}

	protected Uri getOutputMediaFileUri() {
		// TODO Auto-generated method stub
		return Uri.fromFile(getOutputMediaFile());
	}

	private File getOutputMediaFile() {
		// TODO Auto-generated method stub
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Hummus");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("Hummus", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");
		Log.e("Media File------------------------", "" + mediaFile + "----"
				+ Uri.fromFile(mediaFile));
		return mediaFile;
	}

	private void listshare() {
		// TODO Auto-generated method stub
		// try{
		//
		// // Uri uri =
		// Uri.parse("android.resource://com.hummus/"+R.drawable.ic_home_logo);
		// Intent shareIntent = new Intent(Intent.ACTION_SEND);
		// shareIntent.setAction(Intent.ACTION_SEND);
		// shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		// shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Check out this listing from Hummus+ app!"
		// );
		// shareIntent.putExtra(Intent.EXTRA_TEXT, "Name:"+Share.s_name + "\n"
		// +"Address:"+Share.s_addre + "\n" + "Telephone:"+Share.s_telephone );
		// // shareIntent.putExtra(Intent.EXTRA_TEXT, Share.s_addre);
		// // shareIntent.putExtra(Intent.EXTRA_TEXT, Share.s_telephone);
		// shareIntent.setType("*/*");
		// startActivity(Intent.createChooser(shareIntent, "send"));
		//
		// }
		// catch(Exception e){
		// e.printStackTrace();
		// }

		if (NetworkManager.isInternetConnected(SearchdetailsActivity.this)) {
			// Intent intent = new Intent(HomeActivity.this,
			// ShareActivity.class);
			// startActivity(intent);
			ShareDialog();
		} else {
			CustomDailog.showAlertDialog(SearchdetailsActivity.this,
					"Network Connection", "Internet not available");
		}
	}

	private void ShareDialog() {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(SearchdetailsActivity.this);
		LayoutInflater inflater = getLayoutInflater();

		final View view = inflater.inflate(R.layout.dialog_activity, null);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		Button btn_msg = (Button) view.findViewById(R.id.btn_message);
		Button btn_mail = (Button) view.findViewById(R.id.btn_email);
		Button btn_fb = (Button) view.findViewById(R.id.btn_facebook);
		Button btn_insta = (Button) view.findViewById(R.id.btn_instagram);
		Button btn_whatsapp = (Button) view.findViewById(R.id.btn_whatsapp);

		dialog.setContentView(view);

		dialog.show();

		btn_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NetworkManager
						.isInternetConnected(SearchdetailsActivity.this)) {

					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("sms:"));
					intent.putExtra("sms_body",
							"Check out this listing from Hummus+ app!" + "\n\n"
									+ "Name:" + Share.s_name + "\n"
									+ "Address:" + Share.s_addre + "\n"
									+ "Telephone:" + Share.s_telephone);
					startActivity(intent);
				}
			}
		});
		dialog.show();

		btn_mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NetworkManager
						.isInternetConnected(SearchdetailsActivity.this)) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("message/rfc822");
					intent.putExtra(Intent.EXTRA_SUBJECT,
							"Check out this listing from Hummus+ app!");
					intent.putExtra(Intent.EXTRA_TEXT, "Name:" + Share.s_name
							+ "\n" + "Address:" + Share.s_addre + "\n"
							+ "Telephone:" + Share.s_telephone);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			}
		});

		btn_fb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (NetworkManager
						.isInternetConnected(SearchdetailsActivity.this)) {

					if (com.facebook.share.widget.ShareDialog
							.canShow(ShareLinkContent.class)) {
						ShareLinkContent linkContent = new ShareLinkContent.Builder()
								.setContentTitle(
										"Check out this listing from Hummus+ app!")
								.setContentDescription(
										"Name:" + Share.s_name + "\n"
												+ "Address:" + Share.s_addre
												+ "\n" + "Telephone:"
												+ Share.s_telephone)
								.setContentUrl(
										Uri.parse("https://play.google.com/store/apps/details?id=com.hummus&hl=en"))
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
				if (NetworkManager
						.isInternetConnected(SearchdetailsActivity.this)) {
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
						Toast.makeText(SearchdetailsActivity.this,
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
				if (NetworkManager
						.isInternetConnected(SearchdetailsActivity.this)) {
					startNewActivity("com.whatsapp", "Whatsapp");
				}
			}
		});

	}

	protected void startNewActivity(String packageName, String appname) {
		// TODO Auto-generated method stub
		Intent intent = SearchdetailsActivity.this.getPackageManager()
				.getLaunchIntentForPackage(packageName);
		if (intent != null) {
			// we found the activity
			// now start the activity
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.setPackage(packageName);
			intent.putExtra(Intent.EXTRA_TEXT,
					"Check out this listing from Hummus+ app!" + "\n\n"
							+ "Name:" + Share.s_name + "\n" + "Address:"
							+ Share.s_addre + "\n" + "Telephone:"
							+ Share.s_telephone);
			startActivity(intent);

		} else {

			Toast.makeText(SearchdetailsActivity.this,
					appname + " App not found on device!", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int requestcode, int resultcode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestcode, resultcode, data);
		callbackManager.onActivityResult(requestcode, resultcode, data);
		if (resultcode == RESULT_OK) {
			if (requestcode == 1) {
				if (NetworkManager
						.isInternetConnected(SearchdetailsActivity.this)) {
					new JSONADDImage().execute();
				}
			}

			else if (requestcode == 2) {
				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				picturePath = c.getString(columnIndex);
				if (NetworkManager
						.isInternetConnected(SearchdetailsActivity.this)) {
					new JSONADDImage1().execute();
				}
			}
		}
	}

	class JSONADDImage extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchdetailsActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("event_id", String.valueOf(Share.s_id));

			response = Webservice
					.Send_message(
							param,
							"http://israelinla.com/hummus/api/app.php?action=user_uplode&event_id=1",
							"img", mediaFile.toString());
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			try {

				JSONObject data = new JSONObject(response);
				if (data.getString("success").equals("1")) {
					// Toast.makeText(getApplicationContext(), "Successs",
					// Toast.LENGTH_SHORT).show();
					CustomDailog.showAlertDialog(SearchdetailsActivity.this,
							"Photo Upload", "Successfully");
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	class JSONADDImage1 extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchdetailsActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("event_id", String.valueOf(Share.s_id));

			response = Webservice
					.Send_message(
							param,
							"http://israelinla.com/hummus/api/app.php?action=user_uplode&event_id=1",
							"img", picturePath);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			try {

				JSONObject data = new JSONObject(response);
				if (data.getString("success").equals("1")) {
					// Toast.makeText(getApplicationContext(), "Successs",
					// Toast.LENGTH_SHORT).show();
					CustomDailog.showAlertDialog(SearchdetailsActivity.this,
							"Photo Upload", "Successfully");
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	class JSONADDreview extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchdetailsActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("event_id", String.valueOf(Share.s_id));
			param.put("message", review);
			param.put("date", date);

			response = Webservice
					.Send_data(
							param,
							"http://israelinla.com/hummus/api/app.php?action=review&event_id=1&message=great%20fun&date=19-09-2015");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			try {
				JSONObject data = new JSONObject(response);

				if (data.getString("success").equals("1")) {
					// Toast.makeText(getApplicationContext(), "Successs",
					// Toast.LENGTH_SHORT).show();
					et_review.setText("");
					CustomDailog.showAlertDialog(SearchdetailsActivity.this,
							"Review Send", "Successfully");

				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	public class Jsonratecheck extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;
		Builder builder;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			pDialog = new ProgressDialog(SearchdetailsActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> param = new HashMap<String, String>();

			param.put("event_id", String.valueOf(Share.s_id));
			param.put("device_id", String.valueOf(Share.s_deviceid));
			response = Webservice
					.Send_data(
							param,
							"http://israelinla.com/hummus/api/app.php?action=check_rat&event_id=1&device_id=2563985214");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			try {

				JSONObject data = new JSONObject(response);
				String rate_check = data.getString("success");
				Share.S_ratecheck = rate_check;

				if (data.getString("success").equals("1")) {
					ratebar.setIsIndicator(true);
				}

				if (data.getString("success").equals("0")) {

				}

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

	}

	public void onBackPressed() {
		Intent mainActivity = new Intent(SearchdetailsActivity.this,
				SearchActivity.class);
		ActivityCompat.finishAffinity(this);

		startActivity(mainActivity);

	}

}
