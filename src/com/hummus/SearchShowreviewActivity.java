package com.hummus;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hummus.share.NetworkManager;
import com.hummus.share.Share;
import com.imageloader.ImageLoader;
import com.webservices.Webservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchShowreviewActivity extends Activity{
	
	LinearLayout ll_back,ll_review;
	TextView event_title,tv_noreview;
	WindowManager windowManager;
	int width,height;
	ImageView icon;
	String date,review,review_main;
	ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showreview_activity);;
		ll_back=(LinearLayout)findViewById(R.id.l1);
		event_title=(TextView)findViewById(R.id.eventname);
		tv_noreview=(TextView)findViewById(R.id.tv_noreview);
		icon=(ImageView)findViewById(R.id.list_icon);
		imageLoader=new ImageLoader(SearchShowreviewActivity.this);
		windowManager=(WindowManager) SearchShowreviewActivity.this
				.getSystemService(Context.WINDOW_SERVICE);
		width = windowManager.getDefaultDisplay().getWidth();
		height = windowManager.getDefaultDisplay().getHeight();
		event_title.setText(Share.s_name);
		imageLoader.DisplayImage(Share.s_imagefinal, icon);
		icon.getLayoutParams().width=(int)(width*0.3);
		icon.getLayoutParams().height=(int)(height*0.3);
	
		ll_back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				
			}
		});
		if (NetworkManager.isInternetConnected(SearchShowreviewActivity.this)) {
			new JSONShowReview().execute();
		}
		
		
		
	}
	private void rowview(String date,String review_main ){
		try {
			View v=getLayoutInflater().inflate(R.layout.row_showreview,null);
			LinearLayout ll_review=(LinearLayout)findViewById(R.id.ll_review);
			TextView row_review=(TextView)v.findViewById(R.id.tv_reviewmain);
			TextView row_date=(TextView)v.findViewById(R.id.tv_reviewdate);
			
			row_review.setText(review_main);
			row_date.setText(date);
			ll_review.addView(v);
		} catch (Exception e) {
		}
	}
	class JSONShowReview extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchShowreviewActivity.this);
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
					.Send_data(
							param,
							"http://israelinla.com/hummus/api/app.php?action=review&event_id=21");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			try {
				JSONObject data = new JSONObject(response);
				if (data.getString("success").equals("0")) {
					tv_noreview.setVisibility(View.VISIBLE);
				}
				else if (data.getString("success").equals("1")){
					JSONArray name = data.getJSONArray("reviwes");
					for (int index = 0; index < name.length(); index++){
						JSONObject row = name.getJSONObject(index);
						if (row.getString("event_id").equals(Share.s_id)){
							date = row.getString("date");
							review = row.getString("review");
							
							try {
								byte[] data1 = Base64
										.decode(review, Base64.DEFAULT);
								review_main = new String(data1, "UTF-8");
							} catch (Exception e) {
								// TODO: handle exception
							}
						}rowview(date, review_main);
					}
				}
			} catch (Exception e) {
					
			}
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent=new Intent(SearchShowreviewActivity.this,SearchdetailsActivity.class);
		ActivityCompat.finishAffinity(this);
		startActivity(intent);
	}
	

}
