package com.hummus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class CustomDailog {

	public static void showAlertDialog(final Activity activity, String message1,String message2 ) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity,AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle(message1);
		builder.setMessage(message2);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{
			
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
