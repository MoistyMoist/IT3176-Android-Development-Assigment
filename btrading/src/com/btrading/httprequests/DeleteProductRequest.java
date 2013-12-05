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

public class DeleteProductRequest implements Runnable {

	private Product productToRemove;
	private StaticObjects staticObjects;
	
	public DeleteProductRequest(Product INproduct)
	{
		this.setProductToRemove(INproduct);
		this.staticObjects = new StaticObjects();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/DeleteProduct?token="+staticObjects.getToken()+"&INproductID="+this.productToRemove.getProductID());
		
        Log.i("REMOVE PRODUCT REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("REMOVE PRODUCT RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractRemoveProduct(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
				
	}

	public Product getProductToRemove() {
		return productToRemove;
	}

	public void setProductToRemove(Product productToRemove) {
		this.productToRemove = productToRemove;
	}

}
