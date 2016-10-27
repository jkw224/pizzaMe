package com.izeni.pizzaMe.database.tables

import android.content.ContentValues
import android.provider.BaseColumns
import android.util.Log
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

/**
 * Created by jonny on 10/17/16.
 */
class Order(val orderName: String, val orderPrice: Int): BaseColumns {

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

        val SQL_DELETE_ORDERS_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    }

    fun contentValues() {

        val ordersContentValues = ContentValues()
        ordersContentValues.put(NAME, orderName)
        ordersContentValues.put(DATE, ZonedDateTime.now().toString())
        ordersContentValues.put(PRICE, orderPrice)

        /** To parse and format date *//*
        val parsedZoned = ZonedDateTime.parse(ZonedDateTime.now().toString())
        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
        val formattedDateTime = dateTimeFormatter.format(parsedZoned) */

    }

}