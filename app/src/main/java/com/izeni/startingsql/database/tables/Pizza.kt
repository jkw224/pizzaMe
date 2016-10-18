package com.izeni.startingsql.database.tables

import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Pizza (): BaseColumns {

    companion object {

        val TABLE_NAME: String = "pizza"

        val NAME: String = "name"
        val PRICE: String = "price"
        val TOPPINGS_ID: String = "toppings_id"
        val SIZES_ID: String = "sizes_id"
        val CRUSTS_ID: String = "crusts_id"

        val SQL_CREATE_PIZZA_TABLE = "CREATE TABLE $TABLE_NAME (" +
                Pizza.NAME + " INTEGER PRIMARY KEY, " +
                Pizza.PRICE + " REAL NOT NULL, " +
                "FOREIGN KEY($TOPPINGS_ID) REFERENCES ${Toppings.TABLE_NAME}(${BaseColumns._ID}), " +
                "FOREIGN KEY($SIZES_ID) REFERENCES ${Size.TABLE_NAME}(${BaseColumns._ID}), " +
                "FOREIGN KEY($CRUSTS_ID) REFERENCES ${Crust.TABLE_NAME}(${BaseColumns._ID})"

        val SQL_DELETE_PIZZA_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    }


}