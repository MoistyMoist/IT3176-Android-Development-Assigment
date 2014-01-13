package com.btrading.main.wish;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.btrading.httprequests.CreateUserRequest;
import com.btrading.httprequests.CreateWishRequest;
import com.btrading.httprequests.RetrieveAllProductRequest;
import com.btrading.httprequests.RetrieveUserRequest;
import com.btrading.httprequests.RetrieveWishRequest;
import com.btrading.main.MainBaseActivity;
import com.btrading.main.ProductListAdapter;
import com.btrading.main.LeftListFragment.SampleAdapter;
import com.btrading.main.login.LoginActivity;
import com.btrading.main.login.RegisterActivity;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WishlistActivity extends MainBaseActivity {

	ActionMode mActionMode;
	ListView lv_wish;
	ArrayAdapter<WishItem> adapter;
	String[] wish_items = {"ABC Duck","TV controller","Water Bottle"};
	String item_selected;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;
	String available = "Unavailable";
	
	public WishlistActivity(){
		super(R.string.title_activity_wishlist);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_wish);
		
		lv_wish = (ListView) findViewById(R.id.lv_wish);
		
		//SampleAdapter adapter = new SampleAdapter(this);
		//for (int i=0; i<wish_items.length; i++){
		//	adapter.add(new WishItem(wish_items[i], "Unavailable"));
		//}
		//lv_wish.setAdapter(adapter);
		getWishlist();
		
		//adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, wish_items);
		//lv_wish.setAdapter(adapter);
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
		lv_wish.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Open product item
				TextView tv_wish = (TextView) arg1.findViewById(R.id.row_wish_title);
				checkWishlist(tv_wish.getText().toString());
				TextView tv_status = (TextView) arg1.findViewById(R.id.row_wish_available);
				tv_status.setText("Pending");
				tv_status.setTextColor(Color.GRAY);
				new RefreshAvailable().execute(Integer.toString(arg2));
			}
			
		});
	}
	
	private class WishItem {
		public String name;
		public String status;
		public WishItem(String name, String status) {
			this.name = name;
			this.status = status;
		}
	}

	public class SampleAdapter extends ArrayAdapter<WishItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
			
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_wish_row, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.row_wish_title);
			title.setText(getItem(position).name);
			TextView count = (TextView) convertView.findViewById(R.id.row_wish_available);
			count.setText(getItem(position).status);	

			return convertView;
		}

	}
	
	private class RefreshAvailable extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
        	String position = params[0];
                for(int i=0;i<3;i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return position;
        }        

        @Override
        protected void onPostExecute(String result) {   
            ListView lv_list = (ListView) findViewById(R.id.lv_wish);
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
            //lv_list.getChildAt(Integer.parseInt(result)).setBackgroundColor(Color.RED);
			TextView tv_status = (TextView) lv_list.getChildAt(Integer.parseInt(result)).findViewById(R.id.row_wish_available);
			tv_status.setText(available);
			if (available.equalsIgnoreCase("available")){
				tv_status.setTextColor(Color.argb(255, 34, 139, 34));
			}
			else if (available.equalsIgnoreCase("unavailable")){
				tv_status.setTextColor(Color.RED);
			}
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
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
			showDialog();
			/*
			Intent intent = new Intent();
			intent.setClass(getBaseContext(), AddWishlistActivity.class);
			startActivity(intent); */
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.wishlist, menu);
		return true;
	}

public void getWishlist(){
		
	if(StaticObjects.getUserWishlist()==null||StaticObjects.getUserWishlist().size()==0)
	{
		progress = ProgressDialog.show(this, "Getting your wishes","please wait...", true);
		RetrieveWishRequest retrieveWishRequest = new RetrieveWishRequest();
		 //UploadImageRequest upload= new UploadImageRequest();
		 new BackgroundTask().execute( retrieveWishRequest,null);
	}
	else
	{
		Log.i("WISH", "weird WISH");
		adapter = new SampleAdapter(context);
		for (int i=0; i<StaticObjects.getUserWishlist().size(); i++){
			adapter.add(new WishItem(StaticObjects.getUserWishlist().get(i).getName(), StaticObjects.getUserWishlist().get(i).getStatus()));
		}
	    lv_wish.setAdapter(adapter);
	}
}

public void checkWishlist(String wishName){
	for(int i=0; i<StaticObjects.getAllProducts().size(); i++){
		if (wishName.equalsIgnoreCase(StaticObjects.getAllProducts().get(i).getName())){
			available = "Available";
		}
	}
}


public void showDialog() {
    //DialogFragment newFragment = MyAlertDialogFragment.newInstance(R.string.dialog_add_wishlist);
	//DialogFragment newFragment = new DialogFragment();
	Intent intent = new Intent();
	intent.setClass(getBaseContext(), AddWishlistActivity.class);
	startActivity(intent);
	
	//DialogFragment newFragment = new MyAlertDialogFragment();
    //newFragment.show(getSupportFragmentManager(), "dialog");
}

public void doPositiveClick() {


  
    Log.i("FragmentAlertDialog", "Positive click!");
}



public static void doNegativeClick() {
    // Do stuff here.
    Log.i("FragmentAlertDialog", "Negative click!");
}


private void addWish() {


}
	

@SuppressLint("ValidFragment")
public class MyAlertDialogFragment extends SherlockDialogFragment {

	public MyAlertDialogFragment(){}
	
    public MyAlertDialogFragment newInstance(int title) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        
     // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_1_navigation_accept)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Add",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //((FragmentAlertDialogSupport)getActivity()).doPositiveClick();
                        	doPositiveClick();
                        }
                    }
                )
                .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //((FragmentAlertDialogSupport)getActivity()).doNegativeClick();
                        	doNegativeClick();
                        }
                    }
                )
                .create();
    }
}

private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
    
	@Override
	protected void onPostExecute(Long result) {
		
		super.onPostExecute(result);
		if(progress!=null)
			progress.dismiss();
        staticObjects= new StaticObjects();
		adapter = new SampleAdapter(context);
		if(StaticObjects.getUserWishlist()==null||StaticObjects.getUserWishlist().size()==0){}
		else{
				for (int i = 0; i < StaticObjects.getUserWishlist().size(); i++) {
					adapter.add(new WishItem(StaticObjects.getUserWishlist()
							.get(i).getName(), StaticObjects.getUserWishlist()
							.get(i).getStatus()));
				}
		}
	    lv_wish.setAdapter(adapter);
		
	}

	@Override
	protected void onPreExecute() {
		Toast.makeText(
				context, "Refreshing..", Toast.LENGTH_SHORT).show();
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


