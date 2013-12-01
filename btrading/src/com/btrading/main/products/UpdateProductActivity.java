package com.btrading.main.products;

import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class UpdateProductActivity extends Activity {

	EditText productName;
	EditText productDescription;
	EditText productQty;
	Spinner productQuality;
	ImageView image;
	Button updateBtn;
	Button uploadImageBtn;
	ArrayAdapter<CharSequence> adapter;
	String[] quality={"Good","Average","Acceptiable"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_update);
		
		productName=(EditText)findViewById(R.id.productName);
		productDescription=(EditText)findViewById(R.id.productDescription);
		productQty=(EditText)findViewById(R.id.productQty);
		productQuality=(Spinner)findViewById(R.id.productQuality);
		uploadImageBtn=(Button)findViewById(R.id.addImage);
		
		adapter= new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, quality);
		productQuality.setAdapter(adapter);
		
		
		productName.setText(StaticObjects.getSelectedProduct().getName());
		productDescription.setText(StaticObjects.getSelectedProduct().getDescription());
		productQty.setText(StaticObjects.getSelectedProduct().getQty());
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_product, menu);
		return true;
	}

	
	public void update()
	{
		
	}
}
