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
import com.btrading.utils.JSONExtractor;
import com.btrading.utils.StaticObjects;

public class CreateUserRequest implements Runnable {
	
	private StaticObjects staticObjects;
	private User userToCreate;
	private String email, password, nickName, contact, dob, sex, imageUrl, status ;
	
	
	public CreateUserRequest(String email, String password, String nickName, String contact, String dob )
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
		//url take from webservice
		//HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/CreateUser?token="+staticObjects.getToken()+"&email="+email+"&nickname"+"&contact"+contact+"&dob"+dob+"&sex"+sex+"&imageUrl"+imageUrl+"&status"+status);
		HttpGet httpget = new HttpGet("http://bartertrading.cloudapp.net/api/CreateUser?token="+staticObjects.getToken()+"&INemail=email&INnickname=nick&INcontact=contact&INdob=dob&INsex=sex&INimageUrl=imageUrl&INstatus=status");

		//"&password"+password+
        Log.i("CREATE USER REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("Create USER RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractCreateUserRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
