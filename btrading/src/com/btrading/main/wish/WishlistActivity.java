package com.btrading.main.wish;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;
import com.btrading.main.MainBaseActivity;
import com.example.btrading.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

public class WishlistActivity extends MainBaseActivity {

	ActionMode mMode;
	ListView lv_wish;
	ArrayAdapter<CharSequence> adapter;
	String[] wish_items = {"ABC Duck","TV controller","Water Bottle"};
	
	public WishlistActivity(){
		super(R.string.title_activity_wishlist);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_wish);
		
		lv_wish = (ListView) findViewById(R.id.lv_wish);
		adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, wish_items);
		lv_wish.setAdapter(adapter);
		/*
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
				mMode = startActionMode(new AnActionModeOfEpicProportions());
				Toast.makeText(getBaseContext(), "hihi", Toast.LENGTH_SHORT).show();
				return true;
			}
			
		}); */
		
        ((Button)findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMode = startActionMode(new AnActionModeOfEpicProportions());
            }
        });
	}
	
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
	
    private final class AnActionModeOfEpicProportions implements ActionMode.Callback {
        
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //Used to put dark icons on light action bar
            //boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;

            menu.add("Save")
                .setIcon(R.drawable.ic_action_github)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Toast.makeText(WishlistActivity.this, "Got click: " + item, Toast.LENGTH_SHORT).show();
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

		@Override
		public boolean onCreateActionMode(ActionMode mode,
				com.actionbarsherlock.view.Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode,
				com.actionbarsherlock.view.Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}
    }
	
}
