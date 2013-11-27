package com.btrading.main;

import com.actionbarsherlock.view.MenuItem;
import com.btrading.main.wish.AddWishlistActivity;
import com.example.btrading.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SettingActivity extends MainBaseActivity {
	
	public SettingActivity(){
		super(R.string.title_activity_setting);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_setting);
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
