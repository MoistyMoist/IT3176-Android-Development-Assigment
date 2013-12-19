package com.btrading.main.login;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.btrading.httprequests.RetrieveAllProductRequest;
import com.btrading.httprequests.RetrieveUserRequest;
import com.btrading.main.MainActivity;
import com.btrading.main.ProductListAdapter;
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
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends Activity {

	StaticObjects staticObjects;
	Button b_login, b_register;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_login);
		
		b_login = (Button) findViewById(R.id.b_login);
		b_register = (Button) findViewById(R.id.b_register);
	 
		b_login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//if (checkUser()){
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.setClass(getBaseContext(), MainActivity.class);
					startActivity(intent);
				//}
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
	
	public boolean checkUser(){
		boolean validUser = false;
		
		if (b_login.getText().equals("") || b_login.getText()==null){
			//to be removed once application completed
			validUser = true;
			return validUser;
		}
		
		if(StaticObjects.getCurrentUser()==null)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
				        RetrieveUserRequest retrieveUserRequest = new RetrieveUserRequest();
				          
				        executor.execute(retrieveUserRequest);
						executor.shutdown();
				        try {
				        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				       	  	Log.i(" RESPONSE :","ENDED REQUEST");
				       	  	
				        } catch (InterruptedException e) {
				           
				        }

				    runOnUiThread(new Runnable() {
				      @Override
				      public void run()
				      {
				        staticObjects= new StaticObjects();
				        if(StaticObjects.getAllProducts().size()==0||StaticObjects.getAllProducts()==null)
				        {
				        	Log.i("PRODUCT", "NO PRODUCT");
				        }
				        else
				        {
				        	//adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
						   // listview.setAdapter(adapter);
				        }
				        
				      }
				    });
				  }
				}).start();
		}
		else
		{
			Log.i("PRODUCT", "weird PRODUCT");
			//adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
		   // listview.setAdapter(adapter);
		}
		
		return validUser;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
