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
import com.btrading.models.User;
import com.btrading.utils.JSONExtractor;
import com.btrading.utils.StaticObjects;

public class UpdateUserRequest implements Runnable {

	private User userToUpdate;
	private StaticObjects staticObjects;
	private String email, password, nickName, contact, dob, sex, imageUrl, status ;
	
	public UpdateUserRequest(String email, String password, String nickName, String contact, String dob )
	{
		this.staticObjects = new StaticObjects();
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.contact = contact;
		this.dob = dob;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		//HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/UpdateProduct?token="+staticObjects.getToken()+"&INproductID="+this.productToUpdate.getProductID()+"&INname="+this.productToUpdate.getName()+"&INdescription="+this.productToUpdate.getDescription()+"&INqty="+this.productToUpdate.getQty()+"&INstatus="+this.productToUpdate.getStatus()+"&INx="+this.productToUpdate.getX()+"&INy="+this.productToUpdate.getY()+"&INquality="+this.productToUpdate.getQuality()+"&INimageURL="+this.productToUpdate.getImageURL());
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/UpdateUser?token="+staticObjects.getToken()+"&INemail=email&INnickname=nick&INcontact=contact&INdob=dob&INsex=sex&INimageUrl=imageUrl&INstatus=status");
        Log.i("UPDATE User REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("UPDATE User RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractUpdateUserRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public User getUserToUpdate() {
		return userToUpdate;
	}

	public void setUserToUpdate(User userToUpdate) {
		this.userToUpdate = userToUpdate;
	}

}
