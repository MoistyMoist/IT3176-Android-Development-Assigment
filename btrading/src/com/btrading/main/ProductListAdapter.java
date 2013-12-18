package com.btrading.main;

import java.util.ArrayList;

import com.btrading.models.Product;
import com.example.btrading.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductListAdapter extends ArrayAdapter<Product>{

	 private final Context context;
     private final ArrayList<Product> productList;

     public ProductListAdapter(Context context, ArrayList<Product> productList) {

         super(context, R.layout.custom_product_row, productList);

         this.context = context;
         this.productList = productList;
         
         if(productList==null)
         {
        	 
         }
     }

     
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

         // 1. Create inflater 
         LayoutInflater inflater = (LayoutInflater) context
             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

         // 2. Get rowView from inflater
         View rowView = inflater.inflate(R.layout.custom_product_row, parent, false);

         // 3. Get the two text view from the rowView
         TextView productName = (TextView) rowView.findViewById(R.id.productName);
         TextView productDescription = (TextView) rowView.findViewById(R.id.productDescription);
         SmartImageView productImage=(SmartImageView)rowView.findViewById(R.id.icon);
         
         // 4. Set the text for textView 
         productName.setText(productList.get(position).getName());
         productDescription.setText(productList.get(position).getDescription());
         productImage.setImageUrl(productList.get(position).getImageURL());
         // 5. retrn rowView
         return rowView;
     }

}
