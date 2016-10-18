package com.izeni.startingsql.database.tables

import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Crust : BaseColumns {

    companion object {

        val TABLE_NAME = "crust"

        val NAME = "name"
        val PRICE = "price"

        val SQL_CREATE_CRUST_TABLE = "CREATE TABLE $TABLE_NAME (" +
                BaseColumns._ID + "INTEGER PRIMARY KEY, " +
                Crust.NAME + " INTEGER PRIMARY KEY, " +
                Crust.PRICE + "REAL)"

        val SQL_DELETE_CRUST_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    }

}