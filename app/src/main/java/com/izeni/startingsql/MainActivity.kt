package com.izeni.startingsql

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.izeni.startingsql.add_pizza.AddPizzaFragment
import com.izeni.startingsql.create_pizza.CreatePizzaFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // loading AddPizzaFragment into fragment_container in activity_main.xml
        moveToAddPizzaList()

        // Reading from a database
//        val readableDB = dbMan.readableDatabase
//        readableDB.query(Toppings.TABLE_NAME, null, "${Pizza.PIZZA_ID}=?", arrayOf("Some pizza id"), null, null, null)
    }

    fun moveToAddPizzaList() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddPizzaFragment())
                .commit()
    }


    fun moveToCreatePizzaView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CreatePizzaFragment())
                .addToBackStack(null)
                .commit()
    }

}
