package com.btrading.httprequests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.util.Log;

import com.btrading.utils.JSONExtractor;
import com.btrading.utils.StaticObjects;

public class SearchProductRequest implements Runnable {
	
	private StaticObjects staticObjects;
	private String key;
	
	public SearchProductRequest(String key)
	{
		this.staticObjects = new StaticObjects();
		this.key=key;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/SearchProduct?token="+staticObjects.getToken()+"&INkey="+this.key);
		
        Log.i("SEARCH PRODUCT REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("SEARCH PRODUCT RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractSearchProductRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
