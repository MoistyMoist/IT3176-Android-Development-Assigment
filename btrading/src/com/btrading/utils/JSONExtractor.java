package com.btrading.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.btrading.models.Product;
import com.btrading.models.User;
import com.btrading.models.Wish;

import android.util.Log;

public class JSONExtractor {

	//JSON NODE NAMES
	private static final String TAG_MESSAGE ="Message";
	private static final String TAG_DATA = "Data";
	private static final String TAG_STATUS="Status";
	private static final String TAG_ERRORS="Errors";
	private static final String TAG_USER="USER";
	private static final String TAG_PRODUCTS="PRODUCTs";
	//JSON PRODUCT NODE NAMES
	private static final String TAG_PRODUCT_ID="productID";
	private static final String TAG_PRODUCT_NAME="name";
	private static final String TAG_PRODUCT_DESCRIPTION="description";
	private static final String TAG_PRODUCT_QTY="qty";
	private static final String TAG_PRODUCT_QUALITY="quality";
	private static final String TAG_PRODUCT_IMAGEURL="imageURL";
	private static final String TAG_PRODUCT_XLOCATION="x";
	private static final String TAG_PRODUCT_YLOCATION="y";	
	//JSON USER NODE NAMES
	private static final String TAG_USER_ID="userID";
	private static final String TAG_USER_PASSWORD="password";
	private static final String TAG_USER_EMAIL="email";
	private static final String TAG_USER_NICKNAME="nickname";
	private static final String TAG_USER_CONTACT="contact";
	private static final String TAG_USER_DOB="dob";
	private static final String TAG_USER_SEX="sex";
	private static final String TAG_USER_IMAGEURL="imageURL";
	private static final String TAG_USER_STATUS="status";
	//JSON WISHLIST NODE NAMES
	private static final String TAG_WISH_ID="wishID";
	private static final String	TAG_WISH_NAME="name";
	private static final String TAG_WISH_STATUS="status";
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
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Product>products= new ArrayList<Product>();
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("product ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Product p= new Product();
					p.setProductID(c.getInt(TAG_PRODUCT_ID));
					p.setName(c.getString(TAG_PRODUCT_NAME));
					p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
					p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
					p.setQty(c.getString(TAG_PRODUCT_QTY));
					p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
					p.setX(c.getString(TAG_PRODUCT_XLOCATION));
					p.setY(c.getString(TAG_PRODUCT_YLOCATION));
					
					User u= new User();
					u.setUserID(c.getInt(TAG_USER_ID));
				
					p.setUser(u);
					products.add(p);
					
					//Log.i("product "+i,c.toString() );
				}
				StaticObjects.setAllProducts(products);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE RESULTS FOR REMOVING PRODUCT
	public void ExtractRemoveProduct(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			Product p = null;
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("product ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					p= new Product();
					p.setProductID(c.getInt(TAG_PRODUCT_ID));
					p.setName(c.getString(TAG_PRODUCT_NAME));
					p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
					p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
					p.setQty(c.getString(TAG_PRODUCT_QTY));
					p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
					p.setX(c.getString(TAG_PRODUCT_XLOCATION));
					p.setY(c.getString(TAG_PRODUCT_YLOCATION));
					
					User u= new User();
					
					u.setUserID(c.getInt(TAG_USER_ID));			
					p.setUser(u);
					
				}
				for(int i=0; i<StaticObjects.getUserProducts().size();i++)
				{
					if(StaticObjects.getUserProducts().get(i).getProductID()==p.getProductID())
					{
						StaticObjects.getUserProducts().remove(i);
						break;
					}
				}
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR RETRIEVING PRODUCT BY ID
	public void ExtractProductByIDRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			if(StaticObjects.getRequestStatus()==0)
			{
				
				JSONObject c=RawData.getJSONObject(0);
				
				Product p= new Product();
				p.setProductID(c.getInt(TAG_PRODUCT_ID));
				p.setName(c.getString(TAG_PRODUCT_NAME));
				p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
				p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
				p.setQty(c.getString(TAG_PRODUCT_QTY));
				p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
				p.setX(c.getString(TAG_PRODUCT_XLOCATION));
				p.setY(c.getString(TAG_PRODUCT_YLOCATION));
				
				User u= new User();
				JSONObject c2=(JSONObject) c.get(TAG_USER);
				
				u.setUserID(c2.getInt(TAG_USER_ID));
				u.setContact(c2.getString(TAG_USER_CONTACT));
				u.setDob(c2.getString(TAG_USER_DOB));
				u.setEmail(c2.getString(TAG_USER_EMAIL));
				u.setImageURL(c2.getString(TAG_USER_IMAGEURL));
				u.setNickname(c2.getString(TAG_USER_NICKNAME));
				u.setPassword(c2.getString(TAG_USER_PASSWORD));
				u.setSex(c2.getString(TAG_USER_SEX));
				u.setStatus(c2.getString(TAG_USER_STATUS));
				
				p.setUser(u);
			
				
				StaticObjects.setSelectedProduct(p);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR RETRIEVING PRODUCT BY USER
	public void ExtractUserProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Product>products= new ArrayList<Product>();
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("product ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Product p= new Product();
					p.setProductID(c.getInt(TAG_PRODUCT_ID));
					p.setName(c.getString(TAG_PRODUCT_NAME));
					p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
					p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
					p.setQty(c.getString(TAG_PRODUCT_QTY));
					p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
					p.setX(c.getString(TAG_PRODUCT_XLOCATION));
					p.setY(c.getString(TAG_PRODUCT_YLOCATION));
					
					User u= new User();
					
					u.setUserID(c.getInt(TAG_USER_ID));
					
					p.setUser(u);
					products.add(p);
					//Log.i("product "+i,c.toString() );
				}
				StaticObjects.setUserProducts(products);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR CREATING A NEW PRODUCT
	public void ExtractCreateProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Product>products= new ArrayList<Product>();
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("product ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Product p= new Product();
					p.setProductID(c.getInt(TAG_PRODUCT_ID));
					p.setName(c.getString(TAG_PRODUCT_NAME));
					p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
					p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
					p.setQty(c.getString(TAG_PRODUCT_QTY));
					p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
					p.setX(c.getString(TAG_PRODUCT_XLOCATION));
					p.setY(c.getString(TAG_PRODUCT_YLOCATION));
					
					User u= new User();
					u.setUserID(c.getInt(TAG_USER_ID));

					p.setUser(u);
					products.add(p);
				}
				StaticObjects.getAllProducts().add(products.get(0));
				StaticObjects.getUserProducts().add(products.get(0));
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE RRESULT FOR UPDATING A PRODUCT
	public void ExtractUpdateProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Product>products= new ArrayList<Product>();
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("product ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Product p= new Product();
					p.setProductID(c.getInt(TAG_PRODUCT_ID));
					p.setName(c.getString(TAG_PRODUCT_NAME));
					p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
					p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
					p.setQty(c.getString(TAG_PRODUCT_QTY));
					p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
					p.setX(c.getString(TAG_PRODUCT_XLOCATION));
					p.setY(c.getString(TAG_PRODUCT_YLOCATION));
					
					User u= new User();
					u.setUserID(c.getInt(TAG_USER_ID));

					p.setUser(u);
					products.add(p);
				}
				for(int i=0;i<StaticObjects.getUserProducts().size();i++)
				{
					Log.i("for loop",StaticObjects.getUserProducts().get(i).getName());
					if(StaticObjects.getUserProducts().get(i).getProductID()==StaticObjects.getSelectedProduct().getProductID())
					{
						StaticObjects.getUserProducts().remove(i);
						StaticObjects.getUserProducts().add(products.get(0));
						break;
					}
				}
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR SEARCHING A PRODUCT 
	public void ExtractSearchProductRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Product>products= new ArrayList<Product>();
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("product ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Product p= new Product();
					p.setProductID(c.getInt(TAG_PRODUCT_ID));
					p.setName(c.getString(TAG_PRODUCT_NAME));
					p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
					p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
					p.setQty(c.getString(TAG_PRODUCT_QTY));
					p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
					p.setX(c.getString(TAG_PRODUCT_XLOCATION));
					p.setY(c.getString(TAG_PRODUCT_YLOCATION));
					
					User u= new User();
					
					u.setUserID(c.getInt(TAG_USER_ID));
					p.setUser(u);
					products.add(p);
				}
				StaticObjects.setAllProducts(products);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE USER DATA AFTER LOGGING IN
	public void ExtractUserRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			Log.i("raw", RawData.toString());
			
			if(StaticObjects.getRequestStatus()==0)
			{
				
				JSONObject c=RawData.getJSONObject(0);
				
				User u= new User();
				//JSONObject c2=(JSONObject) c.get(TAG_USER);
				
				u.setUserID(c.getInt(TAG_USER_ID));
				u.setContact(c.getString(TAG_USER_CONTACT));
				u.setDob(c.getString(TAG_USER_DOB));
				u.setEmail(c.getString(TAG_USER_EMAIL));
				u.setImageURL(c.getString(TAG_USER_IMAGEURL));
				u.setNickname(c.getString(TAG_USER_NICKNAME));
				u.setPassword(c.getString(TAG_USER_PASSWORD));
				u.setSex(c.getString(TAG_USER_SEX));
				u.setStatus(c.getString(TAG_USER_STATUS));
			
				
				StaticObjects.setCurrentUser(u);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}

	//THIS METHODS EXTRACTS THE IMAGE URL AFTER UPLOADING THE BASE64 DATA
	public void ExtractUploadImage(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            String string = result;
            String[] parts = string.split("</Data>");
            String part1 = parts[0]; // 004
           // Log.i("JSON", part1.length()+"");
            String url=part1.replace("<ImageModel xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.datacontract.org/2004/07/BarterTradingWebServices.Model\"><Data>", "");
            
            Log.i("JSON", url);
            if(StaticObjects.getSelectedProduct()!=null)
            {
            	StaticObjects.getSelectedProduct().setImageURL(url);
            	StaticObjects.getSelectedProduct().setImageURL(url.replace(" ", "%2520"));
            }
            if(StaticObjects.getProductToCreate()!=null)
            {
            	StaticObjects.getProductToCreate().setImageURL(url.replace(" ", "%2520"));
            }
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE RESULT FOR CREATING A NEW USER
	public void ExtractCreateUserRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            Log.i("ABCD - Created USer",json.toString() );
            
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			Log.i("ABCD - Created USer",RawData.toString() );
			
			ArrayList<User>user= new ArrayList<User>();
			if(StaticObjects.getRequestStatus()==0)
			{
				
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					User user1= new User();
					user1.setEmail(c.getString(TAG_USER_EMAIL));
					user1.setPassword(c.getString(TAG_USER_PASSWORD));
					user1.setNickname(c.getString(TAG_USER_NICKNAME));
					user1.setContact(c.getString(TAG_USER_CONTACT));
					user1.setDob(c.getString(TAG_USER_DOB));
					
					
					User u= new User();
					u.setUserID(c.getInt(TAG_USER_ID));

					
					user.add(user1);
				}
				//What to do in this???
			//	StaticObjects.getAllUser().add(User.get(0));
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}

	
	public void ExtractUpdateUserRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
	
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("user ",RawData.toString() );
				ArrayList<User>user= new ArrayList<User>();
								
				JSONObject c=RawData.getJSONObject(0);
				
				User user1= new User();
				user1.setEmail(c.getString(TAG_USER_EMAIL));
				user1.setPassword(c.getString(TAG_USER_PASSWORD));
				user1.setNickname(c.getString(TAG_USER_NICKNAME));
				user1.setContact(c.getString(TAG_USER_CONTACT));
				user1.setDob(c.getString(TAG_USER_DOB));
				user1.setUserID(c.getInt(TAG_USER_ID));
				user.add(user1);
				
				StaticObjects.setCurrentUser(user1);
			
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	}

	


	public void ExtractWishRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		// TODO Auto-generated method stub
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			Log.i("raw", RawData.toString());
			
			if(StaticObjects.getRequestStatus()==0)
			{
				ArrayList<Wish> wishList = new ArrayList<Wish>();
				for(int i=0;i<RawData.length();i++){
				
				JSONObject c=RawData.getJSONObject(i);
				
				Wish w = new Wish();
				w.setName(c.getString(TAG_WISH_NAME));
				w.setStatus(c.getString(TAG_WISH_STATUS));
				w.setWishID(c.getInt(TAG_WISH_ID));
				
				/*
				User u= new User();
				//JSONObject c2=(JSONObject) c.get(TAG_USER);
				u.setUserID(c.getInt(TAG_USER_ID));
				u.setContact(c.getString(TAG_USER_CONTACT));
				u.setDob(c.getString(TAG_USER_DOB));
				u.setEmail(c.getString(TAG_USER_EMAIL));
				u.setImageURL(c.getString(TAG_USER_IMAGEURL));
				u.setNickname(c.getString(TAG_USER_NICKNAME));
				u.setPassword(c.getString(TAG_USER_PASSWORD));
				u.setSex(c.getString(TAG_USER_SEX));
				u.setStatus(c.getString(TAG_USER_STATUS));
				*/
				
				wishList.add(w);
				}
				
				StaticObjects.setUserWishlist(wishList);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	
	}



	public void ExtractCreateWishListRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            Log.i("ABCD - Created Wish",json.toString() );
            
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			Log.i("ABCD - Created Wish",RawData.toString() );
			
			ArrayList<Wish>wish1= new ArrayList<Wish>();
			if(StaticObjects.getRequestStatus()==0)
			{
				
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Wish wish= new Wish();
					wish.setName(TAG_WISH_NAME);
					wish.setStatus(TAG_WISH_STATUS);
					
					
					
					
					
					

					
					wish1.add(wish);
				}
				//What to do in this???
			//	StaticObjects.getAllUser().add(User.get(0));
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
	
    	}}


      //THIS METHOD EXTRACTS THE RESULT FOR CREATING A NEW USER
   
	