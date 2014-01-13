package com.btrading.main.wish;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.CreateUserRequest;
import com.btrading.httprequests.CreateWishRequest;
import com.btrading.main.MainActivity;
import com.btrading.main.login.LoginActivity;
import com.btrading.main.login.RegisterActivity;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWishlistActivity extends Activity {

	EditText wishProduct;
	Button addWishButton;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_add_wishlist);

		wishProduct = (EditText) findViewById(R.id.wishProduct);
		addWishButton = (Button) findViewById(R.id.addWishButton);

		addWishButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int userID = StaticObjects.getCurrentUser().getUserID();
				CreateWishRequest createWishRequest = new CreateWishRequest(
						wishProduct.getText().toString(),
						"unavailable", userID);
				new AddWishList().execute(createWishRequest, null);
				
			} 
		}); 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_wishlist, menu);
		return true;
	}
	

	private class AddWishList extends AsyncTask<Runnable, Integer, Long> {

		@Override
		protected void onPostExecute(Long result) {

			super.onPostExecute(result);
			if (progress != null)
				progress.dismiss();
			//return to wishlist
			Intent intent = new Intent();
			intent.setClass(getBaseContext(),
					WishlistActivity.class);
			startActivity(intent);
			finish();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {

			for (int i = 0; i < task.length; i++) {
				if (task[i] != null)
					task[i].run();
				if (isCancelled())
					break;
			}
			return null;
		}
	}

}
