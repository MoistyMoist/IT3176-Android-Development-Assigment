package com.btrading.main.wish;

import com.actionbarsherlock.view.MenuItem;
import com.btrading.main.MainBaseActivity;
import com.example.btrading.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WishlistActivity extends MainBaseActivity {

	ListView lv_wish;
	ArrayAdapter<CharSequence> adapter;
	String[] wish_items = {"ABC Duck","TV controller","Water Bottle"};
	
	public WishlistActivity(){
		super(R.string.title_activity_add_wishlist);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_wish);
		
		lv_wish = (ListView) findViewById(R.id.lv_wish);
		adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, wish_items);
		lv_wish.setAdapter(adapter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_add_wishlist:
			Intent intent = new Intent();
			intent.setClass(getBaseContext(), AddWishlistActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.wishlist, menu);
		return true;
	}
}
