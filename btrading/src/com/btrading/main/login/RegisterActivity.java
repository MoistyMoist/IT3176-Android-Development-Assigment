package com.btrading.main.login;

import com.btrading.models.User;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RegisterActivity extends Activity {

	User createUser;
	EditText etEmail, etPassword2, etNickName, etContact, etDOB;  
	RadioGroup rgSex;
	Button register, cancel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		createUser = new User();
		etEmail=(EditText)findViewById(R.id.etEmail);
		etPassword2=(EditText)findViewById(R.id.etPassword2);
		etNickName=(EditText)findViewById(R.id.etNickName);
		etContact=(EditText)findViewById(R.id.etContact);
		etDOB=(EditText)findViewById(R.id.etDOB);
		rgSex=(RadioGroup)findViewById(R.id.rgSex);
		register=(Button) findViewById(R.id.register);
		cancel = (Button) findViewById(R.id.cancel);
		
		
		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createUser.setEmail(etEmail.getText().toString().replace(" ", "%20"));
				createUser.setPassword(etPassword2.getText().toString().replace(" ", "%20"));
				createUser.setNickname(etNickName.getText().toString().replace(" ", "%20"));
				createUser.setContact(etContact.getText().toString().replace(" ", "%20"));
				createUser.setDob(etDOB.getText().toString().replace(" ", "%20"));
			
				
				
				//createUser.setNickName(quality[productQuality.getSelectedItemPosition()].replace(" ", "%20"));
				//createUser.setQty(productQty.getText().toString().replace(" ", "%20"));
				//createUser.setImageURL("");
				StaticObjects.setNewUser(createUser);
				
			}}); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
