package com.btrading.main.wish;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.btrading.main.MainBaseActivity;
import com.example.btrading.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WishlistActivity extends MainBaseActivity {

	ActionMode mActionMode;
	ListView lv_wish;
	ArrayAdapter<CharSequence> adapter;
	String[] wish_items = {"ABC Duck","TV controller","Water Bottle"};
	String item_selected;
	
	public WishlistActivity(){
		super(R.string.title_activity_wishlist);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_wish);
		
		lv_wish = (ListView) findViewById(R.id.lv_wish);
		adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, wish_items);
		lv_wish.setAdapter(adapter);

		lv_wish.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Open product item
			}
			
		});
		lv_wish.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//mMode = startActionMode(mActionModeCallback);

			        if (mActionMode != null) {
			            return false;
			        }

			        // Start the CAB using the ActionMode.Callback defined above
			        arg1.setSelected(true);
			        item_selected = wish_items[arg2];
			        mActionMode = startActionMode(mActionModeCallback);
			        
			        int doneButtonId = Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android");
			        View doneButton = findViewById(doneButtonId);
			        doneButton.setOnClickListener(new View.OnClickListener() {

			            @Override
			            public void onClick(View v) {
			                // do whatever you want 
			                // in android source code it's calling mMode.finish();
			            	mActionMode.finish();
			            	Toast.makeText(getBaseContext(), "hihi", Toast.LENGTH_SHORT).show();
			            }
			        });
			        

				return true;
			}
			
		}); 
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
			mode.setSubtitle(item_selected);
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_add_wishlist:
			Intent intent = new Intent();
			intent.setClass(getBaseContext(), AddWishlistActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.wishlist, menu);
		return true;
	}
	
	
}
