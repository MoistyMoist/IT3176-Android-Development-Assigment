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

public class SendTradeRequest implements Runnable {

	private StaticObjects staticObjects;
	private int productOfferID;
	private int productTakeID;
	private int userOfferID;
	private int userTakeID;
	private String status;
	
	public SendTradeRequest(int productOfferID, int productTakeID, int userOfferID, int userTakeID, String status)
	{
		this.staticObjects = new StaticObjects();
		this.productOfferID = productOfferID;
		this.productTakeID = productTakeID;
		this.userOfferID = userOfferID;
		this.userTakeID = userTakeID;
		this.status = status;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/SendTradeRequest?token="+staticObjects.getToken()+"&INpOfferID="+productOfferID+"&INpTakeID="+productTakeID+"&INuOfferID="+userOfferID+"&INuTakeID="+userTakeID+"&INstatus="+status);
		
        Log.i("RETRIEVE USER REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE USER RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractUserRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
