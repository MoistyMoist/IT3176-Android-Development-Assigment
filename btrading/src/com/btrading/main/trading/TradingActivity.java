package com.btrading.main.trading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.AcceptTradeRequest;
import com.btrading.httprequests.RetrieveUserProductRequest;
import com.btrading.httprequests.RetrieveUserRequest;
import com.btrading.httprequests.SendTradeRequest;
import com.btrading.main.MainActivity;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TradingActivity extends Activity {

	ListView lv_products;
	StaticObjects staticObjects;
	ArrayAdapter<CharSequence> adapter;
	String[] productOfferName;
	int[] productOfferID;
	int singularProductOfferID;
	int productTakeID;
	int userTakeID;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		
		productTakeID = getIntent().getIntExtra("ptake",0);
		userTakeID = getIntent().getIntExtra("utake",0);
		intent = new Intent(this, MainActivity.class);
		
		lv_products = (ListView)findViewById(R.id.listviewtradingofferingproducts);
		
		lv_products.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Open product item
				singularProductOfferID = productOfferID[arg2];
				sendObjects();
			}
			
		});

		
		retrieveObjects();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		
	}

	public void sendObjects(){
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
						SendTradeRequest sendTradeRequest = new SendTradeRequest(singularProductOfferID,productTakeID,staticObjects.getCurrentUser().getUserID(),userTakeID,"Pending");
				          
				        executor.execute(sendTradeRequest);
						executor.shutdown();
				        try {
				        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				       	  	Log.i(" RESPONSE :","ENDED REQUEST");
				       	  	
				        } catch (InterruptedException e) {}

	                	  runOnUiThread(new Runnable() {
	                          @Override
	                          public void run()
	                          {
	                        	  staticObjects= new StaticObjects();
	                  			  Toast.makeText(getBaseContext(), "Successfully sent request", Toast.LENGTH_SHORT).show();
	                  			  startActivity(intent);
	                          }
	                        });
				  }
				}).start();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
	}
	
	public void retrieveObjects(){
		if(StaticObjects.getUserProducts()==null)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
						RetrieveUserProductRequest retrieveUserProductRequest = new RetrieveUserProductRequest(staticObjects.getCurrentUser());
				          
				        executor.execute(retrieveUserProductRequest);
						executor.shutdown();
				        try {
				        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				       	  	Log.i(" RESPONSE :","ENDED REQUEST");
				       	  	
				        } catch (InterruptedException e) {}

	                	  runOnUiThread(new Runnable() {
	                          @Override
	                          public void run()
	                          {
	                        	  staticObjects= new StaticObjects();
	                        	  if(StaticObjects.getUserProducts()==null)
	                        	  {
	                                    Log.i("USER", "NO USER");
	                        	  }
	                        	  else
	                        	  {
	                        		  getAllProducts();
	                        	  }
	                          }
	                        });
				  }
				}).start();
		}
		else
		{
			getAllProducts();
		}
		}

	
	public void getAllProducts(){
		  productOfferName = new String[StaticObjects.getUserProducts().size()];
		  productOfferID = new int[StaticObjects.getUserProducts().size()];
		  for(int i= 0; i<StaticObjects.getUserProducts().size(); i++){
			  productOfferName[i] = StaticObjects.getUserProducts().get(i).getName();
			  productOfferID[i] = StaticObjects.getUserProducts().get(i).getProductID();
		  }
		  
		  adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, productOfferName);
		  lv_products.setAdapter(adapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trading, menu);
		return true;
	}

}
