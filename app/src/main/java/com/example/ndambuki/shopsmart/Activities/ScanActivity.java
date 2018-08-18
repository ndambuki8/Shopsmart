package com.example.ndambuki.shopsmart.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ndambuki.shopsmart.Database.DatabaseHelper;
import com.example.ndambuki.shopsmart.R;
import com.example.ndambuki.shopsmart.Utils.Methods;

import java.io.Serializable;

public class ScanActivity extends AppCompatActivity implements Serializable {

    Button viewBarcodeScanList, openBarcodeScanner;
    DatabaseHelper myDb;
    public TextView tvAmount;
    Methods myMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        myMethods = new Methods(this);
        viewBarcodeScanList = (Button) findViewById(R.id.btnViewScanList);
        tvAmount = (TextView) findViewById(R.id.tvAmountRemaining);
        openBarcodeScanner = (Button) findViewById(R.id.btnOK);
        tvAmount.setText("Amount Remaining : " + myMethods.getAmount());
        viewBarcodeScanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScannedListActivity();

            }
        });

        openBarcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewBarcodeActivity();
            }
        });


        myDb = new DatabaseHelper(this);


    }

    private void openScannedListActivity() {
        startActivity(new Intent(this, ScanlistActivity.class));
    }

    private void openNewBarcodeActivity(){
        startActivity(new Intent(this, BarcodeActivity.class));
    }




}
