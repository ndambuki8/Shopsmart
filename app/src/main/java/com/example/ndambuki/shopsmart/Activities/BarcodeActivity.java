package com.example.ndambuki.shopsmart.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;

import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ndambuki.shopsmart.Database.DatabaseHelper;
import com.example.ndambuki.shopsmart.R;
import com.example.ndambuki.shopsmart.Utils.BarcodeQueryResponse;
import com.example.ndambuki.shopsmart.Utils.Methods;
import com.google.zxing.Result;

import java.io.Serializable;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler,Serializable {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    DatabaseHelper myDb;
    public static Methods myMethods;
   // static int totalAmount;
    public static Double totalAmount;
    static Double x;
    //static int x;
    static Double totalBill = 0.0;
    public static AlertDialog.Builder builder;
    TextView textView;
   static BarcodeQueryResponse itemDetais;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder = new AlertDialog.Builder(this);

        itemDetais = new BarcodeQueryResponse();

        myDb = new DatabaseHelper(this);
        myMethods = new Methods(this);
        builder.setTitle("Scan Result" + "\nAmount Remaining : " + Double.parseDouble(myMethods.getAmount()));

        textView = (TextView) findViewById(R.id.tvTotalDisplay);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Scan an Item's Barcode!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }


    }


    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(BarcodeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void handleResult(final Result result) {
        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());

       // final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result" + "\nAmount Remaining : " + myMethods.getAmount());
        builder.setNeutralButton("View Scanned Items", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //scannerView.resumeCameraPreview(BarcodeActivity.this);
                openScannedListActivity();
            }
        });
        builder.setNegativeButton("RESCAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(BarcodeActivity.this);
            }
        });

        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //BarcodeQueryResponse itemDetais = myDb.getBarcodeDetail(myResult.trim());
                try {
                    itemDetais = myDb.getBarcodeDetail(myResult.trim());

                    String barcodeName = String.valueOf(BarcodeActivity.itemDetais.getBarcodeName());

                    if ((!myResult.trim().isEmpty()) && (totalAmount >= Double.parseDouble(itemDetais.getItemPrice()))) {
                        //final long result = myDb.insertScannedItem(myResult.toString().trim(),(myResult.toString().trim().replaceAll("[^0.0-9.0]+", "")));
                        final long result = myDb.insertScannedItem(itemDetais);

                        if (result != -1) {
                            Toast.makeText(BarcodeActivity.this, "Scanned Successfully", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(BarcodeActivity.this, "Value Not Saved", Toast.LENGTH_SHORT).show();
                        }
                        Double itemPrice = Double.parseDouble(itemDetais.getItemPrice());
                        if (totalAmount > 0 && totalAmount >= itemPrice) {
                            totalAmount = totalAmount - itemPrice;
                            String s = String.valueOf(totalAmount);

                            myMethods.setAmountValue(s);
                            //builder.setTitle("Amount Remaining : " +s.toString().replaceAll("[^0-9]+", ""));
//                            builder.setTitle("Amount Remaining : "+s);
                            builder.setTitle("Amount Remaining : " + myMethods.getAmount());

                            // builder.setMessage("Scan Result" + "\nAmount Remaining : " + s.toString().replaceAll("[^0-9]+", ""));
                            //System.out.println(totalAmount);

                        }

                        if (!itemPrice.toString().trim().isEmpty()) {
                            myMethods.calculateTotalAmount(itemPrice.toString().trim());
                            //totalBill = itemPrice;

                            if (itemPrice > 0 && itemPrice != null) {
                                totalBill = totalBill + itemPrice;
                                String sh = String.valueOf(totalBill);
                                //textView.setText("Total: "+sh);
                                // TODO: 8/3/18 Try if works
                              /*  Intent myIntent = new Intent(BarcodeActivity.this,ScanlistActivity.class);
                                myIntent.putExtra("display","Total: "+sh);
                                startActivity(myIntent);*/
                                myMethods.setlastAmounttotal(sh);

                                //scannerView.resumeCameraPreview(BarcodeActivity.this);

                            }
                        }


//                        if (itemPrice>0 && itemPrice != null){
//                            totalBill = totalBill + itemPrice;
//                            String sh = String.valueOf(totalBill);
//                            //textView.setText("Total: "+sh);
//
//                            Intent myIntent = new Intent(BarcodeActivity.this,ScanlistActivity.class);
//                            myIntent.putExtra("display","Total: "+sh);
//                            startActivity(myIntent);
//                            //scannerView.resumeCameraPreview(BarcodeActivity.this);
//
//                        }


                        scannerView.resumeCameraPreview(BarcodeActivity.this);
                    } else if (myResult.toString().trim().isEmpty()) {

                        Toast.makeText(BarcodeActivity.this, "Scan an Item's Barcode First", Toast.LENGTH_SHORT).show();
                        scannerView.resumeCameraPreview(BarcodeActivity.this);

                    }
//                    else {
//                        Toast.makeText(BarcodeActivity.this, "You DO NOT Have Enough Cash!", Toast.LENGTH_SHORT).show();
//                        scannerView.resumeCameraPreview(BarcodeActivity.this);
//                    }

                    else if ((!myResult.trim().isEmpty()) && (totalAmount < Double.parseDouble(itemDetais.getItemPrice()))) {
                        Toast.makeText(BarcodeActivity.this, "You DO NOT Have Enough Cash!", Toast.LENGTH_SHORT).show();
                        scannerView.resumeCameraPreview(BarcodeActivity.this);
                    } else {
                        Toast.makeText(BarcodeActivity.this, "This Barcode Does Not exist!", Toast.LENGTH_SHORT).show();
                        scannerView.resumeCameraPreview(BarcodeActivity.this);

                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(BarcodeActivity.this, "This Barcode Does Not exist!", Toast.LENGTH_SHORT).show();
                    scannerView.resumeCameraPreview(BarcodeActivity.this);

                }

            }
        });

            builder.setMessage(result.getText());
            AlertDialog alert1 = builder.create();
            alert1.show();
        /*builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/
    }
    private void openScannedListActivity() {
        //startActivity(new Intent(this, CsvActivity.class));
        startActivity(new Intent(this, ScanlistActivity.class));
    }

//    public void calculateTotalAmount(Double amt){
//
//        if (!amt.toString().trim().isEmpty()){
//            myMethods.calculateTotalAmount(amt.toString().trim());
//            //totalBill = itemPrice;
//
//            if (amt>0 && amt != null){
//                totalBill = totalBill + amt;
//                String sh = String.valueOf(totalBill);
//                //textView.setText("Total: "+sh);
//
//                Intent myIntent = new Intent(BarcodeActivity.this,ScanlistActivity.class);
//                myIntent.putExtra("display","Total: "+sh);
//                startActivity(myIntent);
//                //scannerView.resumeCameraPreview(BarcodeActivity.this);
//
//            }
//        }
//
//    }




}
