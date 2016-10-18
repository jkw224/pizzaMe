package com.izeni.startingsql

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.izeni.startingsql.database.DatabaseManager
import com.izeni.startingsql.database.tables.Crust
import com.izeni.startingsql.database.tables.Pizza
import com.izeni.startingsql.database.tables.Size
import com.izeni.startingsql.database.tables.Toppings

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // https://developer.android.com/training/basics/data-storage/databases.html#DbHelper
        val dbMan = DatabaseManager(this)
        var writeableDB = dbMan.writableDatabase


        // Insert toppings into toppings table
        var toppingsValues = ContentValues()
        toppingsValues.put(Toppings.NAME, "cheese")

        writeableDB.insert(Toppings.TABLE_NAME, null, toppingsValues)

//        var contentValue = ContentValues()
//        contentValue.put(Toppings.NAME, "pepperoni")
//
//        writeableDB.insert(Toppings.TABLE_NAME, null, contentValue)


        // Insert sizes into size table
        var sizeValues = ContentValues()
        sizeValues.put(Size.SIZE, "small")

        writeableDB.insert(Size.TABLE_NAME, null, sizeValues)


        // Insert crusts into crust table
        var crustValues = ContentValues()
        crustValues.put(Crust.TYPE, "regular")

        writeableDB.insert(Crust.TABLE_NAME, null, crustValues)


        var pizzaValues = ContentValues()
        pizzaValues.put(Pizza.NAME, "Small Cheese Pizza")

        // Reading from a database
//        val readableDB = dbMan.readableDatabase
//        readableDB.query(Toppings.TABLE_NAME, null, "${Pizza.PIZZA_ID}=?", arrayOf("Some pizza id"), null, null, null)
    }
}
