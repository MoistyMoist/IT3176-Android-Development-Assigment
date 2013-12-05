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

public class UpdateProductRequest implements Runnable {

	private Product productToUpdate;
	private StaticObjects staticObjects;
	
	public UpdateProductRequest(Product INproduct)
	{
		this.setProductToUpdate(INproduct);
		this.staticObjects = new StaticObjects();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/UpdateProduct?token="+staticObjects.getToken()+"&INproductID="+this.productToUpdate.getProductID()+"&INname="+this.productToUpdate.getName()+"&INdescription="+this.productToUpdate.getDescription()+"&INqty="+this.productToUpdate.getQty()+"&INstatus="+this.productToUpdate.getStatus()+"&INx="+this.productToUpdate.getX()+"&INy="+this.productToUpdate.getY()+"&INquality="+this.productToUpdate.getQuality()+"&INimageURL="+this.productToUpdate.getImageURL());
		
        Log.i("UPDATE PRODUCT REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("UPDATE PRODUCT RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractUpdateProductRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Product getProductToUpdate() {
		return productToUpdate;
	}

	public void setProductToUpdate(Product productToUpdate) {
		this.productToUpdate = productToUpdate;
	}

}
