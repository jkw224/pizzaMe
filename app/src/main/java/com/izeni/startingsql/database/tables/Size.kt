package com.izeni.startingsql.database.tables

import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Size(): BaseColumns {

    companion object {

        val TABLE_NAME = "size"

        val NAME = "name"
        val PRICE = "price"

        val SQL_CREATE_SIZE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                Size.NAME + " TEXT, " +
                Size.PRICE + " REAL)"

        val SQL_DELETE_SIZE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    }

}