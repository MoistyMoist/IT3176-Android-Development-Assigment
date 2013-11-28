package com.btrading.main.products;

import com.actionbarsherlock.view.MenuItem;

import com.example.btrading.R;
import com.btrading.main.MainBaseActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.ViewGroup;


public class ProductActivity  extends MainBaseActivity{

	
	
	
	
	public ProductActivity(){
		super(R.string.title_activity_product);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_product);
		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.product_map);
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
