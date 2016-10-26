package com.izeni.startingsql.database.tables

import android.content.ContentValues
import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Crust(val crustType: String, val crustPrice: Int): BaseColumns {

    companion object {

        val TABLE_NAME = "crust"

        val TYPE = "type"
        val PRICE = "price"

        val SQL_CREATE_CRUST_TABLE = "CREATE TABLE $TABLE_NAME (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Crust.TYPE + " TEXT NOT NULL, " +
                Crust.PRICE + " INTEGER, " +
                Pizza.PIZZA_ID + " INTEGER, " +
                "FOREIGN KEY (${Pizza.PIZZA_ID}) REFERENCES ${Pizza.TABLE_NAME}(${BaseColumns._ID}));"

        val SQL_DELETE_CRUST_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"

    }

    fun contentValues(): ContentValues {

        var crustContentValues = ContentValues()

        crustContentValues.put(TYPE, crustType)
        crustContentValues.put(PRICE, crustPrice)

        return crustContentValues

    }

}