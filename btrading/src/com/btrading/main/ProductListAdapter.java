package com.btrading.main;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProductListAdapter extends BaseAdapter{

	 private Activity activity;
     private ArrayList data;
     private static LayoutInflater inflater=null;
     public Resources res;
     
     int i=0;
	
     
     /*************  CustomAdapter Constructor *****************/
     public ProductListAdapter(Activity a, ArrayList d,Resources resLocal) {
          
            /********** Take passed values **********/
             activity = a;
             data=d;
             res = resLocal;
          
             /***********  Layout inflator to call external xml layout () ***********/
              inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          
     }
     
	
	@Override
	public int getCount() {
		if(data.size()<=0)
            return 1;
        return data.size();
	}

	@Override
	public Object getItem(int position) {
		 return position;
	}

	@Override
	public long getItemId(int position) {
		 return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
