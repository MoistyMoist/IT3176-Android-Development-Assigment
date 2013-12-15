package com.btrading.httprequests;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.util.Log;

import com.btrading.utils.JSONExtractor;

public class UploadImageRequest implements Runnable {

	String base64;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost= new HttpPost("http://bartertrading.cloudapp.net/api/UploadImage");
		try
		{
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("data",base64));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse response=httpclient.execute(httppost);
			Log.i("UPLOAD IMAGE RESPONSE",response.getStatusLine().toString());
			//PASS THE RESPONSE TO THE EXTRACTOR
	        JSONExtractor paser= new JSONExtractor();
	        try {
				paser.ExtractUploadImage(response);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(ClientProtocolException e){}
		catch(IOException ex){}
	}

}
