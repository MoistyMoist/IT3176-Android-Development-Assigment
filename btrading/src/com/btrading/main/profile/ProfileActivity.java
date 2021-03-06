package com.btrading.main.profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.UpdateUserRequest;
import com.btrading.main.MainActivity;
import com.btrading.main.login.RegisterActivity;
import com.btrading.main.trading.RetrieveTrades;
import com.btrading.main.trading.TradingActivity;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;
import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {

	StaticObjects staticObjects;
	EditText etEmail, etSex, etDob, etContact, nickName, etPassword;
	TextView tvUserName;
	Button editProfile, btnUpdate, btnClear, btnPastTrades;
	SmartImageView imageView1;
	String updateUserName, updateEmail, updateSex, updateDob, updateContact, updateNickName, updateImageUrl, updateStatus, updatePassword;
	int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_profile);

		tvUserName = (TextView) findViewById(R.id.tvUserName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSex = (EditText) findViewById(R.id.etSex);
		etDob = (EditText) findViewById(R.id.etDob);
		etContact = (EditText) findViewById(R.id.etContact);
		nickName = (EditText) findViewById(R.id.nickName);
		editProfile = (Button) findViewById(R.id.btnEditProfile);
		btnUpdate = (Button) findViewById(R.id.btnProfileUpdate);
		btnClear = (Button) findViewById(R.id.btnClear);
		imageView1 = (SmartImageView) findViewById(R.id.imageView1);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnPastTrades = (Button) findViewById(R.id.btnPastTrades);
		

		tvUserName.setText(Integer.toString(StaticObjects.getCurrentUser()
				.getUserID()));
		etEmail.setText(StaticObjects.getCurrentUser().getEmail());
		etSex.setText(StaticObjects.getCurrentUser().getSex());
		etDob.setText(StaticObjects.getCurrentUser().getDob());
		etContact.setText(StaticObjects.getCurrentUser().getContact());
		nickName.setText(StaticObjects.getCurrentUser().getNickname());

		imageView1.setImageUrl(StaticObjects.getCurrentUser().getImageURL());

		editProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (etEmail.isEnabled() == false) {

					btnUpdate.setVisibility(View.VISIBLE);
					
					tvUserName.setEnabled(true);
					etEmail.setEnabled(true);
					etSex.setEnabled(true);
					tvUserName.setEnabled(true);
					etDob.setEnabled(true);
					etContact.setEnabled(true);
					nickName.setEnabled(true);

					tvUserName.setBackgroundColor(0xffcccccc);
					etEmail.setBackgroundColor(0xffcccccc);
					etSex.setBackgroundColor(0xffcccccc);
					tvUserName.setBackgroundColor(0xffcccccc);
					etDob.setBackgroundColor(0xffcccccc);
					etContact.setBackgroundColor(0xffcccccc);
					nickName.setBackgroundColor(0xffcccccc);

					editProfile.setText("cancel");

				} else if (editProfile.getText() == "cancel") {
					tvUserName.setEnabled(false);
					etEmail.setEnabled(false);
					etSex.setEnabled(false);
					tvUserName.setEnabled(false);
					etDob.setEnabled(false);
					etContact.setEnabled(false);
					nickName.setEnabled(false);

					tvUserName.setBackgroundColor(0x00000000);
					etEmail.setBackgroundColor(0x00000000);
					etSex.setBackgroundColor(0x00000000);

					etDob.setBackgroundColor(0x00000000);
					etContact.setBackgroundColor(0x00000000);
					nickName.setBackgroundColor(0xffcccccc);

					editProfile.setText("edit profile");
					btnUpdate.setVisibility(View.GONE);
					btnClear.setVisibility(View.GONE);
				}
			}
		});

		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				etEmail.setText("");
				etSex.setText("");

				etDob.setText("");
				etContact.setText("");
				nickName.setText("");

				etEmail.setHint("hint: abc@gmail.com");
				etSex.setHint("hint: m/f");

				etDob.setHint("hint: 01/01/1991");
				etContact.setHint("hint: 98765432");
				nickName.setHint("hint: jack");

				tvUserName.setEnabled(true);
				etEmail.setEnabled(true);
				etSex.setEnabled(true);
				tvUserName.setEnabled(true);
				etDob.setEnabled(true);
				etContact.setEnabled(true);
				nickName.setEnabled(true);

				tvUserName.setBackgroundColor(0xffcccccc);
				etEmail.setBackgroundColor(0xffcccccc);
				etSex.setBackgroundColor(0xffcccccc);

				etDob.setBackgroundColor(0xffcccccc);
				etContact.setBackgroundColor(0xffcccccc);
				nickName.setBackgroundColor(0xffcccccc);

			}
		});

		btnPastTrades.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(getBaseContext(), RetrieveTrades.class);		
				startActivity(intent);
			}
			
		});
		
		
		btnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				 if (etEmail.getText().toString().isEmpty()
							|| etPassword.getText().toString().isEmpty()|| nickName.getText().toString().isEmpty() || etContact.getText().toString().isEmpty()|| etDob.getText().toString().isEmpty())
						
					{
						Toast.makeText(
								ProfileActivity.this,
								"Please fill in the empty fields",
								Toast.LENGTH_LONG).show();
					}
				 else{

				
				userId = StaticObjects.getCurrentUser().getUserID();

				//updateUserName = tvUserName.getText().toString();
				updateEmail = etEmail.getText().toString();
				//updatePassword = etPassword.getText().toString();
				// updateSex = etSex.getText().toString();
				updateSex = etSex.getText().toString();
				updateImageUrl = "testImage";
				updateStatus = "testStatus";
				updateDob = etDob.getText().toString();
				updateContact = etContact.getText().toString();
				updateNickName = nickName.getText().toString();

				
			updateUser();	

		
				 }}
		});
		
	}

	
	public void updateUser()
	{
	
		
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
					  	
					  	UpdateUserRequest updateUserRequest = new UpdateUserRequest(userId,updateEmail,updatePassword, updateSex,updateNickName , updateContact,updateDob);
				          
				        executor.execute(updateUserRequest);
						executor.shutdown();
				        try {
				        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				       	  	Log.i(" RESPONSE :","ENDED REQUEST");
				       	  	
				        } catch (InterruptedException e) {}

	                	  runOnUiThread(new Runnable() {
	                          @Override
	                          public void run()
	                          {
	                        	  staticObjects= new StaticObjects();
	                        	  if(StaticObjects.getCurrentUser()==null)
	                        	  {
	                                    Log.i("USER", "NO USER");
	                        	  }
	                        	  else
	                        	  {
//	                        		  	User user = StaticObjects.getCurrentUser();
//	                        		  	if(et_pass.getText().toString().equals(user.getPassword())){
//	                        		  		validUser=true;        
	                        		//  	}
	                        	  }
	                          }
	                        });
				  }
				}).start();
		    Toast.makeText(
					ProfileActivity.this,
					"Account Updated Successfully",
					Toast.LENGTH_LONG).show();
		    Intent intent = new Intent();
		    intent.setClass(getBaseContext(), MainActivity.class);		
			startActivity(intent);
		}
		
		
		 
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
