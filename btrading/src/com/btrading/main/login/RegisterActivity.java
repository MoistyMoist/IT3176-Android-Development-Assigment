package com.btrading.main.login;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.CreateUserRequest;
import com.btrading.httprequests.RetrieveUserRequest;

import com.btrading.models.User;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	User createUser;
	EditText etEmail, etPassword2, etNickName, etContact, etDOB, etPassword;
	RadioGroup rgSex;
	Button register, cancel, addUserImage;
	Boolean validUser = true;
	StaticObjects staticObjects;
	String message = "No message";
	ImageView userImage;
	private RadioButton rb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		createUser = new User();
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword2 = (EditText) findViewById(R.id.etPassword2);
		etNickName = (EditText) findViewById(R.id.etNickName);
		etContact = (EditText) findViewById(R.id.etContact);
		etDOB = (EditText) findViewById(R.id.etDOB);
		rgSex = (RadioGroup) findViewById(R.id.rgSex);
		register = (Button) findViewById(R.id.register);
		cancel = (Button) findViewById(R.id.cancel);
		

		etEmail.clearFocus();

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(getBaseContext(), LoginActivity.class);
				startActivity(intent);
				finish();
			}

		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				checkUser();

				// createUser.setNickName(quality[productQuality.getSelectedItemPosition()].replace(" ",
				// "%20"));
				// createUser.setQty(productQty.getText().toString().replace(" ",
				// "%20"));
				// createUser.setImageURL("");

			}

		});

	}

	public void checkUser() {

		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword2 = (EditText) findViewById(R.id.etPassword2);
		etNickName = (EditText) findViewById(R.id.etNickName);
		etContact = (EditText) findViewById(R.id.etContact);
		etDOB = (EditText) findViewById(R.id.etDOB);
		
		
		
		 if (etEmail.getText().toString().isEmpty()
				|| etPassword2.getText().toString().isEmpty()|| etNickName.getText().toString().isEmpty() || etContact.getText().toString().isEmpty()|| etDOB.getText().toString().isEmpty())
			
		{
			Toast.makeText(
					RegisterActivity.this,
					"Please fill in the empty fields",
					Toast.LENGTH_LONG).show();
		}
		 else if(!(etPassword.getText().toString().equals(etPassword2.getText().toString())))
			{
				
				Toast.makeText(
						RegisterActivity.this,
						"Password Dont match, Please try again",
						Toast.LENGTH_LONG).show();
			}

		else{
		int selectedOption = rgSex.getCheckedRadioButtonId();
		rb = (RadioButton) findViewById(selectedOption);

		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(1);

				CreateUserRequest createUserRequest = new CreateUserRequest(
						etEmail.getText().toString(), etPassword2.getText()
								.toString(), etNickName.getText().toString(),
						etContact.getText().toString(), etDOB.getText()
								.toString(), rb.getText().toString(), "test",
						"test");

				executor.execute(createUserRequest);
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
							// User user = StaticObjects.getCurrentUser();
							// if(et_pass.getText().toString().equals(user.getPassword())){
							// validUser=true;
							// }
						}
					}
				});
			}
		}).start();
		Toast.makeText(
				RegisterActivity.this,
				"Account Created for\n" + etNickName.getText() + "\n Thank You",
				Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.setClass(getBaseContext(), LoginActivity.class);
		startActivity(intent);
		finish();
	}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
}
