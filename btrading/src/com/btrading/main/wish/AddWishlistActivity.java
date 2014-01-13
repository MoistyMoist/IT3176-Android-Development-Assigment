package com.btrading.main.wish;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.CreateUserRequest;
import com.btrading.httprequests.CreateWishRequest;
import com.btrading.main.login.LoginActivity;
import com.btrading.main.login.RegisterActivity;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
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

				for (int i = 0; i < staticObjects.getUserWishlist().size(); i++) {
					if (StaticObjects.getUserWishlist().get(i).getName()
							.equals(wishProduct.getText().toString())) {
						Toast.makeText(AddWishlistActivity.this,
								"Item Already in your Wish list",
								Toast.LENGTH_LONG).show();
					} else {

						new Thread(new Runnable() {
							@Override
							public void run() {
								ExecutorService executor = Executors
										.newFixedThreadPool(1);
								int i = staticObjects.getCurrentUser()
										.getUserID();
								String userID = String.valueOf(i);

								CreateWishRequest createWishRequest = new CreateWishRequest(
										wishProduct.getText().toString(),
										"unavailable", userID);

								executor.execute(createWishRequest);
								executor.shutdown();
								try {
									executor.awaitTermination(Long.MAX_VALUE,
											TimeUnit.NANOSECONDS);
									Log.i(" RESPONSE :", "ENDED REQUEST");

								} catch (InterruptedException e) {
								}

								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										staticObjects = new StaticObjects();
										if (StaticObjects.getCurrentUser() == null) {
											Log.i("USER", "NO USER");
										} else {
											// User user =
											// StaticObjects.getCurrentUser();
											// if(et_pass.getText().toString().equals(user.getPassword())){
											// validUser=true;
											// }
										}
									}
								});
							}
						}).start();
						Toast.makeText(
								AddWishlistActivity.this,
								"Wishlist Created for\n"
										+ staticObjects.getCurrentUser()
												.getNickname() + "\n Thank You",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						intent.setClass(getBaseContext(),
								WishlistActivity.class);
						startActivity(intent);
						finish();

					}

				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_wishlist, menu);
		return true;
	}

}
