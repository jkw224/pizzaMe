package com.izeni.startingsql.add_pizza

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izeni.startingsql.MainActivity
import com.izeni.startingsql.R
import com.izeni.startingsql.common.GenericRecyclerAdapter
import com.izeni.startingsql.common.ViewHolder
import kotlinx.android.synthetic.main.fragment_add_pizza.view.*
import kotlinx.android.synthetic.main.item_add_pizza.view.*
import org.jetbrains.annotations.Mutable
import java.util.*

/**
 * Created by jonny on 10/18/16.
 */
class AddPizzaFragment: Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var addPizzaRV: RecyclerView
    lateinit var addPizzaFab: FloatingActionButton
    var ordersList: MutableList<String>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_pizza, container, false)

        view?.let {
            addPizzaRV = view.add_pizza_recycler
            addPizzaFab = view.add_pizza_fab

            addToOrdersArray()
        }

        addPizzaFab.setOnClickListener {

            mainActivity.moveToCreatePizzaView()

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


    fun addToOrdersArray() {
        ordersList = mutableListOf("Hello", "World")
        // first pull from database
        // add one, tell adapter -> new pizza added, then save to database
        // notify after that

        ordersList.let {
            addPizzaRV.layoutManager = LinearLayoutManager(context)
            addPizzaRV.adapter = GenericRecyclerAdapter(context, R.layout.item_add_pizza, it!!) { view: View -> AddPizzaViewHolder(view) }
        }
    }
}