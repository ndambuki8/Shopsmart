package com.example.ndambuki.shopsmart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ndambuki.shopsmart.Database.ColumnsContract;
import com.example.ndambuki.shopsmart.Database.DatabaseHelper;
import com.example.ndambuki.shopsmart.R;
import com.example.ndambuki.shopsmart.R;
import com.example.ndambuki.shopsmart.Utils.BarcodeQueryResponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ndambuki on 7/8/18.
 */

public class ScannedListAdapter extends BaseAdapter{

    DatabaseHelper myDb;

    //private SQLiteDatabase db;
    Context mContext;
    ArrayList<BarcodeQueryResponse> data;//modify here


    public ScannedListAdapter(Context context, ArrayList<BarcodeQueryResponse> data) //modify here
    {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();// # of items in your arraylist
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);// get the actual movie
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.scan_list_note, parent, false);//modify here
            viewHolder = new ViewHolder();
            viewHolder.tvScannedItemName = (TextView) convertView.findViewById(R.id.scan_Item_Name);//modify here
            viewHolder.tvScannedItemQuantity = (TextView) convertView.findViewById(R.id.scan_Item_Quantity);//modify here
            viewHolder.tvScannedItemPrice = (TextView) convertView.findViewById(R.id.scan_Item_Price);//modify here
            viewHolder.tvScannedItemId = (TextView) convertView.findViewById(R.id.scan_Item_Id);


            convertView.setTag(viewHolder);
            convertView.setTag(R.id.scan_Item_Name, viewHolder.tvScannedItemName);
            convertView.setTag(R.id.scan_Item_Name, viewHolder.tvScannedItemQuantity);
            convertView.setTag(R.id.scan_Item_Price, viewHolder.tvScannedItemPrice);
            convertView.setTag(R.id.scan_Item_Id, viewHolder.tvScannedItemId);




        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BarcodeQueryResponse b = data.get(position);//modify here
        viewHolder.tvScannedItemPrice.setText(b.getItemPrice());//modify here
        viewHolder.tvScannedItemId.setText(b.getBarcodeName());//modify here
        viewHolder.tvScannedItemName.setText(b.getItemName());//modify here
        viewHolder.tvScannedItemQuantity.setText(b.getItemQuantity());//modify here

        return convertView;

    }

    static class ViewHolder {
       // TextView tvScannedItemName;//modify here
        TextView tvScannedItemPrice;//modify here
        TextView tvScannedItemId;//modify here
        TextView tvScannedItemName;//modify here
        TextView tvScannedItemQuantity;



    }




}

