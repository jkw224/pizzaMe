package com.izeni.startingsql.database.tables

import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Toppings( val name: String, val type: Int ): BaseColumns {

    companion object {

        val TABLE_NAME = "toppings"

        val NAME = "name"
        val PRICE = "price"
        val TYPE = "type"

        val SQL_CREATE_TOPPINGS_TABLE = "CREATE TABLE $TABLE_NAME (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Toppings.NAME + " TEXT NOT NULL, " +
                Toppings.PRICE + " INTEGER, " +
                Toppings.TYPE + " INTEGER NOT NULL, " +
                Pizza.PIZZA_ID + " INTEGER, " +
                "FOREIGN KEY (${Pizza.PIZZA_ID}) REFERENCES ${Pizza.TABLE_NAME}(${BaseColumns._ID}));"


        val SQL_DELETE_TOPPINGS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"

        val SAUCE = 0
        val MEAT = 1
        val SECONDARY = 2
        val CUSTOM_PIZZA = 3

    }

}