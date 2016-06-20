package com.hummus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hummus.model.MapModel;
import com.hummus.share.NetworkManager;
import com.hummus.share.Share;
import com.webservices.JSONfunctions;

public class MapActivity extends FragmentActivity implements OnInfoWindowClickListener{
	LinearLayout l1;
	JSONObject jo;
	JSONArray jsonarray;
	ProgressDialog mProgressDialog;
	
	final String URL = "http://israelinla.com/hummus/api/app.php?action=event_list";
	List<MapModel> list;
	List<MarkerOptions> MarkerOptionsList;
	GoogleMap googlemap;
	Intent intent;
	String text;
	String s_address;
	String name;
	String address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		l1=(LinearLayout)findViewById(R.id.l1);
		l1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				MapActivity.this.finish();
				
				
			}
		});
		addGoogleMap();
		list = new ArrayList<MapModel>();
		if (NetworkManager.isInternetConnected(MapActivity.this)) {
			
			new DownloadJSON().execute();
		}
		
		
		
	}
	private void addGoogleMap() {
		// TODO Auto-generated method stub
		if (googlemap == null) {
	        googlemap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		}
		
		googlemap.setOnInfoWindowClickListener(this);
	}
	

	
	private class DownloadJSON extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			jo = JSONfunctions.getJSONfromURL(URL);

			try {
				jsonarray = jo.getJSONArray("event_list");
				Log.e("tag", "msg response "+jsonarray);
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			mProgressDialog.dismiss();
			try{
				
				if(jsonarray!=null){
					MarkerOptionsList = new ArrayList<MarkerOptions>();
					for(int i=0;i<jsonarray.length();i++){
						
						jo=jsonarray.getJSONObject(i);
						String s_name=jo.getString("name");
						 s_address=jo.getString("address");
						try {
							byte[] data = Base64.decode(s_name, Base64.DEFAULT);
							 text = new String(data, "UTF-8");
							
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						try{
							
							MapModel dataclass=new MapModel(jo.getString("event_id"),text,jo.getDouble("latitude"),jo.getDouble("longitude"),jo.getString("image")
									,(float) jo.getDouble("rating"),jo.getString("des"),jo.getString("website"),jo.getString("telephone"),
									s_address);
							
							if(googlemap!=null){
								MarkerOptions mo = new MarkerOptions();
								Double lat = Double.parseDouble(jo.getString("latitude"));
								Double lng = Double.parseDouble(jo.getString("longitude"));
								LatLng ltlg = new LatLng(lat, lng);
								mo.position(ltlg);
								mo.title(text);
								mo.snippet(jo.getString("address"));
								mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin));
								CameraPosition cp=new CameraPosition.Builder().target(ltlg).zoom(10).build();
								googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
								MarkerOptionsList.add(mo);
								googlemap.addMarker(mo);
								
								googlemap.setInfoWindowAdapter(new InfoWindowAdapter() {
									
									public View getInfoWindow(Marker arg0) {
										// TODO Auto-generated method stub
										return null;
									}
									
									public View getInfoContents(Marker mark) {
										// TODO Auto-generated method stub
										View myContentView = getLayoutInflater().inflate(
							                    R.layout.row_marker, null);
							            TextView tvTitle = ((TextView) myContentView
							                    .findViewById(R.id.title));
							            tvTitle.setText(mark.getTitle());
							            TextView tvSnippet = ((TextView) myContentView
							                    .findViewById(R.id.snippet));
							            tvSnippet.setText(mark.getSnippet());
							            return myContentView;
									}
								});
								
								

							}
							list.add(dataclass);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				else{
				Toast.makeText(getApplicationContext(), "Data Fatching Error", Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e){
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				mProgressDialog = new ProgressDialog(MapActivity.this);
				mProgressDialog.setMessage("Loading...");
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void onBackPressed() {
		  Intent mainActivity = new Intent(MapActivity.this,HomeActivity.class);
		  ActivityCompat.finishAffinity(this);
		     startActivity(mainActivity);
		    
	}
	
	

	public void onInfoWindowClick(Marker mark) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MapActivity.this,MapdetailsActivity.class);

		for(int j=0;j<MarkerOptionsList.size();j++){
			if (MarkerOptionsList.get(j).getTitle().equals(mark.getTitle())){
			
				MapModel itemData = list.get(j);
				@SuppressWarnings("unused")
				String name=itemData.getName();
				Double lat=itemData.getLatitude();
				Double longt=itemData.getLongitude();
				String des=itemData.getDes();
				String image=itemData.getImage();
				String address=itemData.getAddress();
				float rating=itemData.getRating();
				String telephone=itemData.getTelephone();
				String website=itemData.getWebsite();
				String id=itemData.getEvent_id();
				
				Share.sm_name=name;
				Share.sm_id=id;
				Share.sm_des=des;
				Share.sm_image=image;
				Log.e("TAG","====map image====" +Share.sm_image);
				Share.sm_addre=address;
				Share.sm_rating=rating;
				Share.sm_lat=lat;
				Share.sm_long=longt;
				Share.sm_telephone=telephone;
				Share.sm_website=website;
				Log.e("tag","ratuuuuuuuuuu" +Share.sm_rating);
				
			break;
			
			}
		}
		startActivity(intent);
	}

}
