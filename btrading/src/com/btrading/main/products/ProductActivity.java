package com.btrading.main.products;

import com.btrading.main.MainActivity;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class ProductActivity  extends MainActivity 
implements 
ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener{


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_product);
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		
		return true;
	}
}
