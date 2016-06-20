package com.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;

import android.util.Log;

public class Webservice {

	public static String webServiceCall(String strUrl, String operation)
			throws IOException {
		URL url = null;
		String response = null;

		try {

			strUrl = strUrl.replace(" ", "%20");

			url = new URL(strUrl);

			System.out.println("------------------start----------------"
					+ operation + " response--------------------------------");
			Log.e("", "URL : " + url);
			InputSource doc = new InputSource(url.openStream());
			response = convertStreamToString(doc.getByteStream());
			Log.e("", "RESPONSE : " + response);
			System.out.println("-------------------end------------------"
					+ operation + " response--------------------------------");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;

	}

	public static String convertStreamToString(InputStream is)
			throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}

	}

	public static String Send_message(HashMap<String, String> valueMap,
			String url, String filekey, String filepath) {

		HttpRequest httpRequest = new HttpRequest();
		// HashMap<String, String> valueMap = new HashMap<String, String>();

		String response = "";

		try {

			// valueMap.clear();
			// valueMap.put("id_ad", id_ad);
			// valueMap.put("email", email);
			// valueMap.put("phone", phone);
			// valueMap.put("msg_text", msg_text);

			// Log.e("", "email=" + email);
			// Log.e("", " password=" + password);

			// Log.e("", "final==" + filepath);
			response = httpRequest.doPostWithFile(url, filekey, filepath,valueMap);

			// user_image
			// l_image_name

			System.out.println("Response  ----" + response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public static String Send_data(HashMap<String, String> valueMap, String url) {

		HttpRequest httpRequest = new HttpRequest();
		// HashMap<String, String> valueMap = new HashMap<String, String>();

		String response = "";

		try {

			// valueMap.clear();
			// valueMap.put("id_ad", id_ad);
			// valueMap.put("email", email);
			// valueMap.put("phone", phone);
			// valueMap.put("msg_text", msg_text);

			// Log.e("", "email=" + email);
			// Log.e("", " password=" + password);

			// Log.e("", "final=="+filepath);
			response = httpRequest.doPost(url, valueMap);

			System.out.println("Response  ----" + response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public static String get_response(String url_main) {

		HttpClient httpclient = new DefaultHttpClient();
		String response = "";
		URI url = null;
		String login1 = null;

		try {

			login1 = url_main;

			url = new URI(login1.replace(" ", "%20"));

			Log.e("my webservice", "Login : " + url);

			HttpPost httppost = new HttpPost(url);
			HttpResponse httpResponse = null;

			try {

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						3);

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httpResponse = httpclient.execute(httppost);

				response = EntityUtils.toString(httpResponse.getEntity(),
						"UTF-8");

				// this is what we extended for the getting the response string
				// which we going to parese for out use in database //

//				System.out.println("get my local centre response : " + response);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.e("Error : ", "Client Protocol exception");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("Error : ", "IO Exception");

			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return response;
	}

	public static String search_mapview(String city_name) {

		HttpClient httpclient = new DefaultHttpClient();
		String response = null;
		URI url;
		String login1 = "";
		// String login2 = "";
		try {

			// login1 = Share.url_search_map
			// + city_name
			// +
			// "&output=json&key=ABQIAAAAsV7S85DtCo0H9T4zv19FoRTdT40ApbWAnDYRE0-JyP5I6Ha9-xT9G5hCQO5UtOKSH5M3qhp5OXiWaA";

			// login2= login1.replace("%2F", "/");
			//
			// login2 = login2.replace("%3D%0A", "=");

			url = new URI(login1.replace(" ", "%20"));

			Log.e("my webservice", "search_map : " + url);
			System.out.println(url);
			HttpPost httppost = new HttpPost(url);
			HttpResponse httpResponse = null;

			try {

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						3);

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				httpResponse = httpclient.execute(httppost);

				response = EntityUtils.toString(httpResponse.getEntity(),
						"UTF-8");

				// this is what we extended for the getting the response string
				// which we going to parese for out use in database //

				System.out
						.println("get my local centre response : " + response);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.e("Error : ", "Client Protocol exception");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("Error : ", "IO Exception");

			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return response;
	}

}
