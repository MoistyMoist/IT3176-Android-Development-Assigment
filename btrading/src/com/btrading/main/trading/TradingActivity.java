package com.btrading.main.trading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.AcceptTradeRequest;
import com.btrading.httprequests.RetrieveUserProductRequest;
import com.btrading.httprequests.RetrieveUserRequest;
import com.btrading.httprequests.SendTradeRequest;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TradingActivity extends Activity {

	ListView lv_products;
	StaticObjects staticObjects;
	ArrayAdapter<CharSequence> adapter;
	String[] productabc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		
		lv_products = (ListView)findViewById(R.id.listviewtradingofferingproducts);
		
		RetrieveObjects();
	}
	
	
	public void RetrieveObjects(){
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
			Log.i("USER", "weird USER");
		}
		}

	
	public void getAllProducts(){
		  productabc = new String[StaticObjects.getUserProducts().size()];
		  for(int i= 0; i<StaticObjects.getUserProducts().size(); i++){
			  productabc[i] = StaticObjects.getUserProducts().get(i).getName();
			  
		  }
		  
		  adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, productabc);
		  lv_products.setAdapter(adapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trading, menu);
		return true;
	}

}
