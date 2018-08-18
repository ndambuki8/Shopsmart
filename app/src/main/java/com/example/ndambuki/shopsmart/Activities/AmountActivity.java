package com.example.ndambuki.shopsmart.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ndambuki.shopsmart.R;

import com.example.ndambuki.shopsmart.Utils.BarcodeQueryResponse;
import com.example.ndambuki.shopsmart.Utils.Methods;

import java.io.Serializable;
import java.util.ArrayList;

public class AmountActivity extends AppCompatActivity implements Serializable {

    public Button submit, viewBarcodeScanList;
    public EditText etAmount;
    //DatabaseHelper myDb;
    Methods myMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        //myDb = new DatabaseHelper(this);
        myMethods = new Methods(this);

        viewBarcodeScanList = (Button) findViewById(R.id.btnViewScanList2);
        etAmount = (EditText)findViewById(R.id.etAmount);

        submit = (Button)findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etAmount.getText().toString().trim().isEmpty()){
                    myMethods.setAmountValue(etAmount.getText().toString().trim());
                    Toast.makeText(AmountActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    //BarcodeActivity.totalAmount = Integer.parseInt(etAmount.getText().toString().trim());
                    BarcodeActivity.totalAmount = Double.parseDouble(etAmount.getText().toString().trim());
                    openNewBarcodeActivity();
                } else {
                    Toast.makeText(AmountActivity.this, "Enter Amount to Proceed", Toast.LENGTH_SHORT).show();
                }


            }
        });

        viewBarcodeScanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScannedListActivity();

            }
        });

    }


    /*private void openNewScanActivity() {
        startActivity(new Intent(this, ScanActivity.class));
    }*/
    private void openNewBarcodeActivity() {
        startActivity(new Intent(this, BarcodeActivity.class));
    }

    private void openScannedListActivity() {

        startActivity(new Intent(this, ScanlistActivity.class));
    }

}
