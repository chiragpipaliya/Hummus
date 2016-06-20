package com.hummus.adapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.hummus.ListdetailsActivity;
import com.hummus.R;
import com.hummus.model.ListModel;
import com.hummus.share.Share;
import com.imageloader.ImageLoader;

public class ListnameAdapter extends BaseAdapter implements Comparator<ListModel> {
	
	Context ctx;
	ArrayList<ListModel> namelist;
	Activity activity;
	LayoutInflater inflater;
	ImageLoader imageloader;
	WindowManager windowManager;
	int width;
	int height;
	int i_width;
	int i_height;
	ArrayList<ListModel> mStringFilterList;
	String id;
	String text;
	
	public ListnameAdapter(Activity activity,ArrayList<ListModel>nameList){
		
		super();
		this.activity=activity;
		this.namelist=nameList;
	  mStringFilterList = nameList;
		imageloader = new ImageLoader(activity);
	}
	

	public static double CalculateDistance(double lat1, double lng1, double lat2, double lng2) {
		 float[] result=new float[1];
		 Location.distanceBetween (lat1,lng1,lat2, lng2,  result);
		 return (double)result[0]/1000*0.62137; // in km
		}
	
	
	public int getCount() {
		// TODO Auto-generated method stub
		return namelist.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return namelist.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public class viewHolder
	{
		TextView name,icon;
		TextView Address;
		TextView desc;
		TextView mile;
		ImageView i;
	}
	
	@SuppressWarnings("deprecation")
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		final viewHolder holder;
		View m_view=convertView;
		
		
		if(convertView==null){
			LayoutInflater inflector=activity.getLayoutInflater();
			m_view=inflector.inflate(R.layout.row_list,parent,false);
			holder=new viewHolder();
			holder.name=(TextView)m_view.findViewById(R.id.ename);
			holder.Address=(TextView)m_view.findViewById(R.id.eaddress);
//			holder.icon=(TextView)m_view.findViewById(R.id.textview);
			holder.mile=(TextView)m_view.findViewById(R.id.emile);
			holder.i=(ImageView)m_view.findViewById(R.id.eimage1);
			i_width=holder.i.getWidth();
			
			
			
			Log.e("tag","width"+i_width);
	
			m_view.setTag(holder);
		}
		else{
			
			holder=(viewHolder)convertView.getTag();
		}
		
		if(namelist.size()>position){
			
//			try {
//				byte[] data = Base64.decode(namelist.get(position).getName(), Base64.DEFAULT);
//				String text = new String(data, "UTF-8");
//			
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			holder.icon.setText(namelist.get(position).getName());
			holder.name.setText(namelist.get(position).getName());
			holder.Address.setText(namelist.get(position).getAddress());
			
			windowManager=(WindowManager) activity
					.getSystemService(Context.WINDOW_SERVICE);
			width = windowManager.getDefaultDisplay().getWidth();
			height = windowManager.getDefaultDisplay().getHeight();
			holder.i.getLayoutParams().width=(int)(width*0.2);
			holder.i.getLayoutParams().height=(int)(height*0.2);
//			imageloader.DisplayImage(namelist.get(position).getImage(),holder.i);
			
			holder.mile.setText(namelist.get(position).getMiles());
			
			holder.i.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
			i_width=holder.i.getMeasuredWidth();
			Log.e("tag","====width=====" +i_width);
			
			m_view.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Share.s_addre=namelist.get(position).getAddress();
					Share.s_name=namelist.get(position).getName();
					Share.slist_rating=namelist.get(position).getL_rating();
					Share.s_id=namelist.get(position).getEvent_id();
					Share.s_des=namelist.get(position).getDes();
//					Share.s_image=namelist.get(position).getImage();
					Share.s_lat=namelist.get(position).getLatitude();
					Share.s_long=namelist.get(position).getLongitude();
					Share.sff_rating=namelist.get(position).getRating();
					Share.s_subimage=namelist.get(position).getSub_image();
					Share.s_telephone=namelist.get(position).getTelephone();
					Share.s_website=namelist.get(position).getWebsite();
					Intent intent=new Intent(activity,ListdetailsActivity.class);
					activity.startActivity(intent);
					activity.finish();
					
				}
			});
			
		}
		
		return m_view;
	}

	public int compare(ListModel lhs, ListModel rhs) {
		// TODO Auto-generated method stub
		return lhs.getName().compareToIgnoreCase(rhs.getName());
	
	}
	public int compare1(ListModel lhs, ListModel rhs) {
		// TODO Auto-generated method stub
		return lhs.getMiles().compareToIgnoreCase(rhs.getMiles());
	
	}
	
}
	
