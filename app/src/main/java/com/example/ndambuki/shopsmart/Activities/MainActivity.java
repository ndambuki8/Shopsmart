package com.example.ndambuki.shopsmart.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ndambuki.shopsmart.Database.DatabaseHelper;
import com.example.ndambuki.shopsmart.R;
import com.example.ndambuki.shopsmart.Utils.Methods;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private Button barcodeScanner;
    private Button shoppingList;
    private Button calculate;
    private DatabaseHelper databaseHelper;
    private Methods methods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        methods = new Methods(this);


        //databaseHelper.deleteAll();

        //methods.clearPrefs();

        try {

            databaseHelper.insertBarcodesItem(methods.getBarcode());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "No Barcodes Saved", Toast.LENGTH_SHORT).show();
        }
        setupUIViews();


        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewShopListActivity();

            }
        });


        barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewAmountActivity();

            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewCalcActivity();

            }
        });

    }

    private void setupUIViews(){

        barcodeScanner = (Button)findViewById(R.id.btnScanner);
        shoppingList = (Button)findViewById(R.id.btnShoplist);
        calculate = (Button)findViewById(R.id.btnCalc);

    }

    private void openNewShopListActivity(){

        startActivity(new Intent(this, ShopListActivity.class));
    }

    private void openNewCalcActivity(){
        startActivity(new Intent(this, CalcActivity.class));
    }


    private void openNewAmountActivity(){
        startActivity(new Intent(this, AmountActivity.class));
    }





}


