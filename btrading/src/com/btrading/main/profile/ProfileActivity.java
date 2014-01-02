package com.btrading.main.profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.RetrieveUserRequest;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class ProfileActivity extends Activity {
	
	StaticObjects staticObjects;
	EditText etUsername,etEmail,etSex,etDob,etContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_profile);
		
		etUsername = (EditText) findViewById(R.id.etUsername);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSex = (EditText) findViewById(R.id.etSex);
		etDob = (EditText) findViewById(R.id.etDob);
		etContact = (EditText) findViewById(R.id.etContact);
		
		
		etUsername.setText(StaticObjects.getCurrentUser().getUserID());
		etEmail.setText(StaticObjects.getCurrentUser().getEmail());
		etSex.setText(StaticObjects.getCurrentUser().getSex());
		etDob.setText(StaticObjects.getCurrentUser().getDob());
		etContact.setText(StaticObjects.getCurrentUser().getContact());
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
}
