package com.btrading.httprequests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.util.Log;

import com.btrading.models.User;
import com.btrading.models.Wish;
import com.btrading.utils.JSONExtractor;
import com.btrading.utils.StaticObjects;

public class CreateWishRequest implements Runnable {
	
	private StaticObjects staticObjects;
	private Wish wishToCreate;
	private String  name, status;
	private int userID;
	
	
	public CreateWishRequest(String  name, String status,int userID)
	{
		this.staticObjects = new StaticObjects();
		this.name=name;
		this.status= status;
		this.userID = userID;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		//PREPARE REQUEST OBJECT
		//url take from webservice
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/CreateWishList?token="+staticObjects.getToken()+"&INname="+name+"&INstatus="+status+"&INuserID="+userID);
		//HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/CreateUser?token="+staticObjects.getToken()+"&INemail=email&INpassword=password&INnickname=nick&INcontact=contact&INdob=dob&INsex=sex&INimageUrl=imageUrl&INstatus=status");

		//"&password"+password+
        Log.i("CREATE WISH REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("Create WISH RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractCreateWishListRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
