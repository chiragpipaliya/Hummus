package com.hummus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ShareActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_activity);
		Share();
		finish();
		
		
	}

	private void Share() {
		// TODO Auto-generated method stub
		try{
		Uri uri = Uri.parse("android.resource://com.hummus/"+R.drawable.ic_home_logo);
	    Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "I have been using Hummus App on my Android device and I like it.  You should check it out.  Just click the link below and it will redirect you to Google Playstore where you can download it \n \n\n https://play.google.com/store/apps/details?id=com.hummus&hl=en");
        shareIntent.setType("*/*");
		startActivity(Intent.createChooser(shareIntent, "send"));
   
		}
		catch(Exception e){
			e.printStackTrace();
		}
        }
	

}
