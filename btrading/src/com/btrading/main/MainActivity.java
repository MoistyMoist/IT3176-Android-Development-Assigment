package com.btrading.main;

import com.example.btrading.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.view.MenuItem;
import com.btrading.httprequests.*;
import com.btrading.main.products.ProductDetailActivity;
import com.btrading.utils.StaticObjects;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends MainBaseActivity {

	ListView lv_products;
	ProductListAdapter adapter;
	ProgressDialog progress;
	TextView tv;
	EditText search;
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
	
		search=(EditText) findViewById(R.id.search);
		search.setFocusable(true);
		listview = (ListView) findViewById(R.id.productList);
		staticObjects = new StaticObjects();
		context=getBaseContext();
		
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				StaticObjects.setSelectedProduct(StaticObjects.getAllProducts().get(arg2));
				Intent intent= new Intent();
				intent.setClass(getApplication(), ProductDetailActivity.class);
				startActivity(intent);
			}});
		
		
		if(StaticObjects.getAllProducts()==null||StaticObjects.getAllProducts().size()==0)
		{
			progress = ProgressDialog.show(this, "Retrieving market","please wait...", true);
			 RetrieveAllProductRequest retrieveAllProductRequest = new RetrieveAllProductRequest();
			 //UploadImageRequest upload= new UploadImageRequest();
			 new BackgroundTask().execute( retrieveAllProductRequest,null);
		}
		else
		{
			Log.i("PRODUCT", "weird PRODUCT");
			adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
		    listview.setAdapter(adapter);
		}
	}
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.refresh)
		{
			searchProduct(null);
		}
		return super.onOptionsItemSelected(item);
	}
	public void searchProduct(View v)
	{
		progress = ProgressDialog.show(this, "Searching","please wait...", true);
		tv=(TextView)findViewById(R.id.search);
		SearchProductRequest searchRequest = new SearchProductRequest(tv.getText().toString());
		new BackgroundTask().execute(searchRequest,null);
	}

	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
//	        staticObjects= new StaticObjects();
//	        adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
//		    listview.setAdapter(adapter);
			
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(context, "Refreshing..", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }
}
