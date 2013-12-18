package com.btrading.main.products;

import java.io.ByteArrayOutputStream;

import com.actionbarsherlock.view.MenuItem;
import com.btrading.main.MainBaseActivity;
import com.btrading.main.ProductListAdapter;
import com.btrading.models.Product;
import com.btrading.utils.LoaderImageView;
import com.btrading.utils.StaticObjects;
import com.btrading.httprequests.*;
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

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProductActivity extends MainBaseActivity {

	private GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private UiSettings uisettings;
    Spinner  productQuality;
    LoaderImageView imageView;
    ImageView imageView2;
	EditText productName;
	EditText productDescription;
	EditText productQty;
	Button uploadImageBtn;
	ProgressDialog progress;
	int imageUpdated=0;
	StaticObjects staticObjects;
	int RESULT_LOAD_IMAGE;
	private Bitmap bitmap;
	String base64;
	
	
	public UpdateProductActivity(){
		super(R.string.title_activity_update_product);
	}

	
	
	ArrayAdapter<CharSequence> adapter;
	String[] quality={"Good","Average","Acceptiable"};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_update);
		
		
		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.product_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		
		setUpMapIfNeeded(Double.parseDouble(StaticObjects.getSelectedProduct().getX()), Double.parseDouble(StaticObjects.getSelectedProduct().getY()));
		uploadImageBtn=(Button)findViewById(R.id.imageButton);
		productName= (EditText)findViewById(R.id.productName);
		productDescription=(EditText)findViewById(R.id.productDescription);
		productQty=(EditText)findViewById(R.id.productQty);
		productQuality=(Spinner)findViewById(R.id.productQuality);
		adapter= new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, quality);
		productQuality.setAdapter(adapter);
		
		imageView=(LoaderImageView)findViewById(R.id.productImage);
		imageView2=(ImageView)findViewById(R.id.productImage2);
		productName.setText(StaticObjects.getSelectedProduct().getName());
		productDescription.setText(StaticObjects.getSelectedProduct().getDescription());
		productQty.setText(StaticObjects.getSelectedProduct().getQty());
		
		imageView.setImageDrawable(StaticObjects.getSelectedProduct().getImageURL());	
	
		uploadImageBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ImageDialogFragment option= new ImageDialogFragment();
				option.show(getFragmentManager(), null);
				
			}});
		
	}
	
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
	
	private void setUpMap(double x, double y) {
		uisettings =mMap.getUiSettings();
        mMap.addMarker(new MarkerOptions().position(new LatLng(x, y)).title(null));
    }
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.update_product)
		{
			
			StaticObjects.getSelectedProduct().setDescription(this.productDescription.getText().toString());
			StaticObjects.getSelectedProduct().setName(this.productName.getText().toString());
			StaticObjects.getSelectedProduct().setQty(this.productQty.getText().toString());
			StaticObjects.getSelectedProduct().setQuality(quality[this.productQuality.getSelectedItemPosition()]);
			if(imageUpdated==1)
			{
				progress = ProgressDialog.show(this, "Uploading image","please wait...", true);
				UploadImageRequest upload= new UploadImageRequest(base64);
				UpdateProductRequest update= new UpdateProductRequest(StaticObjects.getSelectedProduct());
				new BackgroundTask().execute(upload,update);
				imageUpdated=0;
			}
			else
			{
				progress = ProgressDialog.show(this, "Updating","please wait...", true);
				UpdateProductRequest update= new UpdateProductRequest(StaticObjects.getSelectedProduct());
				new BackgroundTask().execute(update,null);
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.update_product, menu);
		return true;
	}
	
	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
			Toast.makeText(getBaseContext(), "Updated..", Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }

	public void loadimage()
	{
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}
	
	public void loadcameral()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    startActivityForResult(intent, RESULT_LOAD_IMAGE);
	    startActivity(intent);
	}
	
	@SuppressLint("ValidFragment")
	private class ImageDialogFragment extends DialogFragment
	{
		@SuppressWarnings("unchecked")
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			String[] eventList={"Use cameral","Select from gallery"};
			@SuppressWarnings("rawtypes")
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_dropdown_item,eventList);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("Select Option");
		    builder.setAdapter(spinnerArrayAdapter, new android.content.DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if(arg1==0)
					{
						loadcameral();
					}
					if(arg1==1)
					{
						loadimage();
					}
				}});

	    return builder.create();
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
        	    && null != data) {
        	   Uri selectedImage = data.getData();
        	   String[] filePathColumn = { MediaStore.Images.Media.DATA };

        	   Cursor cursor = getContentResolver().query(selectedImage,
        	     filePathColumn, null, null, null);
        	   cursor.moveToFirst();

        	   int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        	   String picturePath = cursor.getString(columnIndex);
        	   cursor.close();

        	   decodeFile(picturePath);

        	  }
     
     
    }
	
	public void decodeFile(String filePath) {
		 // Decode image size
		 BitmapFactory.Options o = new BitmapFactory.Options();
		 o.inJustDecodeBounds = true;
		 BitmapFactory.decodeFile(filePath, o);

		 // The new size we want to scale to
		 final int REQUIRED_SIZE = 100;

		 // Find the correct scale value. It should be the power of 2.
		 int width_tmp = o.outWidth, height_tmp = o.outHeight;
		 int scale = 1;
		 while (true) {
		 if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
		   break;
		   width_tmp /= 2;
		   height_tmp /= 2;
		   scale *= 2;
		 }

		 // Decode with inSampleSize
		 BitmapFactory.Options o2 = new BitmapFactory.Options();
		 o2.inSampleSize = scale;
		 bitmap = BitmapFactory.decodeFile(filePath, o2);

		 imageView2.setVisibility(1);
         imageView.setVisibility(2);
         imageView2.setImageBitmap(bitmap);
         imageUpdated=1;
        
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         bitmap.compress(CompressFormat.PNG, 1, bos);
         byte[] data = bos.toByteArray();
         base64 = Base64.encodeToString(data, Base64.NO_WRAP);
         Log.i("image data",data.length+"");
         Log.i("image data",base64.length()+"");
         Log.i("image data",base64.charAt(base64.length()-1)+"");
         Log.i("image data",base64);
       
	}
	    
}
