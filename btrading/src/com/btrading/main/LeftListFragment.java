package com.btrading.main;

import com.btrading.main.login.LoginActivity;
import com.btrading.main.products.ProductActivity;
import com.btrading.main.profile.ProfileActivity;
import com.btrading.main.wish.WishlistActivity;
import com.btrading.utils.StaticObjects;
import com.example.btrading.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LeftListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.add(new SampleItem("Home", android.R.drawable.ic_menu_myplaces,0));
		//1-6
		adapter.add(new SampleItem("Account", R.drawable.ic_left_menu_title,0)); //int 0 for title, custom set	
		adapter.add(new SampleItem("Profile", android.R.drawable.ic_menu_myplaces,0));
		adapter.add(new SampleItem("My Items", android.R.drawable.ic_menu_search,0));
		adapter.add(new SampleItem("Others", R.drawable.ic_left_menu_title,0)); //int 0 for title, custom set
		adapter.add(new SampleItem("Wish List", android.R.drawable.ic_menu_my_calendar,0));
		adapter.add(new SampleItem("Settings", android.R.drawable.ic_menu_preferences,0));
		adapter.add(new SampleItem("Logout", android.R.drawable.ic_lock_power_off,0));
		
		setListAdapter(adapter);
	}

	public void onListItemClick(ListView lv, View v, int position, long id) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		switch (position) {
			
		case 0: //customer Profile
			intent.setClass(getActivity(), MainActivity.class);
			startActivity(intent);
			break;
		
		case 2: //customer Profile
			intent.setClass(getActivity(), ProfileActivity.class);
			startActivity(intent);
			break;

		case 3: //My items
			intent.setClass(getActivity(), ProductActivity.class);
			startActivity(intent);
			break;

		case 5: //wish
			intent.setClass(getActivity(), WishlistActivity.class);
			startActivity(intent);
			break;

		case 6: //Settings
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
		case 7: //Logout
			StaticObjects.setStaticEmpty();
			Intent logIntent = new Intent();
			logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			logIntent.setClass(getActivity(), LoginActivity.class);
			startActivity(logIntent);
			break;
			
		}
		((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle();
	};
	
	private class SampleItem {
		public String tag;
		public int iconRes;
		public int count;
		public SampleItem(String tag, int iconRes, int count) {
			this.tag = tag; 
			this.iconRes = iconRes;
			this.count = count;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);
			
			if (getItem(position).iconRes == R.drawable.ic_left_menu_title) {
				convertView.setBackgroundColor(Color.BLACK);  
				title.setTextColor(Color.WHITE);
			}
			else{
				//convertView.setBackgroundColor(Color.TRANSPARENT); 
				convertView.setBackgroundColor(Color.WHITE); 
			}
			
			TextView count = (TextView) convertView.findViewById(R.id.row_count);
			count.setBackgroundColor(Color.GRAY);
			count.setText(Integer.toString(getItem(position).count));
			if (getItem(position).count==0){
				count.setVisibility(View.INVISIBLE);
			}
			

			return convertView;
		}

	}
}
