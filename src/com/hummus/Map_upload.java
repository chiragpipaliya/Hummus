package com.hummus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hummus.share.GPSTracker;
import com.hummus.share.Share;

public class Map_upload extends FragmentActivity implements OnMapClickListener {
	GoogleMap googlemap;
	Geocoder geocoder;
	LinearLayout ll_back,ll_done;
	List<android.location.Address> addresses;
	String lat,lonng;
	TextView tv_address;
	LatLng ltlg;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapupload_activity);
		ll_back=(LinearLayout)findViewById(R.id.l1);
		ll_done=(LinearLayout)findViewById(R.id.ll_done);
		tv_address=(TextView)findViewById(R.id.t_address);
		ll_back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Map_upload.this,AddresturantActivity.class);
				startActivity(intent);
			}		
		});
		ll_done.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stu			
				onBackPressed();
			}
		});
		addGooglemap();
		googlemap.setOnMapClickListener(this);
		
//		googlemap.setInfoWindowAdapter(new InfoWindowAdapter() {
//			
//			public View getInfoWindow(Marker arg0) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			public View getInfoContents(Marker marker) {
//				// TODO Auto-generated method stub
//				View myContentView = getLayoutInflater().inflate(
//	                    R.layout.row_marker, null);
//	            TextView tvTitle = ((TextView) myContentView
//	                    .findViewById(R.id.title));
//	            tvTitle.setText("");
//	            
//	            return myContentView;
//			}
//		});
		
	}
	

	private void addGooglemap() {
		// TODO Auto-generated method stub
		if (googlemap == null) {
	        googlemap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	        GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
			Share.su_lat=gpsTracker.getLatitude();
			Share.su_long=gpsTracker.getLongitude();
	        LatLng ltlg = new LatLng(Share.su_lat, Share.su_long);
	        CameraPosition cp=new CameraPosition.Builder().target(ltlg).zoom(13).build();
	        googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
	        MarkerOptions mo=new MarkerOptions();
	        mo.position(ltlg);
	        mo.title("Your current Location");
	        googlemap.addMarker(mo);
	     
		}
		
	}
	

	

	public void onMapClick(LatLng latLang) {
		// TODO Auto-generated method stub
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(latLang);
		markerOptions.title("You Place Marker");
		
	
//		Share.su_lat=latLang.latitude;
//		Share.su_long=latLang.longitude;
		Log.e("tag","=========lat=========" +Share.su_lat);
		Log.e("tag","=========long=========" +Share.su_long);
		googlemap.clear();
		
		 googlemap.addMarker(markerOptions);
		 
		 geocoder=new Geocoder(this,Locale.getDefault());
		 
		 try {
			addresses=geocoder.getFromLocation(latLang.latitude,latLang.longitude, 1);
			if(addresses.get(0).getPostalCode()!=null){
				String addrress=addresses.get(0).getAddressLine(0)+","+addresses.get(0).getLocality()+","+addresses.get(0).getAdminArea()+","+addresses.get(0).getCountryName()+"," +
						addresses.get(0).getPostalCode();
				Share.su_address=addrress;
				Log.e("tag","=========address=========" +addrress);
				tv_address.setText(addrress);
			}
			else {
			String addrress=addresses.get(0).getAddressLine(0)+","+addresses.get(0).getLocality()+","+addresses.get(0).getAdminArea()+","+addresses.get(0).getCountryName();
			Share.su_address=addrress;
			Log.e("tag","=========address=========" +addrress);
			tv_address.setText(addrress);
			
			}
//			String city=addresses.get(0).getLocality();
//			String State=addresses.get(0).getAdminArea();
//			String country=addresses.get(0).getCountryName();
//			String postalcode=addresses.get(0).getPostalCode();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
