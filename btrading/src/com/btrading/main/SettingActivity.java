package com.btrading.main;

import com.actionbarsherlock.view.MenuItem;
import com.btrading.main.wish.AddWishlistActivity;
import com.example.btrading.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SettingActivity extends PreferenceActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_setting);
		addPreferencesFromResource(R.xml.app_setting);
	}

}
