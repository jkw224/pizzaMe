package com.izeni.startingsql.create_pizza

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.izeni.startingsql.R
import com.izeni.startingsql.common.GenericRecyclerAdapter
import com.izeni.startingsql.common.NothingSelectedSpinnerAdapter
import com.izeni.startingsql.database.tables.Toppings
import kotlinx.android.synthetic.main.fragment_create_pizza.*
import kotlinx.android.synthetic.main.fragment_create_pizza.view.*
import java.util.*


/**
 * Created by jonny on 10/18/16.
 */
class CreatePizzaFragment: Fragment() {

    lateinit var toppingsSelectedRecycler: RecyclerView
    lateinit var toppingsPoolRecycler: RecyclerView
    lateinit var crustSpinner: Spinner
    lateinit var sizeSpinner: Spinner
    lateinit var toppingsSelectedArray: ArrayList<Toppings>
    lateinit var toppingsPoolArray: ArrayList<Toppings>


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_create_pizza, container, false)

        view?.let {

            /** Spinner */
            crustSpinner = view.crust_spinner as Spinner
            sizeSpinner = view.size_spinner as Spinner

            createSpinner(crustSpinner, R.array.crusts_spinner_array, R.layout.spinner_nothing_selected_crust)
            createSpinner(sizeSpinner, R.array.size_spinner_array, R.layout.spinner_nothing_selected_size)

            /** RecyclerViews */
            toppingsSelectedRecycler = view.toppings_selected_recycler1 as RecyclerView
            toppingsPoolRecycler = view.toppings_pool_recycler2 as RecyclerView

            toppingsSelectedArray = ArrayList<Toppings>()
            toppingsSelectedArray = arrayListOf()

            toppingsPoolArray = ArrayList<Toppings>()
            toppingsPoolArray = orderToppingsList()

            updateToppingsSelectedRecycler()
            updateToppingsPoolRecycler()

        }

        return view
    }

    /**
     * Creating custom spinner (Custom title (not from array) and custom dropdown items)
     * http://stackoverflow.com/questions/867518/how-to-make-an-android-spinner-with-initial-text-select-one */
    fun createSpinner(targetSpinner: Spinner, spinnerArrayId: Int, spinnerLayoutId: Int) {
        val spinnerAdapter = ArrayAdapter.createFromResource(context, spinnerArrayId, spinnerLayoutId)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item) // customize dropdown text
        targetSpinner.adapter = NothingSelectedSpinnerAdapter(spinnerAdapter, spinnerLayoutId, context)
    }

    fun updateToppingsSelectedRecycler() {

        toppingsSelectedRecycler.layoutManager = LinearLayoutManager(context)
        toppingsSelectedRecycler.isNestedScrollingEnabled = false
        toppingsSelectedRecycler.adapter = GenericRecyclerAdapter(context, R.layout.item_topping, toppingsSelectedArray, {view: View -> ToppingsSelectedViewHolder(view) { topping, position ->

            toppingsSelectedArray.removeAt(position)
//            toppingsSelectedRecycler.adapter.notifyItemRemoved(position)

            toppingsSelectedRecycler.adapter.notifyDataSetChanged()
            toppingsPoolRecycler.adapter.notifyDataSetChanged()

//            val index = toppingsPoolArray.indexOfFirst { it.name == topping.name }
//
//            if(index >= 0) {
//                toppingsPoolRecycler.adapter.notifyItemChanged(index)
//
//            }

//            updateToppingsSelectedRecycler()
//            updateToppingsPoolRecycler()

            /** Select vs Selected heading in fragment_create_pizza*/
            if (toppingsSelectedArray.size > 0) {
                selected_divider1a.visibility = View.GONE
                selected_divider1b.visibility = View.VISIBLE
            } else if (toppingsSelectedArray.size == 0) {
                selected_divider1a.visibility = View.VISIBLE
                selected_divider1b.visibility = View.GONE
            }

        }})
    }

    fun updateToppingsPoolRecycler() {
        toppingsPoolRecycler.layoutManager = LinearLayoutManager(context)
        toppingsPoolRecycler.isNestedScrollingEnabled = false
        toppingsPoolRecycler.adapter = GenericRecyclerAdapter(context, R.layout.item_topping, toppingsPoolArray, {view: View -> ToppingsPoolViewHolder(view, { topping, position ->

//            toppingsPoolRecycler.adapter.notifyItemChanged(position)
            toppingsSelectedArray.add(topping)

            toppingsSelectedRecycler.adapter.notifyDataSetChanged()
            toppingsPoolRecycler.adapter.notifyDataSetChanged()


//            updateToppingsPoolRecycler()
//            updateToppingsSelectedRecycler()

            /** Select vs Selected heading in fragment_create_pizza*/
            if (toppingsSelectedArray.size > 0) {
                selected_divider1a.visibility = View.GONE
                selected_divider1b.visibility = View.VISIBLE
            }

        }, { it in toppingsSelectedArray })
        })
    }


    fun orderToppingsList(): ArrayList<Toppings> {

        /** Made an array of objects, because want to sort array according to Topping type */
        /** i.e Toppings.SAUCE, Toppings.MEAT, Toppings.SECONDARY (topping)*/

        val sauceList = arrayListOf(Toppings("Tomato Sauce", Toppings.SAUCE),
                Toppings("BBQ Sauce", Toppings.SAUCE), Toppings("Thai Sauce", Toppings.SAUCE),
                Toppings("Chicken Alfredo Sauce", Toppings.SAUCE))

        val meatsList = arrayListOf(Toppings("Cheese", Toppings.MEAT),
                Toppings("Pepperoni", Toppings.MEAT), Toppings("Chicken", Toppings.MEAT),
                Toppings("Bacon", Toppings.MEAT), Toppings("Canadian Bacon", Toppings.MEAT))

        val secondaryToppingsList = arrayListOf(Toppings("Pineapple", Toppings.SECONDARY), Toppings("Bell Peppers", Toppings.SECONDARY),
                Toppings("Onions", Toppings.SECONDARY), Toppings("Mushroom", Toppings.SECONDARY))


        val toppingsList: ArrayList<Toppings> = arrayListOf()

        toppingsList.addAll(sauceList)
        toppingsList.addAll(meatsList)
        toppingsList.addAll(secondaryToppingsList)


        return toppingsList
    }


}