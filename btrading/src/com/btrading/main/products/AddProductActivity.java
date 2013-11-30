package com.btrading.main.products;

import com.actionbarsherlock.view.MenuItem;
import com.btrading.main.MainBaseActivity;
import com.btrading.models.Product;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddProductActivity extends MainBaseActivity{

	
	
	private GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private UiSettings uisettings;
    
    
	ArrayAdapter<CharSequence> adapter;
	Product newProduct;
	EditText productName, productDescription, productQty;
	Spinner productQuality;
	ImageView image;
	String[] quality={"Good","Average","Acceptiable"};
	
	
	
	public AddProductActivity(){
		super(R.string.title_activity_add_product);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_add_product);
		
		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.product_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		setUpMapIfNeeded();
		
		productQuality=(Spinner)findViewById(R.id.productQuality);
		adapter= new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, quality);
		productQuality.setAdapter(adapter);
		
		
		productName=(EditText)findViewById(R.id.productName);
		productDescription=(EditText)findViewById(R.id.productDescription);
		productQty=(EditText)findViewById(R.id.productQty);
		image=(ImageView)findViewById(R.id.productImage);
		///get device location x & y
	}

	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                
                LatLng singapore = new LatLng(103,14);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 5));
               
 
                uisettings.setZoomControlsEnabled(false);
 
                
            }
        }
    }
	@SuppressWarnings("unused")
	private void setUpMap() {
		uisettings =mMap.getUiSettings();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(x, y)).title(null));
    }
	
	public void SelectImage()
	{
		
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.add_product)
		{
			//some validation
			if(productName.getText().equals("")||productDescription.getText().equals("")||productQty.getText().equals(""))
			{
				
			}
			else
			{
				//check image 
				//upload
				//get location xy
				
				//create product
				
			}
			
			
			
			
			
			
			
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.add_product, menu);
		return true;
	}
	
}
