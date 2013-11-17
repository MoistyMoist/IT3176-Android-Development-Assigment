package com.btrading.main;

import com.example.btrading.R;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.btrading.httprequests.*;
import com.btrading.main.BaseActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends MainBaseActivity {

	Button buttonMap;
	
	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		setSlidingActionBarEnabled(true);
		
		RightListFragment rightFrag = new RightListFragment();		
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		
		sm.setSecondaryMenu(R.layout.menu_frame_two);
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.menu_frame_two, rightFrag).commit();					
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		sm.setShadowDrawable(R.drawable.shadow);
        
        
         
        /*
        
        ExecutorService executor = Executors.newFixedThreadPool(1);
        
          AddLocationRequest worker = new AddLocationRequest();
          LoginRequest worker2= new LoginRequest("super@mail.com","password");
         // executor.execute(worker);
          executor.execute(worker2);
          executor.execute(worker);
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
//        executor.shutdown();
//        // Wait until all threads are finish
//        try {
//			executor.awaitTermination(1000, null);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println("Finished all threads");
        
        
        */
        
        

	}



}
