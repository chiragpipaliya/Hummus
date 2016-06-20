package com.hummus;

import java.io.InputStream;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity
{
	// declare InputStream variable for manage database
		public static InputStream databaseInputStream1;     
//		final DBAdapter dba = new DBAdapter(this);
		
		private Handler guiThread;
		// declare Runnable variable for update database data
		private Runnable updateTask;

		//create Database
//		final DBAdapter dba = new DBAdapter(this);
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		new InsertTask().execute("");
	}
	//Restore data from old database
		private class InsertTask extends AsyncTask<String, Void, Boolean> {

			@SuppressLint("SdCardPath")
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				try
				{
					//create Calendar.sql database file on default database path
					/*File f = new File("/data/data/com.orthodox/databases/"+Share.DATABASE_NAME);
					if (f.exists()) {
						//
					}
					else 
					{
						try
						{
							dba.open();
							dba.close();
							databaseInputStream1 = getAssets().open(Share.DATABASE_NAME);
							//copy old data
							importdatabase.copyDataBase();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}*/
				} catch (Exception e) {
				}

			}

			@Override
			protected Boolean doInBackground(String... params) {

				Boolean success = false;
				try {
					success = true;
				} catch (Exception e) {
					if (e.getMessage() != null)
						e.printStackTrace();
				}
				return success;
			}

			@Override
			protected void onPostExecute(Boolean success) {
				super.onPostExecute(success);
				
				//Waiting for copy data after change activity
				call();
			}
		}

		public void call() {
			initThreading();
			guiThread.postDelayed(updateTask, 1000);
		}

		private void initThreading() {
			guiThread = new Handler();
			updateTask = new Runnable() {
				public void run() {
					changeImage();
				}
			};
		}

		private void changeImage() 
		{
				Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
				//set animation for splash screen 
//				setActivityAnimation(Splash.this, R.anim.fade_in, R.anim.fade_out);
		}

		static public void setActivityAnimation(Activity activity, int in, int out) {
			try {
				Method method = Activity.class.getMethod("overridePendingTransition", new Class[] { int.class,int.class });
				method.invoke(activity, in, out);
			} catch (Exception e) {
			}
		}
}
