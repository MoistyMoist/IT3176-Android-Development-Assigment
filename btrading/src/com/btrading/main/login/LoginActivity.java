package com.btrading.main.login;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.RetrieveAllProductRequest;
import com.btrading.httprequests.RetrieveUserRequest;
import com.btrading.main.MainActivity;
import com.btrading.main.ProductListAdapter;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	StaticObjects staticObjects;
	Button b_login, b_register;
	EditText et_username, et_pass;
	boolean validUser = false;
	String message = "No message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_login);
		
		b_login = (Button) findViewById(R.id.b_login);
		b_register = (Button) findViewById(R.id.b_register);
		et_username = (EditText) findViewById(R.id.et_username);
		et_pass = (EditText) findViewById(R.id.et_pass);
		
		b_login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				b_login.setEnabled(false);
				checkUser();
				new CheckLogin().execute("email");
			}
		});
		
		staticObjects = new StaticObjects();
		
		b_register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(getBaseContext(), RegisterActivity.class);
				startActivity(intent);
			}});
	}
	
	public void checkUser(){
		
		if (et_username.getText().toString().equals("") || et_username.getText().toString().isEmpty()){
			validUser = true;
		}
		
		else {
		
		if(StaticObjects.getCurrentUser()==null)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
				        RetrieveUserRequest retrieveUserRequest = new RetrieveUserRequest(et_username.getText().toString());
				          
				        executor.execute(retrieveUserRequest);
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
	                                    message = "No user with that email.";
	                        	  }
	                        	  else
	                        	  {
	                        		  checkingUser();
	                        	  }
	                          }
	                        });
				  }
				}).start();
		}
		else
		{
			Log.i("USER", "weird USER");
			checkingUser();
		}
		} 
	}
	
	public void checkingUser(){
	  	User user = StaticObjects.getCurrentUser();
	  	if(et_pass.getText().toString().equals(user.getPassword())){
	  		validUser=true;        
	  	}
	  	else{
	  		message = "Wrong Password.";
	  	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private class CheckLogin extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
                for(int i=0;i<2;i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return "Okay";
        }        

		@Override
        protected void onPostExecute(String result) {   
			//Toast.makeText(getApplicationContext(), "validUser"+validUser, Toast.LENGTH_SHORT).show();
			b_login.setEnabled(true);
            if (validUser){
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(getBaseContext(), MainActivity.class);
				startActivity(intent);
            }
            else {
            	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
