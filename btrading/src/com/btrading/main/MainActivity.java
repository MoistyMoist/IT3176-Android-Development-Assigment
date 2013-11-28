package com.btrading.main;

import com.example.btrading.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.btrading.httprequests.*;
import com.btrading.main.login.LoginActivity;
import com.btrading.main.products.ProductActivity;
import com.btrading.main.products.ProductDetailActivity;
import com.btrading.models.Product;
import com.btrading.utils.StaticObjects;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends MainBaseActivity {

	ListView lv_products;
	ProductListAdapter adapter;
	ProgressDialog progress;
	TextView tv;
	StaticObjects staticObjects;
	ListView listview;
	Context context;
	
	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		sm.setShadowDrawable(R.drawable.shadow);
	
		
		listview = (ListView) findViewById(R.id.productList);
		staticObjects = new StaticObjects();
		context=getBaseContext();
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent= new Intent();
				intent.setClass(getApplication(), ProductDetailActivity.class);
				startActivity(intent);
			}});
		if(StaticObjects.getAllProducts()==null||StaticObjects.getAllProducts().size()==0)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
				        RetrieveAllProductRequest retrieveAllProductRequest = new RetrieveAllProductRequest();
				          
				        executor.execute(retrieveAllProductRequest);
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
				        //progress.dismiss();
				        staticObjects= new StaticObjects();
				        if(StaticObjects.getAllProducts().size()==0||StaticObjects.getAllProducts()==null)
				        {
				        	Log.i("PRODUCT", "NO PRODUCT");
				        }
				        else
				        {
				        	adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
						    listview.setAdapter(adapter);
				        }
				        
				      }
				    });
				  }
				}).start();
		}
		else
		{
			adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
		    listview.setAdapter(adapter);
		}
      
	}

	
	public void selectItem()
	{
		
	}

	
	public void searchProduct(View v)
	{
		progress = ProgressDialog.show(this, "Searching",
			    "please wait...", true);
		tv=(TextView)findViewById(R.id.search);

		new Thread(new Runnable() {
			  @Override
			  public void run()
			  {
				  ExecutorService executor = Executors.newFixedThreadPool(1);
					SearchProductRequest searchRequest = new SearchProductRequest(tv.getText().toString());
					
					executor.execute(searchRequest);
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
			        progress.dismiss();
			        staticObjects= new StaticObjects();
			        adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
				    listview.setAdapter(adapter);
			      }
			    });
			  }
			}).start();
	}
}
