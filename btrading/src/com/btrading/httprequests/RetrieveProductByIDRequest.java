package com.btrading.httprequests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.util.Log;

import com.btrading.models.Product;
import com.btrading.utils.JSONExtractor;
import com.btrading.utils.StaticObjects;

public class RetrieveProductByIDRequest implements Runnable {

	private Product productToRetrieve;
	private StaticObjects staticObjects;
	
	public RetrieveProductByIDRequest(Product INproduct)
	{
		this.setProductToRetrieve(INproduct);
		this.staticObjects = new StaticObjects();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/RetrieveProductByID/"+this.productToRetrieve.getProductID()+"?token="+staticObjects.getToken());
		
        Log.i("RETRIEVE PRODUCT BY ID REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE PRODUCT BY ID RESPONSE",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractProductByIDRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
			
	}

	public Product getProductToRetrieve() {
		return productToRetrieve;
	}

	public void setProductToRetrieve(Product productToRetrieve) {
		this.productToRetrieve = productToRetrieve;
	}

}
