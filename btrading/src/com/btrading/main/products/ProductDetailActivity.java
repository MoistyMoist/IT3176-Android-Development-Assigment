package com.btrading.main.products;

import com.btrading.main.MainBaseActivity;
import com.btrading.utils.LoaderImageView;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends MainBaseActivity {

	private GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private UiSettings uisettings;
    
    TextView productName, productDescription,productQty, productQuality;
    LoaderImageView imageView;
    
    
    
    
    
	public ProductDetailActivity(){
		super(R.string.title_activity_product_detail);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		
		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.product_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		
		setUpMapIfNeeded(Double.parseDouble(StaticObjects.getSelectedProduct().getX()), Double.parseDouble(StaticObjects.getSelectedProduct().getY()));
		
		productName= (TextView)findViewById(R.id.productName);
		productDescription=(TextView)findViewById(R.id.productDescription);
		productQty=(TextView)findViewById(R.id.productQty);
		productQuality=(TextView)findViewById(R.id.productQuality);
		
		imageView=(LoaderImageView)findViewById(R.id.productImage);
		
		productName.setText(StaticObjects.getSelectedProduct().getName());
		productDescription.setText(StaticObjects.getSelectedProduct().getDescription());
		productQty.setText(StaticObjects.getSelectedProduct().getQty());
		productQuality.setText(StaticObjects.getSelectedProduct().getQuality());
		
		imageView.setImageDrawable(StaticObjects.getSelectedProduct().getImageURL());
		
//		if(StaticObjects.getCurrentUser().getUserID()==StaticObjects.getSelectedProduct().getUser().getUserID())
//		{
//			
//		}
		
		
		
		
		
		
	}

	@SuppressWarnings("unused")
	private void setUpMapIfNeeded(double x,double y) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap(x,y);
                
                LatLng singapore = new LatLng(x,y);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 14));
               
                uisettings.setMyLocationButtonEnabled(false);
                uisettings.setAllGesturesEnabled(false);
                uisettings.setCompassEnabled(false);
                uisettings.setRotateGesturesEnabled(false);
                uisettings.setScrollGesturesEnabled(false);
                uisettings.setTiltGesturesEnabled(false);
                uisettings.setZoomControlsEnabled(false);
                uisettings.setZoomGesturesEnabled(false);
                
            }
        }
    }
	@SuppressWarnings("unused")
	private void setUpMap(double x, double y) {
		uisettings =mMap.getUiSettings();
        mMap.addMarker(new MarkerOptions().position(new LatLng(x, y)).title(null));
    }

}
