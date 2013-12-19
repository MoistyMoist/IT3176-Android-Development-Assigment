package com.btrading.main.products;

import java.io.ByteArrayOutputStream;
import com.actionbarsherlock.view.MenuItem;
import com.btrading.httprequests.CreateProductRequest;
import com.btrading.httprequests.UploadImageRequest;
import com.btrading.main.MainBaseActivity;
import com.btrading.models.Product;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProductActivity extends MainBaseActivity{

	private GoogleMap mMap;
	Button buttonMap;
    @SuppressWarnings("unused")
	private LocationClient mLocationClient;
    @SuppressWarnings("unused")
	private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private UiSettings uisettings;
    
    int RESULT_LOAD_IMAGE;
    Product productToCreate;
    boolean imageAdded=false;
    ProgressDialog progress;
    String base64;
    Bitmap bitmap;
    
	ArrayAdapter<CharSequence> adapter;
	Product newProduct;
	EditText productName, productDescription, productQty;
	Spinner productQuality;
	ImageView imageView;
	String[] quality={"Good","Average","Acceptiable"};
	Button addImage;
	
	
	
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
		
		productToCreate= new Product();
		
		productQuality=(Spinner)findViewById(R.id.productQuality);
		adapter= new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, quality);
		productQuality.setAdapter(adapter);

		productName=(EditText)findViewById(R.id.productName);
		productDescription=(EditText)findViewById(R.id.productDescription);
		productQty=(EditText)findViewById(R.id.productQty);
		imageView=(ImageView)findViewById(R.id.productImage);
		addImage = (Button)findViewById(R.id.addImage);
		addImage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ImageDialogFragment option= new ImageDialogFragment();
				option.show(getFragmentManager(), null);
			}});
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
                
                LatLng singapore = new LatLng(1.36,103.8);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 14));
                mMap.setMyLocationEnabled(true);
                uisettings.setMyLocationButtonEnabled(true);
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

	private void setUpMap() {
		uisettings =mMap.getUiSettings();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(x, y)).title(null));
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.add_product)
		{
			productToCreate.setName(productName.getText().toString().replace(" ", "%20"));
			productToCreate.setDescription(productDescription.getText().toString().replace(" ", "%20"));
			productToCreate.setQuality(quality[productQuality.getSelectedItemPosition()].replace(" ", "%20"));
			productToCreate.setQty(productQty.getText().toString().replace(" ", "%20"));
			productToCreate.setImageURL("");
			StaticObjects.setProductToCreate(productToCreate);
			
			if(mMap.getMyLocation()==null)
			{
				Toast.makeText(getBaseContext(), "Select Map's GPS to get your location", Toast.LENGTH_SHORT).show();
			}
			else if(productToCreate.getName().equals("")||productToCreate.getDescription().equals("")||productToCreate.getQty().equals("")||imageAdded==false)
			{
				
				Toast.makeText(getBaseContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
				StaticObjects.getProductToCreate().setX(mMap.getMyLocation().getLatitude()+"");
				StaticObjects.getProductToCreate().setY(mMap.getMyLocation().getLongitude()+"");
			}
			else
			{
				User user= new User();
			  	user.setUserID(1);
				StaticObjects.getProductToCreate().setUser(user);
				progress = ProgressDialog.show(this, "Uploading item","please wait...", true);
				StaticObjects.getProductToCreate().setX(mMap.getMyLocation().getLatitude()+"");
				StaticObjects.getProductToCreate().setY(mMap.getMyLocation().getLongitude()+"");
				UploadImageRequest upload= new UploadImageRequest(base64);
				CreateProductRequest create= new CreateProductRequest(StaticObjects.getProductToCreate());
				new BackgroundTask().execute(upload,create);
			}
		}
		return super.onOptionsItemSelected(item);
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
		 final int REQUIRED_SIZE = 2000;

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


        imageView.setImageBitmap(bitmap);
        imageAdded=true;
       
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 1, bos);
        byte[] data = bos.toByteArray();
        base64 = Base64.encodeToString(data, Base64.NO_WRAP);
//        Log.i("image data",data.length+"");
//        Log.i("image data",base64.length()+"");
//        Log.i("image data",base64.charAt(base64.length()-1)+"");
//        Log.i("image data",base64);
      
	}
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.add_product, menu);
		return true;
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

	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
			Toast.makeText(getBaseContext(), "Item Created..", Toast.LENGTH_SHORT).show();
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
}
