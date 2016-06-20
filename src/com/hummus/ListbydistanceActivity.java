package com.hummus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hummus.adapter.ListdistanceAdapter;
import com.hummus.model.ListdistanceModel;
import com.hummus.share.NetworkManager;
import com.hummus.share.Share;
import com.webservices.Webservice;

public class ListbydistanceActivity extends Fragment {
	
	ListView l1;
	ListdistanceAdapter adapter;
	InterstitialAd interstitial;
	ArrayList<ListdistanceModel>name_list=new ArrayList<ListdistanceModel>();
	String text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mov = inflater.inflate(R.layout.listbydistance_activity, container, false);
		return mov;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		l1=(ListView)view.findViewById(R.id.listbydistance);
//		AdView adView = (AdView) getActivity().findViewById(R.id.adView);
//		final String device_id = Secure.getString(
//				getActivity().getContentResolver(), Secure.ANDROID_ID);
//		interstitial = new InterstitialAd(getActivity());
//		interstitial.setAdUnitId("ca-app-pub-9264938368122005/9861881773");
//		
//		com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(device_id)
//				.build();
//		adView.loadAd(adRequest);
//		
////		AdSize customAdSize = new AdSize(getActivity().getWindowManager().getDefaultDisplay().getWidth(), adView.getHeight());
////		adView.setAdSize(AdSize.SMART_BANNER);
//		
//	
//		
//		interstitial.loadAd(adRequest);
		if (NetworkManager.isInternetConnected(getActivity())) {
			new JSONParse().execute();
		}
	}
//	@Override
//	public void onResume() {
//	    super.onResume();
//
//	    getView().setFocusableInTouchMode(true);
//	    getView().requestFocus();
//	    getView().setOnKeyListener(new View.OnKeyListener() {
//	        public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//	            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//	                // handle back button's click listener
//	                Intent intent=new Intent(getActivity(),HomeActivity.class);
//	              startActivity(intent);
//	            }
//	            return false;
//	        }
//	    });
//	}
	
private class JSONParse extends AsyncTask<String, String, String>{
		String response = null;
		ProgressDialog   pDialog ;
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
		
			pDialog.dismiss();
			try {
					JSONObject data1=new JSONObject(response);
						JSONArray name=data1.getJSONArray("event_list");
						for(int index=0;index<name.length();index++){
							JSONObject row=name.getJSONObject(index);
							String s_name=row.getString("name");
							try {
								byte[] data = Base64.decode(s_name, Base64.DEFAULT);
								 text = new String(data, "UTF-8");
								
							} catch (Exception e) {
								// TODO: handle exception
							}
							ListdistanceModel lm=new ListdistanceModel(row.getString("event_id"),row.getString("image"),text,
									row.getString("address"),
									row.getString("miles"),row.getString("latitude"),row.getString("longitude"),(float) row.getLong("rating"),row.getString("telephone"),row.getString("website"),row.getString("des"));
							Log.e("Tag","lat"+row.getString("latitude"));
							name_list.add(lm);
						}
						adapter=new ListdistanceAdapter(getActivity(),name_list);
						l1.setAdapter(adapter);
						l1.setAdapter(new ListdistanceAdapter(getActivity(), name_list));
						Collections.sort(name_list,new ListdistanceAdapter(getActivity(), name_list));
						Log.e("tag", "ADAPTER"+l1.getCount());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
//				Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
				pDialog= new ProgressDialog(getActivity());
	            pDialog.setMessage("Loading ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
		}
	}
}
