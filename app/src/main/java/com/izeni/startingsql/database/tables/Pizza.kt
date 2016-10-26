package com.izeni.startingsql.database.tables

import android.content.ContentValues
import android.provider.BaseColumns

/**
 * Created by jonny on 10/17/16.
 */
class Pizza(val pizName: String, val pizPrice: Int): BaseColumns {

    companion object {

        val TABLE_NAME: String = "pizza"

        val NAME: String = "name"
        val PRICE: String = "price"
        val PIZZA_ID: String = "pizza_id"

        val SQL_CREATE_PIZZA_TABLE = "CREATE TABLE $TABLE_NAME (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Pizza.NAME + " TEXT, " +
                Pizza.PRICE + " INTEGER)"

        val SQL_DELETE_PIZZA_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"

    }

    fun pizzaContentValues(): ContentValues {

        var pizzaContentValues = ContentValues()

        pizzaContentValues.put(NAME, pizName)
        pizzaContentValues.put(PRICE, pizPrice)

        return pizzaContentValues

    }

}