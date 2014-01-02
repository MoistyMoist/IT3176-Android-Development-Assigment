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
import android.util.Log;
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
	Boolean validUser = true;
	StaticObjects staticObjects;
	
	
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
				checkUser();
				
				
				
				//createUser.setNickName(quality[productQuality.getSelectedItemPosition()].replace(" ", "%20"));
				//createUser.setQty(productQty.getText().toString().replace(" ", "%20"));
				//createUser.setImageURL("");
				
				
			}}); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	public void checkUser()
	{
		
		//go in with empty fields
//		if (etEmail.getText().toString().equals("") || etPassword2.getText().toString().isEmpty()){
//			validUser = true;
//		}
//		else {
//			
			if(StaticObjects.getCurrentUser()==null)
			{
			    new Thread(new Runnable() {
					  @Override
					  public void run()
					  {
						  	ExecutorService executor = Executors.newFixedThreadPool(1);
						  	
					        CreateUserRequest createUserRequest = new CreateUserRequest(etEmail.getText().toString(),etPassword2.getText().toString(),etNickName.getText().toString(),etContact.getText().toString(),etDOB.getText().toString());
					          
					        executor.execute(createUserRequest);
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
//		                        		  	User user = StaticObjects.getCurrentUser();
//		                        		  	if(et_pass.getText().toString().equals(user.getPassword())){
//		                        		  		validUser=true;        
		                        		//  	}
		                        	  }
		                          }
		                        });
					  }
					}).start();
			}
			else
			{
				Log.i("USER", "weird USER");
			}
			} 
		//}
		
		
	

}
