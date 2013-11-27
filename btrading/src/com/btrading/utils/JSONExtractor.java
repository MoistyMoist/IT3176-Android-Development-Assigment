package com.btrading.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONExtractor {

	//THIS METHOD CONVERTS THE HTTP RESPONSE TO JSON.
	//DO NOT EDIT OR REMOVE THIS METHOD
	private static String convertStreamToString(InputStream is) 
	{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
			
	
	
	//THIS METHODS EXTRACTS THE REQUESTED PRODUCTS DATA
	public void ExtractAllProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
	//THIS METHOD EXTRACTS THE RESULTS FOR REMOVING PRODUCT
	public void ExtractRemoveProduct(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR RETRIEVING PRODUCT BY ID
	public void ExtractProductByIDRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR RETRIEVING PRODUCT BY USER
	public void ExtractUserProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR CREATING A NEW PRODUCT
	public void ExtractCreateProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
	//THIS METHOD EXTRACTS THE RRESULT FOR UPDATING A PRODUCT
	public void ExtractUpdateProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR SEARCHING A PRODUCT 
	public void ExtractSearchProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
}
