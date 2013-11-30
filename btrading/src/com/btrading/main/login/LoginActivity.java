package com.btrading.main.login;

import com.btrading.main.MainActivity;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends Activity {

	Button b_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_login);
		
		
		b_login = (Button) findViewById(R.id.b_login);
		b_login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(getBaseContext(), MainActivity.class);
				startActivity(intent);
			}
			
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
