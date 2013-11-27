package com.btrading.main.wish;

import com.btrading.main.MainBaseActivity;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
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
	
	
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wishlist, menu);
		return true;
	}
	*/
}
