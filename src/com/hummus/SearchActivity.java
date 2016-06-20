package com.hummus;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hummus.adapter.SearchAdapter;
import com.hummus.model.ListModel;
import com.hummus.share.NetworkManager;
import com.hummus.share.Share;
import com.webservices.Webservice;

public class SearchActivity extends Activity implements
		SearchView.OnQueryTextListener {

	ListView l1;
	SearchView sv;
	SearchAdapter adapter;
	ArrayList<ListModel> name_list = new ArrayList<ListModel>();
	LinearLayout ll1,lsearch1;
	InterstitialAd interstitial;
	String text;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		l1 = (ListView) findViewById(R.id.slist);
		sv = (SearchView) findViewById(R.id.searchView1);
		sv.onActionViewExpanded();
		sv.setOnQueryTextListener(this);
		
		int searchPlateId = sv.getContext().getResources()
	            .getIdentifier("android:id/search_plate", null, null);
	    View searchPlateView = sv.findViewById(searchPlateId);
	    if (searchPlateView != null) {
	        searchPlateView.setBackgroundColor(Color.WHITE);
	    }

		ll1 = (LinearLayout) findViewById(R.id.l1);
		ll1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchActivity.this,
						HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		AdView adView = (AdView) this.findViewById(R.id.adView);
		final String device_id = Secure.getString(
				SearchActivity.this.getContentResolver(), Secure.ANDROID_ID);
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-9264938368122005/5058898575");
		
		com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(device_id)
				   
				
				.build();
		
		adView.loadAd(adRequest);
		
		interstitial.loadAd(adRequest);
		
		if (NetworkManager.isInternetConnected(SearchActivity.this)) {
			new JSONparse().execute();
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent mainActivity = new Intent(SearchActivity.this,HomeActivity.class);
		ActivityCompat.finishAffinity(this);
	     startActivity(mainActivity);
		
	}

	public class JSONparse extends AsyncTask<String, String, String> {

		String response = null;
		ProgressDialog pDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				HashMap<String, String> param=new HashMap<String, String>();
				param.put("latitude", String.valueOf(Share.slat));
				param.put("longitude", String.valueOf(Share.slong));
				response = Webservice.Send_data(param,"http://israelinla.com/hummus/api/app.php?action=event_list&latitude=25.231837&longitude=72.833581");
				Log.e("tag", "msg response "+response);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			try {

				JSONObject data1 = new JSONObject(response);
				JSONArray jarry = data1.getJSONArray("event_list");
				for (int index = 0; index < jarry.length(); index++) {
					JSONObject row = jarry.getJSONObject(index);
					
					String s_name=row.getString("name");
					try {
						byte[] data = Base64.decode(s_name, Base64.DEFAULT);
						 text = new String(data, "UTF-8");
						
					} catch (Exception e) {
						// TODO: handle exception
					}

					ListModel lm=new ListModel(row.getString("event_id"),text,
							row.getString("address"),
							row.getString("miles"),row.getString("latitude"),row.getString("longitude"),(float) row.getDouble("rating"),row.getString("telephone"),row.getString("website"),row.getString("des"));
					name_list.add(lm);

				}
				
				adapter = new SearchAdapter(SearchActivity.this,name_list);
				l1.setAdapter(adapter);
				Log.e("tag", "ADAPTER"+l1.getCount());
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(SearchActivity.this, "error", Toast.LENGTH_LONG).show();
			}
		}

	}

	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		adapter.getFilter().filter(newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

}
