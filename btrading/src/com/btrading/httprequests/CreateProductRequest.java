package com.btrading.httprequests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.util.Log;

import com.btrading.models.*;
import com.btrading.utils.StaticObjects;
import com.btrading.utils.JSONExtractor;

public class CreateProductRequest implements Runnable{

	private Product productToCreate;
	private StaticObjects staticObjects;
	
	public CreateProductRequest(Product INproduct)
	{
		this.productToCreate=INproduct;
		setStaticObjects(new StaticObjects());
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet(""); 
        
        Log.i("CREATE PRODUCT REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("CREATE PRODUCT RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractAllProductRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
		
	}


	public Product getProductToCreate() {
		return productToCreate;
	}


	public void setProductToCreate(Product productToCreate) {
		this.productToCreate = productToCreate;
	}

	public StaticObjects getStaticObjects() {
		return staticObjects;
	}

	public void setStaticObjects(StaticObjects staticObjects) {
		this.staticObjects = staticObjects;
	}

}
