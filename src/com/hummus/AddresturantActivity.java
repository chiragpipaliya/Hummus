package com.hummus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hummus.SearchdetailsActivity.JSONADDImage;
import com.hummus.SearchdetailsActivity.JSONADDImage1;
import com.hummus.share.NetworkManager;
import com.hummus.share.Share;
import com.imageloader.ImageLoader;
import com.webservices.Webservice;

public class AddresturantActivity extends Activity implements OnClickListener {

	LinearLayout llback;
	EditText e_name, e_telepone, e_desc, e_website;
	TextView e_address, tv_imagetext;
	Button b_submit;
	ImageView icon;
	String name, address, telephone, desc, website, image, imagestring,
			main_image, f_name;
	private static int RESULT_LOAD_IMG = 1;
	ImageLoader imageLoader;
	WindowManager windowManager;
	int height, width;

	String imageUri, picturePath;
	private Uri fileUri;
	private static File mediaFile;
	String image_icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_res_activity);

		llback = (LinearLayout) findViewById(R.id.ll_back);
		e_name = (EditText) findViewById(R.id.et_name);
		e_address = (TextView) findViewById(R.id.et_address);
		e_telepone = (EditText) findViewById(R.id.et_telephone);
		e_desc = (EditText) findViewById(R.id.et_description);
		tv_imagetext = (TextView) findViewById(R.id.textview);
		e_website = (EditText) findViewById(R.id.et_website);
		icon = (ImageView) findViewById(R.id.list_icon);
		b_submit = (Button) findViewById(R.id.b_submit);

		windowManager = (WindowManager) AddresturantActivity.this
				.getSystemService(Context.WINDOW_SERVICE);
		width = windowManager.getDefaultDisplay().getWidth();
		height = windowManager.getDefaultDisplay().getHeight();

		icon.getLayoutParams().width = (int) (width * 0.4);
		icon.getLayoutParams().height = (int) (height * 0.4);

		llback.setOnClickListener(this);

		e_address.setOnClickListener(this);

		b_submit.setOnClickListener(this);
		icon.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent mainActivity = new Intent(AddresturantActivity.this,
				HomeActivity.class);
		ActivityCompat.finishAffinity(this);
		mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		e_address.setText("");
		startActivity(mainActivity);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		e_address.setText(Share.su_address);
		// name=e_name.getText().toString();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == llback) {
			onBackPressed();
		}

		if (v == e_address) {
			Intent intent = new Intent(AddresturantActivity.this,
					Map_upload.class);
			startActivity(intent);
		}

		if (v == b_submit) {
			name = e_name.getText().toString();

			telephone = e_telepone.getText().toString();
			desc = e_desc.getText().toString();
			website = e_website.getText().toString();
			if (icon.getDrawable() == null || name.equals("")
					|| telephone.equals("") || e_address.equals("")
					|| website.equals("") || desc.equals("")) {
				CustomDailog.showAlertDialog(AddresturantActivity.this,
						"Message", "Some value is missing");

			}
			if (NetworkManager.isInternetConnected(AddresturantActivity.this)) {
				new JSONSubmit().execute();
			}

		}
		if (v == icon) {

			SelectImage();
		}

	}

	private void SelectImage() {
		// TODO Auto-generated method stub
		final CharSequence[] options = { "Camera", "Photo gallery", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AddresturantActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {
				// TODO Auto-generated method stub
				if (options[item].equals("Camera")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					fileUri = getOutputMediaFileUri(); // set the image file
														// name
					intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
					tv_imagetext.setVisibility(View.GONE);
					startActivityForResult(intent, 1);

				} else if (options[item].equals("Photo gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					tv_imagetext.setVisibility(View.GONE);
					startActivityForResult(intent, 2);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}

		});
		builder.show();
	}

	protected Uri getOutputMediaFileUri() {
		// TODO Auto-generated method stub
		return Uri.fromFile(getOutputMediaFile());
	}

	private File getOutputMediaFile() {
		// TODO Auto-generated method stub
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"Hummus");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("Hummus", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");
		Log.e("Media File------------------------", "" + mediaFile + "----"
				+ Uri.fromFile(mediaFile));
		return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				image_icon = mediaFile.toString();
				Bitmap bmImg = BitmapFactory.decodeFile(image_icon);
				icon.setImageBitmap(bmImg);
			}

			else if (requestCode == 2) {
				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				picturePath = c.getString(columnIndex);
				image_icon = picturePath;
				Bitmap bmImg = BitmapFactory.decodeFile(image_icon);
				icon.setImageBitmap(bmImg);
			}
		}
	}

	class JSONSubmit extends AsyncTask<String, String, String> {
		String response = null;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(AddresturantActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("name", name);
			param.put("website", website);
			param.put("telephone", telephone);
			param.put("address", Share.su_address);
			param.put("des", desc);
			param.put("lat", String.valueOf(Share.su_lat));
			param.put("long", String.valueOf(Share.su_long));

			response = Webservice
					.Send_message(
							param,
							"http://israelinla.com/hummus/api/app.php?action=add_restaurant&name=test&website=www.fuunly.com&telephone=13456879&address=kamrej&des=ucjbdjcb&lat=123131&long=455456456",
							"img", image_icon);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			try {

				JSONObject data = new JSONObject(response);

				if (data.getString("success").equals("1")) {
					icon.setImageResource(R.drawable.ic_launcher);
					e_name.setText("");
					e_address.setText("");
					e_telepone.setText("");
					e_website.setText("");
					e_desc.setText("");
					icon.setImageResource(android.R.color.transparent);
					Share.su_address = String.valueOf("");

					AlertDialog.Builder builder1 = new AlertDialog.Builder(
							AddresturantActivity.this);
					builder1.setMessage("Restaurant Add Successfully..");
					builder1.setCancelable(true);
					builder1.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									Intent intent = new Intent(
											AddresturantActivity.this,
											AdmobActivity.class);
									startActivity(intent);

								}
							});

					AlertDialog alert11 = builder1.create();
					alert11.show();
				}

				if (data.getString("success").equals("0")) {
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		}

	}

}
