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
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.btrading.httprequests.*;
import com.btrading.main.login.LoginActivity;
import com.btrading.models.Product;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends MainBaseActivity {

	ListView lv_products;
	ArrayAdapter<CharSequence> adapter;
	
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
	
		
		final ListView listview = (ListView) findViewById(R.id.productList);


	    ArrayList<Product> list = new ArrayList<Product>();
	    for (int i = 0; i <10; ++i) {
	    	Product p= new Product();
	    	p.setDescription("description");
	    	p.setName("name");
	      list.add(p);
	    }
	    ProductListAdapter adapter = new ProductListAdapter(this, list);
	    listview.setAdapter(adapter);
        
        
	    ExecutorService executor = Executors.newFixedThreadPool(1);
         RetrieveAllProductRequest worker = new RetrieveAllProductRequest();
          
          executor.execute(worker);
          executor.execute(worker);
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        
          executor.shutdown();
          try {
        	  executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        	  Log.i(" RESPONSE :","ENDED REQUEST");
          } catch (InterruptedException e) {
            
          }
	}



	
	public void searchProduct(View v)
	{
		//add a searching loader
		
		TextView tv=(TextView)findViewById(R.id.search);
		Log.i("serch","searching");
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		SearchProductRequest searchRequest = new SearchProductRequest(tv.getText().toString());
		
		executor.execute(searchRequest);
		executor.shutdown();
        try {
        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
       	  	Log.i(" RESPONSE :","ENDED REQUEST");
        } catch (InterruptedException e) {
           
        }
        
	}
	
	

}
