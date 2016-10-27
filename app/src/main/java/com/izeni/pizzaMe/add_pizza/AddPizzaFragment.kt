package com.izeni.pizzaMe.add_pizza

import android.content.Context
import android.database.SQLException
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.izeni.pizzaMe.MainActivity
import com.izeni.pizzaMe.R
import com.izeni.pizzaMe.common.GenericRecyclerAdapter
import com.izeni.pizzaMe.database.DatabaseManager
import com.izeni.pizzaMe.database.tables.Order
import com.izeni.pizzaMe.database.tables.Pizza
import kotlinx.android.synthetic.main.fragment_add_pizza.view.*
import java.util.*

/**
 * Created by jonny on 10/18/16.
 */
class AddPizzaFragment: Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var addPizzaRV: RecyclerView
    lateinit var addPizzaFab: FloatingActionButton
    lateinit var orderPizzasButton: Button

    var pizzasToOrderArray: ArrayList<Pizza> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_pizza, container, false)

        view?.let {
            addPizzaRV = view.add_pizza_recycler
            addPizzaFab = view.add_pizza_fab
            orderPizzasButton = view.order_pizzas_button

            createPizzasToOrderRecycler()
        }

        addPizzaFab.setOnClickListener {

            mainActivity.moveToCreatePizzaView()

        }

        orderPizzasButton.setOnClickListener {

            var writeableDb = DatabaseManager(context).writableDatabase

            try {
                writeableDb.beginTransaction()

                try {

//                    Order()

                } catch (e: SQLException) {
                    e.printStackTrace()
                } finally {
                    writeableDb.setTransactionSuccessful()
                }

            } finally {
                writeableDb.endTransaction()
            }

        }

        return view

    }

    // Sets reference to MainActivity
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is MainActivity) {
            mainActivity = context
        }
    }


    fun createPizzasToOrderRecycler() {
        pizzasToOrderArray = fetchPizzaOrdersFromDatabase()

        if (pizzasToOrderArray.size > 0) {
            orderPizzasButton.visibility = View.VISIBLE
        } else {
            orderPizzasButton.visibility = View.GONE
        }

        pizzasToOrderArray.let {
            addPizzaRV.layoutManager = LinearLayoutManager(context)
            addPizzaRV.adapter = GenericRecyclerAdapter(context, R.layout.item_add_pizza, it) { view: View -> AddPizzaViewHolder(view) }
        }
    }

    fun fetchPizzaOrdersFromDatabase(): ArrayList<Pizza> {

        val pizzasToOrder = mutableListOf<Pizza>()

        val readableDb = DatabaseManager(context).readableDatabase
        val cursor = readableDb.query(Pizza.TABLE_NAME, null, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex(Pizza.NAME))
            val centPrice = cursor.getInt(cursor.getColumnIndex(Pizza.PRICE))

            pizzasToOrder.add(Pizza(name, centPrice))

        }

        cursor.close()

        return pizzasToOrder as ArrayList<Pizza>

    }

    fun calculatePizzaPrice(): Int {

        var totalOrderPrice: Int = 0

        for (pizza in pizzasToOrderArray) {
            totalOrderPrice += pizza.pizPrice
        }

        return totalOrderPrice

    }
}