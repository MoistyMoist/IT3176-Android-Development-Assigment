package com.btrading.main.products;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;

import com.example.btrading.R;
import com.btrading.httprequests.DeleteProductRequest;
import com.btrading.httprequests.RetrieveUserProductRequest;
import com.btrading.main.MainBaseActivity;
import com.btrading.models.Product;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


public class ProductActivity  extends MainBaseActivity{

	
	UserProductListAdapter adapter;
	ListView list;
	StaticObjects staticObjects;
	Context context;
	ActionMode mActionMode;
	ProgressDialog progress;
	Product productToDelete;
	
	public ProductActivity(){
		super(R.string.title_activity_product);
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_product);

		list = (ListView) findViewById(R.id.userProductList);
		staticObjects = new StaticObjects();
		context=getBaseContext();
		
		
		
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				StaticObjects.setSelectedProduct(StaticObjects.getUserProducts().get(arg2));
				Intent intent= new Intent();
				intent.setClass(getApplication(), UpdateProductActivity.class);
				startActivity(intent);
			}});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//mMode = startActionMode(mActionModeCallback);

			        if (mActionMode != null) {
			            return false;
			        }

			        // Start the CAB using the ActionMode.Callback defined above
			        arg1.setSelected(true);
			        productToDelete = StaticObjects.getUserProducts().get(arg2);
			        mActionMode = startActionMode(mActionModeCallback);
			        
			        int doneButtonId = Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android");
			        View doneButton = findViewById(doneButtonId);
			        doneButton.setOnClickListener(new View.OnClickListener() {

			            @Override
			            public void onClick(View v) {
			                // do whatever you want 
			                // in android source code it's calling mMode.finish();
			            	deleteProduct();
			            	mActionMode.finish();
			            	
			            }
			        });
			        
				return true;
			}
		}); 

		if(StaticObjects.getUserProducts()==null||StaticObjects.getUserProducts().size()==0)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
//				        RetrieveUserProductRequest retrieveUserProductRequest = new RetrieveUserProductRequest(StaticObjects.getCurrentUser());
					  	
					  	User user= new User();
					  	user.setUserID(1);
					  	RetrieveUserProductRequest retrieveUserProductRequest = new RetrieveUserProductRequest(user);
					  	
				        executor.execute(retrieveUserProductRequest);
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
				        if(StaticObjects.getUserProducts().size()==0||StaticObjects.getUserProducts()==null)
				        {
				        	Log.i("PRODUCT", "NO PRODUCT");
				        }
				        else
				        {
				        	adapter = new UserProductListAdapter(context, StaticObjects.getUserProducts());
						    list.setAdapter(adapter);
				        }
				        
				      }
				    });
				  }
				}).start();
		}
		else
		{
			Log.i("PRODUCT", "weird PRODUCT");
			adapter = new UserProductListAdapter(context, StaticObjects.getUserProducts());
		    list.setAdapter(adapter);
		}
	}

	
	
	
	
	
	
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onCreateActionMode(ActionMode mode, com.actionbarsherlock.view.Menu menu) {
			// TODO Auto-generated method stub
			//getSupportMenuInflater().inflate(R.menu.wishlist, (com.actionbarsherlock.view.Menu) menu);
            menu.add("Delete")
            .setIcon(R.drawable.ic_no)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			mode.setTitle("Confirm Delete?");
			//mode.setSubtitle(item_selected);
			return true;
		}
	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    	
	        switch (item.getItemId()) {
	            case R.drawable.ic_no:
	            	mode.finish(); // Action picked, so close the CAB
	                return true;
	            default:
	            	mode.finish(); // Action picked, so close the CAB
	                return false;   
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	        mActionMode = null;
	    }
	    
		@Override
		public boolean onPrepareActionMode(ActionMode mode,
				com.actionbarsherlock.view.Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	private void deleteProduct()
	{
		progress = ProgressDialog.show(this, "Deleting",
			    "please wait...", true);
		new Thread(new Runnable() {
			  @Override
			  public void run()
			  {
				  	ExecutorService executor = Executors.newFixedThreadPool(1);
				  	DeleteProductRequest deleteProductRequest = new DeleteProductRequest(productToDelete);
				  	
			        executor.execute(deleteProductRequest);
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
			    	progress.dismiss();
			        staticObjects= new StaticObjects();
			        Toast.makeText(getBaseContext(), "Product deleted", Toast.LENGTH_SHORT).show();
			        if(StaticObjects.getUserProducts().size()==0||StaticObjects.getUserProducts()==null)
			        {
			        	Log.i("PRODUCT", "NO PRODUCT");
			        }
			        else
			        {
			        	adapter = new UserProductListAdapter(context, StaticObjects.getUserProducts());
					    list.setAdapter(adapter);
			        }
			        
			      }
			    });
			  }
			}).start();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.add_product)
		{
			Intent intent= new Intent();
			intent.setClass(getApplication(), AddProductActivity.class);
			startActivity(intent);
		}
		if(item.getItemId()==R.id.refresh)
		{
			refresh();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.my_product, menu);
		return true;
	}
	
	public void refresh()
	{
		progress = ProgressDialog.show(this, "Searching",
			    "please wait...", true);
		new Thread(new Runnable() {
			  @Override
			  public void run()
			  {
				  	ExecutorService executor = Executors.newFixedThreadPool(1);
//			        RetrieveUserProductRequest retrieveUserProductRequest = new RetrieveUserProductRequest(StaticObjects.getCurrentUser());
				  	
				  	User user= new User();
				  	user.setUserID(1);
				  	RetrieveUserProductRequest retrieveUserProductRequest = new RetrieveUserProductRequest(user);
				  	
			        executor.execute(retrieveUserProductRequest);
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
			    	  progress.dismiss();
			        staticObjects= new StaticObjects();
			        if(StaticObjects.getUserProducts().size()==0||StaticObjects.getUserProducts()==null)
			        {
			        	Log.i("PRODUCT", "NO PRODUCT");
			        }
			        else
			        {
			        	adapter = new UserProductListAdapter(context, StaticObjects.getUserProducts());
					    list.setAdapter(adapter);
			        }
			        
			      }
			    });
			  }
			}).start();
	}

}
