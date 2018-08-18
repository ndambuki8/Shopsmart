package com.example.ndambuki.shopsmart.Database;

import android.provider.BaseColumns;

public final class ColumnsContract {

    private ColumnsContract() {

    }

    public static class ScannedItemEntry implements BaseColumns{

        public static final String TABLE_SCANNED_ITEMS = "Scanned_items";
        public static final String COLUMN_ITEM_ID = "Item_id";
        public static final String COLUMN_ITEM_QTY = "item_qty";
        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_SCANNED_ITEMS_PRICE = "item_price";



        //public static final String[] ALL_KEYS = {COLUMN_ITEM_ID,COLUMN_SCANNED_ITEMS_PRICE};
        //public static final String COLUMN_DELETE = "Delete_Item";


        //public static final int COL_ITEM_ID = 0;
        //public static final int COL_SCANNED_ITEM = 1;
        // public static final int COL_DELETE = 2;


    }

    public static class BarcodesReference implements BaseColumns{

        public static final String TABLE_BARCODE = "barcode_table";
        public static final String COLUMN_BARCODE = "Item_id";
        public static final String COLUMN_ITEM_NAME = "Item_name";
        public static final String COLUMN_ITEM_PRICE = "item_price";
        public static final String COLUMN_QUANTITY = "item_quantity";



    }
}

