package com.izeni.startingsql.database.tables

import android.content.ContentValues
import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Size(val crustSizeSelected: String, val crustPrice: Int): BaseColumns {

    companion object {

        val TABLE_NAME = "size"

        val SIZE = "size"
        val PRICE = "price"

        val SQL_CREATE_SIZE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Size.SIZE + " TEXT NOT NULL, " +
                Size.PRICE + " INTEGER, " +
                Pizza.PIZZA_ID + " INTEGER, " +
                "FOREIGN KEY (${Pizza.PIZZA_ID}) REFERENCES ${Pizza.TABLE_NAME}(${BaseColumns._ID}));"

        val SQL_DELETE_SIZE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"

    }

    fun contentValues(): ContentValues {

        var sizeContentValues = ContentValues()

        sizeContentValues.put(Size.SIZE, crustSizeSelected)
        sizeContentValues.put(Size.PRICE, crustPrice)

        return sizeContentValues

    }

}