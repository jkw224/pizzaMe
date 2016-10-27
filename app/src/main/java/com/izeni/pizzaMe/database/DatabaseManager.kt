package com.izeni.pizzaMe.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.izeni.pizzaMe.database.tables.Order
import com.izeni.pizzaMe.database.tables.Crust
import com.izeni.pizzaMe.database.tables.Pizza
import com.izeni.pizzaMe.database.tables.Size
import com.izeni.pizzaMe.database.tables.Toppings

/**
 * Created by jonny on 10/17/16.
 */
class DatabaseManager(context: Context): SQLiteOpenHelper(context, "PizzaOrders.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Order.SQL_CREATE_ORDERS_TABLE)
        db?.execSQL(Pizza.SQL_CREATE_PIZZA_TABLE)
        db?.execSQL(Size.SQL_CREATE_SIZE_TABLE)
        db?.execSQL(Toppings.SQL_CREATE_TOPPINGS_TABLE)
        db?.execSQL(Crust.SQL_CREATE_CRUST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

/*        when (db) {
            is
        }
        db?.execSQL(Order.SQL_DELETE_ORDERS_TABLE)
        db?.execSQL(Pizza.SQL_DELETE_PIZZA_TABLE)
        db?.execSQL(Size.SQL_DELETE_SIZE_TABLE)
        db?.execSQL(Toppings.SQL_DELETE_TOPPINGS_TABLE)
        db?.execSQL(Crust.SQL_DELETE_CRUST_TABLE)*/
    }


}