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
import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	
	StaticObjects staticObjects;
	EditText etUsername,etEmail,etSex,etDob,etContact;
	TextView nickName;
	Button editProfile, btnUpdate, btnClear;
	SmartImageView imageView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_profile);
		
		etUsername = (EditText) findViewById(R.id.etUsername);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSex = (EditText) findViewById(R.id.etSex);
		etDob = (EditText) findViewById(R.id.etDob);
		etContact = (EditText) findViewById(R.id.etContact);
		nickName=(TextView) findViewById(R.id.tvNickName);
		editProfile=(Button) findViewById(R.id.btnEditProfile);
		btnUpdate=(Button) findViewById(R.id.btnUpdate);
		btnClear=(Button) findViewById(R.id.btnClear);
		imageView1 = (SmartImageView) findViewById(R.id.imageView1);
		
		
		etUsername.setText(Integer.toString(StaticObjects.getCurrentUser().getUserID()));
		etEmail.setText(StaticObjects.getCurrentUser().getEmail());
		etSex.setText(StaticObjects.getCurrentUser().getSex());
		etDob.setText(StaticObjects.getCurrentUser().getDob());
		etContact.setText(StaticObjects.getCurrentUser().getContact());
		nickName.setText(StaticObjects.getCurrentUser().getNickname());
		
        imageView1.setImageUrl(StaticObjects.getCurrentUser().getImageURL());
		
		editProfile.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if(etEmail.isEnabled()==false){	
			
				btnUpdate.setVisibility(View.VISIBLE);
				etUsername.setEnabled(true);
				etEmail.setEnabled(true);
				etSex.setEnabled(true);
				etUsername.setEnabled(true);
				etDob.setEnabled(true);
				etContact.setEnabled(true);
				nickName.setEnabled(true);
				
				etUsername.setBackgroundColor(0xffcccccc);
				etEmail.setBackgroundColor(0xffcccccc);
				etSex.setBackgroundColor(0xffcccccc);
				etUsername.setBackgroundColor(0xffcccccc);
				etDob.setBackgroundColor(0xffcccccc);
				etContact.setBackgroundColor(0xffcccccc);
				nickName.setBackgroundColor(0xffcccccc);
				
				editProfile.setText("cancel");
				
			}else if(editProfile.getText()=="cancel")
			{
				etUsername.setEnabled(false);
				etEmail.setEnabled(false);
				etSex.setEnabled(false);
				etUsername.setEnabled(false);
				etDob.setEnabled(false);
				etContact.setEnabled(false);
				nickName.setEnabled(false);
				
				etUsername.setBackgroundColor(0x00000000);
				etEmail.setBackgroundColor(0x00000000);
				etSex.setBackgroundColor(0x00000000);
				etUsername.setBackgroundColor(0x00000000);
				etDob.setBackgroundColor(0x00000000);
				etContact.setBackgroundColor(0x00000000);
				nickName.setBackgroundColor(0xffcccccc);
				
				editProfile.setText("edit profile");
				btnUpdate.setVisibility(View.GONE);
			}
			}});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
}
