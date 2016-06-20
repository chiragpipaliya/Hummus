package com.hummus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hummus.adapter.ListnameAdapter;
import com.hummus.model.ListModel;
import com.hummus.share.GPSTracker;
import com.hummus.share.NetworkManager;
import com.hummus.share.Share;
import com.webservices.Webservice;

public class ListbynameActivity extends Fragment{
	
	EditText esearch;
	ListView l1;
	ListnameAdapter adapter;
	WindowManager windowManager;
	InterstitialAd interstitial;
	ArrayList<ListModel>name_list=new ArrayList<ListModel>();
	Webservice webservice;
	ListActivity la;
	String text,android_id;
	GPSTracker gpsTracker;
	int height,width;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mov=inflater.inflate(R.layout.listname_activity, container,false);
		return mov;
		
		
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		l1=(ListView)view.findViewById(R.id.listbyname);
//		AdView adView = (AdView) getActivity().findViewById(R.id.adView);
//		final String device_id = Secure.getString(
//				getActivity().getContentResolver(), Secure.ANDROID_ID);
//		interstitial = new InterstitialAd(getActivity());
//		interstitial.setAdUnitId("ca-app-pub-9264938368122005/9861881773");
//		
//		
//
//		
//		
//		
//		com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().addTestDevice(device_id)
//				   
//				
//				.build();
//		adView.loadAd(adRequest);
//		
////		AdSize customAdSize = new AdSize(getActivity().getWindowManager().getDefaultDisplay().getWidth(), adView.getHeight());
////		adView.setAdSize(AdSize.SMART_BANNER);
//		
//	
//		
//		interstitial.loadAd(adRequest);
		gpsTracker=new GPSTracker(getActivity());
		Share.slat =gpsTracker.getLatitude();
		Share.slong=gpsTracker.getLongitude();
		
		if (NetworkManager.isInternetConnected(getActivity())){
			new JSONParse().execute();
		}
		
		
     }
	
//	public static final String md5(final String s) {
//	    try {
//	        // Create MD5 Hash
//	        MessageDigest digest = java.security.MessageDigest
//	                .getInstance("MD5");
//	        digest.update(s.getBytes());
//	        byte messageDigest[] = digest.digest();
//
//	        // Create Hex String
//	        StringBuffer hexString = new StringBuffer();
//	        for (int i = 0; i < messageDigest.length; i++) {
//	            String h = Integer.toHexString(0xFF & messageDigest[i]);
//	            while (h.length() < 2)
//	                h = "0" + h;
//	            hexString.append(h);
//	        }
//	        return hexString.toString();
//
//	    } catch (NoSuchAlgorithmException e) {
//	        
//	    }
//	    return "";
//	}
   
	//	@Override
//    public void onDetach() {
//        super.onDetach();
//        Intent intent=new Intent(getActivity(),HomeActivity1.class);
//        startActivity(intent);
//    }
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
//	                Intent intent=new Intent(getActivity(),HomeActivity1.class);
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
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog= new ProgressDialog(getActivity());
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
				Log.e("tag","===================Share.s_lat" +Share.slat);
				
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
							Share.s_imagefinal=row.getString("image");
							Log.e("TAG","==image==" +Share.s_imagefinal);
							try {
								byte[] data = Base64.decode(s_name, Base64.DEFAULT);
								 text = new String(data, "UTF-8");
								
							} catch (Exception e) {
								// TODO: handle exception
							}
							Log.e("TAG","==image==" +row.getString("image"));
							ListModel lm=new ListModel(row.getString("event_id"),text,
									row.getString("address"),
									row.getString("miles"),row.getString("latitude"),row.getString("longitude"),(float) row.getLong("rating"),row.getString("telephone"),row.getString("website"),row.getString("des"));
							
						
							name_list.add(lm);
						}
						adapter=new ListnameAdapter(getActivity(),name_list);
						l1.setAdapter(adapter);
						l1.setAdapter(new ListnameAdapter(getActivity(), name_list));
						Collections.sort(name_list,new ListnameAdapter(getActivity(), name_list));
						Log.e("tag", "ADAPTER"+l1.getCount());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
//				Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
			}
		}
	}

}
