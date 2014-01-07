package com.btrading.main.trading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.RetrieveUserProductRequest;
import com.btrading.httprequests.RetrieveWishRequest;
import com.btrading.main.wish.WishlistActivity.SampleAdapter;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RetrieveTrades extends Activity implements Runnable {
	
	ListView lv_trades;
	StaticObjects staticObjects;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrieve_trades);
		lv_trades = (ListView)findViewById(R.id.lv_trades);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.retrieve_trades, menu);
		return true;
	}
	
	public void retrieveObjects(){
		if(StaticObjects.getAllTrades()==null)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
						RetrieveTrades retrieveTrades = new RetrieveTrades();
				          
				        executor.execute(retrieveTrades);
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
	                        	  }
	                          }
	                        });
				  }
				}).start();
		}
		else
		{
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
