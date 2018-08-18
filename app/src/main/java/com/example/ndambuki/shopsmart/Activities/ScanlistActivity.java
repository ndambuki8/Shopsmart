package com.example.ndambuki.shopsmart.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ndambuki.shopsmart.Adapter.ScannedListAdapter;
import com.example.ndambuki.shopsmart.Database.ColumnsContract;
import com.example.ndambuki.shopsmart.Database.DatabaseHelper;
import com.example.ndambuki.shopsmart.R;
import com.example.ndambuki.shopsmart.Utils.BarcodeQueryResponse;
import com.example.ndambuki.shopsmart.Utils.Methods;

import java.io.Serializable;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.ndambuki.shopsmart.Activities.BarcodeActivity.totalBill;

public class ScanlistActivity extends AppCompatActivity implements Serializable {

    public ListView scannedList;
    DatabaseHelper myDb;
    ScannedListAdapter myAdapter;
    Context context;
    FloatingActionButton fab;
    TextView textView;
    Methods myMethods;
    Button button;


    private ZXingScannerView scannerView;
    ArrayList<BarcodeQueryResponse> datalist;

    BarcodeActivity barcodeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanlist);
        myDb = new DatabaseHelper(this);
        datalist = myDb.getAllAmounts();
        myAdapter = new ScannedListAdapter(this,datalist);
        scannedList = (ListView)findViewById(R.id.scan_list);
        button = (Button)findViewById(R.id.btnCloseApp);
        textView = (TextView) findViewById(R.id.tvTotalDisplay);
        scannedList.setAdapter(myAdapter);
        myMethods = new Methods(this);

        barcodeActivity = new BarcodeActivity();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myMethods.clearPreferences2();

                closeApp();
               // Toast.makeText(ScanlistActivity.this, "Thank you for using Shopping Tracker, Goodbye", Toast.LENGTH_SHORT).show();


            }
        });

        //textView.setText("Total: " +myMethods.getTotalAmount());



        //textView.setText("Total: 0 ");

     /*   final Intent intent = getIntent();
        String disp = intent.getStringExtra("display");*/
        textView.setText("Total: "+myMethods.getLastAmountTotal());

            deleteItem();

        fab = (FloatingActionButton) findViewById(R.id.fab_Mpesa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent simToolKitLaunchIntent = view.getContext().getPackageManager().getLaunchIntentForPackage("com.android.stk");
                if(simToolKitLaunchIntent != null) {
                    startActivity(simToolKitLaunchIntent);
                }

           }
        });



    }
    public void deleteItem(){
        scannedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    myDb.deleteAScannedEntry(Integer.parseInt(datalist.get(position).getBarcodeName()));
                    myAdapter = new ScannedListAdapter(ScanlistActivity.this, myDb.getAllAmounts());

                    autoSubtract();
                    scannedList.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                    Toast.makeText(ScanlistActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();


                    return false;
                }



        });

            }

            public void autoSubtract(){
                Double itemPrice = Double.parseDouble(BarcodeActivity.itemDetais.getItemPrice());

                Double  x = Double.parseDouble(myMethods.getLastAmountTotal());


                if(x >0 && x >= itemPrice ) {
                    x = x - itemPrice;
                    barcodeActivity.totalBill = x;
                    String shua = String.valueOf(x);
                    myMethods.setlastAmounttotal(shua);
                    //System.out.println("Hellos" + sh);

                    textView.setText("Total: " + myMethods.getLastAmountTotal());

//                    y = y + itemPrice;
//                    String str = String.valueOf(y);
////                    myMethods.setAmountValue(str);
////                   barcodeActivity.builder.setTitle("Amount Remaining : "+myMethods.getAmount());
//                    barcodeActivity.builder.setTitle("Amount Remaining : "+str);

                }

               Double y = Double.parseDouble(myMethods.getAmount());


                if (y >=0 ) {

                    y = y + itemPrice;
                    barcodeActivity.totalAmount = y;
                    String str = String.valueOf(y);
                    myMethods.setAmountValue(str);

                   barcodeActivity.builder.setTitle("Amount Remaining : "+Double.parseDouble(myMethods.getAmount()));
                    //barcodeActivity.builder.setTitle("Amount Remaining : "+str);


                }

            }

            public void closeApp(){
                if (myAdapter == null){
                    finish();

                }else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                            .setTitle("Leave?")
                            .setMessage("You are about close a Shopping Tracker Session" + ", are you sure?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    myMethods.clearPrefs();
                                    myDb.deleteAll();
                                    Toast.makeText(ScanlistActivity.this, "Thank you for using Shopping Tracker, Goodbye", Toast.LENGTH_SHORT).show();
                                    //Code for launching out of app

                                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(homeIntent);

                                   // finish();
                                }
                            })
                            .setNegativeButton("NO", null)
                            .setCancelable(false);

                    dialog.show();
                }


            }



}
