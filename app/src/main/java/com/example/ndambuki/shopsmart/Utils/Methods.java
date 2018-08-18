package com.example.ndambuki.shopsmart.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Methods {
    SharedPreferences sharedPreferences;
    Context context;
    public static final String MYPREFS ="myprefs";
    public static final String MYPREFS2 ="amount";

    public Methods(Context context) {
        this.context = context;
    }

    public void setAmountValue(String amountValue){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amount",amountValue);
        editor.apply();
    }

    public String getAmount(){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("amount","");
    }

    public void clearPreferences(){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public JSONArray getBarcode() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("barcode","6161101536790");
        jsonObject.put("itemName","Kasuku ex. book");
        jsonObject.put("itemPrice","65.00");
        jsonObject.put("itemQuantity","96 pgs");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("barcode","6161100045231");
        jsonObject1.put("itemName","AMARA lotion");
        jsonObject1.put("itemPrice","150.00");
        jsonObject1.put("itemQuantity","200 ml");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("barcode","4005900088215");
        jsonObject2.put("itemName","Nivea Deodorant");
        jsonObject2.put("itemPrice","250.00");
        jsonObject2.put("itemQuantity","150 ml");

        JSONObject jsonObject3 = new JSONObject();
        jsonObject2.put("barcode","6001087357050");
        jsonObject2.put("itemName","Vaseline lotion");
        jsonObject2.put("itemPrice","300.00");
        jsonObject2.put("itemQuantity","400 ml");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        jsonArray.put(jsonObject3);
        //Toast.makeText(context, "Barcodes Array", Toast.LENGTH_SHORT).show();
        return jsonArray;
    }

    public void calculateTotalAmount(String amountValue){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("total",amountValue);
        editor.apply();
    }

    public String getTotalAmount(){
        sharedPreferences = context.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("total","");
    }

    public void setlastAmounttotal(String amountValue){
        sharedPreferences = context.getSharedPreferences(MYPREFS2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_total",amountValue);
        editor.apply();
    }

    public String getLastAmountTotal(){
        sharedPreferences = context.getSharedPreferences(MYPREFS2, Context.MODE_PRIVATE);
        return sharedPreferences.getString("last_total","");
    }

//    public void clearPreferences2(){
//        sharedPreferences = context.getSharedPreferences(MYPREFS2, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//    }
    public void clearPrefs(){
        sharedPreferences = context.getSharedPreferences(MYPREFS2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}

