package com.izeni.startingsql.database.tables

import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Toppings: BaseColumns {

    companion object {

        val TABLE_NAME = "toppings"

        val NAME = "name"
        val PRICE = "price"

        val SQL_CREATE_TOPPINGS_TABLE = "CREATE TABLE $TABLE_NAME (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                Toppings.NAME + " TEXT NOT NULL, " +
                Toppings.PRICE + " REAL NOT NULL) "

        val SQL_DELETE_TOPPINGS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    }

}