package com.izeni.startingsql.database.tables

import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Orders: BaseColumns {

    companion object {

        val TABLE_NAME = "orders"

        val NAME = "name"
        val ADDRESS = "address"
        val PHONE = "phone"
        val PRICE = "price"
        val DATE = "date"
        val IS_DELIVERED = "delivered"

        // https://www.tutorialspoint.com/sqlite/sqlite_data_types.htm
        val SQL_CREATE_ORDERS_TABLE =  "CREATE TABLE ${TABLE_NAME} (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                ADDRESS + " TEXT, " +
                PHONE + " TEXT, " +
                DATE + " DATE, " + // Numeric affinity
                PRICE + " INTEGER, " + // store as cents (if currency is US Dollar)
                IS_DELIVERED + " BOOLEAN CHECK (delivered IN (0,1)));" // Numeric

        val SQL_DELETE_ORDERS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}