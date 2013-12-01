package com.btrading.main.products;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.actionbarsherlock.view.MenuItem;

import com.example.btrading.R;
import com.btrading.httprequests.RetrieveAllProductRequest;
import com.btrading.httprequests.RetrieveUserProductRequest;
import com.btrading.main.MainBaseActivity;
import com.btrading.main.ProductListAdapter;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ProductActivity  extends MainBaseActivity{

	
	UserProductListAdapter adapter;
	ListView list;
	StaticObjects staticObjects;
	Context context;
	
	public ProductActivity(){
		super(R.string.title_activity_product);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_product);

		list = (ListView) findViewById(R.id.productList);
		staticObjects = new StaticObjects();
		context=getBaseContext();
		
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				StaticObjects.setSelectedProduct(StaticObjects.getUserProducts().get(arg2));
				Intent intent= new Intent();
				intent.setClass(getApplication(), UpdateProductActivity.class);
				startActivity(intent);
			}});
		
		
		
		if(StaticObjects.getUserProducts()==null||StaticObjects.getUserProducts().size()==0)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
//				        RetrieveUserProductRequest retrieveUserProductRequest = new RetrieveUserProductRequest(StaticObjects.getCurrentUser());
					  	
					  	User user= new User();
					  	user.setUserID(1);
					  	RetrieveUserProductRequest retrieveUserProductRequest = new RetrieveUserProductRequest(user);
					  	
				        executor.execute(retrieveUserProductRequest);
						executor.shutdown();
				        try {
				        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				       	  	Log.i(" RESPONSE :","ENDED REQUEST");
				       	  	
				        } catch (InterruptedException e) {
				           
				        }

				    runOnUiThread(new Runnable() {
				      @Override
				      public void run()
				      {
				        staticObjects= new StaticObjects();
				        if(StaticObjects.getAllProducts().size()==0||StaticObjects.getAllProducts()==null)
				        {
				        	Log.i("PRODUCT", "NO PRODUCT");
				        }
				        else
				        {
				        	adapter = new UserProductListAdapter(context, StaticObjects.getAllProducts());
						    list.setAdapter(adapter);
				        }
				        
				      }
				    });
				  }
				}).start();
		}
		else
		{
			Log.i("PRODUCT", "weird PRODUCT");
			adapter = new UserProductListAdapter(context, StaticObjects.getAllProducts());
		    list.setAdapter(adapter);
		}
		
		
		
		
		
		
		
		
		
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.menu_add_wishlist)
		{
			Intent intent= new Intent();
			intent.setClass(getApplication(), AddProductActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.wishlist, menu);
		return true;
	}


}
