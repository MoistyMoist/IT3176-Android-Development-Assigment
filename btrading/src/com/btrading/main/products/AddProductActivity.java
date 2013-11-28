package com.btrading.main.products;

import com.btrading.models.Product;
import com.example.btrading.R;
import com.example.btrading.R.layout;
import com.example.btrading.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddProductActivity extends Activity {

	ArrayAdapter<CharSequence> adapter;
	Product newProduct;
	EditText productName, productDescription, productQty;
	Spinner productQuality;
	String[] quality={"Good","Average","Acceptiable"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_add_product);
		
		productQuality=(Spinner)findViewById(R.id.productQuality);
		//adapter= new ArrayAdapter<CharSequence>(this, R., quality);
		productQuality.setAdapter(adapter);
		
		///get device location x & y
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_product, menu);
		return true;
	}

}
