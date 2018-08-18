package com.example.ndambuki.shopsmart.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.ndambuki.shopsmart.Utils.BarcodeQueryResponse;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 26;
    private static final String DATABASE_NAME = "contactsManager";
    //private SQLiteDatabase db;

    String SQL_CREATE_SCANNED_ITEM_TABLE = "CREATE TABLE " + ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS + " ("
            + ColumnsContract.ScannedItemEntry.COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ColumnsContract.ScannedItemEntry.COLUMN_ITEM_NAME + " TEXT, "+ ColumnsContract.ScannedItemEntry.COLUMN_ITEM_QTY + " TEXT, " + ColumnsContract.ScannedItemEntry.COLUMN_SCANNED_ITEMS_PRICE + " TEXT NOT NULL " + ")";
    String SQL_DROP_SCANNED_ITEM_TABLE = "DROP TABLE IF EXISTS "+ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS;

    String SQL_CREATE_BARCODES_TABLE = "CREATE TABLE " + ColumnsContract.BarcodesReference.TABLE_BARCODE + " ("
            + ColumnsContract.BarcodesReference.COLUMN_BARCODE + " TEXT, " + ColumnsContract.BarcodesReference.COLUMN_ITEM_NAME + " TEXT, "
            + ColumnsContract.BarcodesReference.COLUMN_ITEM_PRICE + " TEXT, " +
            ColumnsContract.BarcodesReference.COLUMN_QUANTITY + " TEXT NOT NULL " + ")";
    String SQL_DROP_BARCODES_TABLE = "DROP TABLE IF EXISTS "+ColumnsContract.BarcodesReference.TABLE_BARCODE;

    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SCANNED_ITEM_TABLE);
        db.execSQL(SQL_CREATE_BARCODES_TABLE);
        Log.e("onCreate: ", "Payment CREATED");
        Log.e("onCreate: ", "Barcodes CREATED");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_SCANNED_ITEM_TABLE);
        db.execSQL(SQL_DROP_BARCODES_TABLE);
        onCreate(db);

    }

    public long insertScannedItem(BarcodeQueryResponse response){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnsContract.ScannedItemEntry.COLUMN_ITEM_QTY, response.getItemQuantity());
        values.put(ColumnsContract.ScannedItemEntry.COLUMN_ITEM_NAME, response.getItemName());
        values.put(ColumnsContract.ScannedItemEntry.COLUMN_SCANNED_ITEMS_PRICE, response.getItemPrice());
        long result = db.insert(ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS,null,values);
       // Log.e("saved_item_name", name);
        Log.e("savedPayments", ""+result);
        db.close();
        return result;

    }

    public void insertBarcodesItem(JSONArray barcodeArray) throws JSONException{
        SQLiteDatabase db = this.getWritableDatabase();

       for (int i = 0; i<barcodeArray.length();i++) {
           ContentValues values = new ContentValues();
           values.put(ColumnsContract.BarcodesReference.COLUMN_BARCODE, barcodeArray.getJSONObject(i).getString("barcode"));
           values.put(ColumnsContract.BarcodesReference.COLUMN_ITEM_NAME, barcodeArray.getJSONObject(i).getString("itemName"));
           values.put(ColumnsContract.BarcodesReference.COLUMN_ITEM_PRICE, barcodeArray.getJSONObject(i).getString("itemPrice"));
           values.put(ColumnsContract.BarcodesReference.COLUMN_QUANTITY, barcodeArray.getJSONObject(i).getString("itemQuantity"));
           long result = db.insert(ColumnsContract.BarcodesReference.TABLE_BARCODE, null, values);
           Log.e("Saved__Items", "" + result);
       }
       db.close();
    }

    public ArrayList<BarcodeQueryResponse> getAllAmounts(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<BarcodeQueryResponse> data = new ArrayList<>();
        String query = "SELECT * FROM "+ ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            BarcodeQueryResponse barcodeQueryResponse = new BarcodeQueryResponse();
            barcodeQueryResponse.setBarcodeName(cursor.getString(cursor.getColumnIndex(ColumnsContract.ScannedItemEntry.COLUMN_ITEM_ID)));
            barcodeQueryResponse.setItemName(cursor.getString(cursor.getColumnIndex(ColumnsContract.ScannedItemEntry.COLUMN_ITEM_NAME)));
            barcodeQueryResponse.setItemPrice(cursor.getString(cursor.getColumnIndex(ColumnsContract.ScannedItemEntry.COLUMN_SCANNED_ITEMS_PRICE)));
            barcodeQueryResponse.setItemQuantity(cursor.getString(cursor.getColumnIndex(ColumnsContract.ScannedItemEntry.COLUMN_ITEM_QTY)));
            data.add(barcodeQueryResponse);
        }
        db.close();
        return data;
    }


    public BarcodeQueryResponse getBarcodeDetail(String barcodes){

        SQLiteDatabase db = this.getWritableDatabase();
        BarcodeQueryResponse result = new BarcodeQueryResponse();
       // String query = "SELECT * FROM "+ ColumnsContract.BarcodesReference.TABLE_BARCODE +" WHERE "+ColumnsContract.BarcodesReference.COLUMN_BARCODE + "='"+"1111111"+"'";
        String query = "SELECT * FROM "+ ColumnsContract.BarcodesReference.TABLE_BARCODE +" WHERE "+ColumnsContract.BarcodesReference.COLUMN_BARCODE + " = '"+barcodes+"'";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            result.setBarcodeName(cursor.getString(cursor.getColumnIndex(ColumnsContract.BarcodesReference.COLUMN_BARCODE)));
            result.setItemName(cursor.getString(cursor.getColumnIndex(ColumnsContract.BarcodesReference.COLUMN_ITEM_NAME)));
            result.setItemPrice(cursor.getString(cursor.getColumnIndex(ColumnsContract.BarcodesReference.COLUMN_ITEM_PRICE)));
            result.setItemQuantity(cursor.getString(cursor.getColumnIndex(ColumnsContract.BarcodesReference.COLUMN_QUANTITY)));
        }
        db.close();
        return result;
    }

    public void deleteAScannedEntry(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS + " WHERE " +
                ColumnsContract.ScannedItemEntry.COLUMN_ITEM_ID + " = '" + id + "'";
        db.execSQL(query);

    }


    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS,null,null);
        db.execSQL("DELETE FROM "+ ColumnsContract.ScannedItemEntry.TABLE_SCANNED_ITEMS);
        db.close();
    }



}
