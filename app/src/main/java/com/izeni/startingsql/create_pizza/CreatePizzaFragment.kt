package com.izeni.startingsql.create_pizza

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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

    lateinit var fabCreatePizza: FloatingActionButton
    lateinit var toppingsSelectedRecycler: RecyclerView
    lateinit var toppingsPoolRecycler: RecyclerView
    lateinit var crustSpinner: Spinner
    lateinit var sizeSpinner: Spinner
    lateinit var toppingsSelectedArray: ArrayList<Toppings>
    lateinit var toppingsPoolArray: ArrayList<Toppings>

    var pizzaSize: String? = ""
    var pizzaType: String? = ""
    var pizzaTopping1: String? = ""
    var pizzaTopping2: String? = ""
    var pizzaName: String? = ""

    var sizePrice = 0
    var typePrice = 0
    var toppingsPrice = 0
    var pizzaPrice: Int? = 0


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_create_pizza, container, false)

        view?.let {
            fabCreatePizza = view.fab_create_pizza as FloatingActionButton
            crustSpinner = view.crust_spinner as Spinner
            sizeSpinner = view.size_spinner as Spinner

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


        /** Create size spinner, with item selected callback */
        val sizeSpinnerAdapter = ArrayAdapter.createFromResource(context, R.array.size_spinner_array, R.layout.spinner_nothing_selected_size)
        sizeSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item) // customizing dropdown text
        sizeSpinner.adapter = NothingSelectedSpinnerAdapter(context, sizeSpinnerAdapter, R.layout.spinner_nothing_selected_size)
        sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                /** Fixing array position due to extending NothingSelectedSpinnerAdapter instead of default adapter*/
                if (position == 0) {
                    pizzaSize = ""
                } else {
                    val correctedPosition = (position - 1)
                    pizzaSize = resources.getStringArray(R.array.size_spinner_array)[correctedPosition]
                    createPizzaName()
                    createPizzaPrice(correctedPosition, null, 0)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        /** Create crust spinner, with item selected callback */
        val typeSpinnerAdapter = ArrayAdapter.createFromResource(context, R.array.crusts_spinner_array, R.layout.spinner_nothing_selected_crust)
        typeSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item) // customizing dropdown text
        crustSpinner.adapter = NothingSelectedSpinnerAdapter(context, typeSpinnerAdapter, R.layout.spinner_nothing_selected_crust)
        crustSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                /** Fixing array position due to extending NothingSelectedSpinnerAdapter instead of default adapter */
                if (position == 0) {
                    pizzaType = ""
                } else {
                    val correctedPosition = (position - 1)
                    pizzaType = resources.getStringArray(R.array.crusts_spinner_array)[correctedPosition]
                    createPizzaName()
                    createPizzaPrice(null, correctedPosition, 0)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        return view
    }


    fun updateToppingsSelectedRecycler() {

        toppingsSelectedRecycler.layoutManager = LinearLayoutManager(context)
        toppingsSelectedRecycler.isNestedScrollingEnabled = false
        toppingsSelectedRecycler.adapter = GenericRecyclerAdapter(context, R.layout.item_topping, toppingsSelectedArray, { view: View ->
            ToppingsSelectedViewHolder(view) { topping, position ->


                /** When I use the commented-out code below, the RecylerView isn't entirely
                 * re-render at once (it only renders the itemView) and you can see when
                 * that itemView renders -- hence I'm not using it (it doesn't look as good) */
                toppingsSelectedArray.removeAt(position)
                toppingsSelectedRecycler.adapter.notifyDataSetChanged()
//              toppingsSelectedRecycler.adapter.notifyItemRemoved(position)

                toppingsPoolRecycler.adapter.notifyDataSetChanged()
//              val index = toppingsPoolArray.indexOfFirst { it.name == topping.name }
//              if(index >= 0) {
//                    toppingsPoolRecycler.adapter.notifyItemChanged(index)
//              }
                createPizzaPrice(null, null, toppingsSelectedArray.size)


                /** Select vs Selected heading in fragment_create_pizza*/
                if (toppingsSelectedArray.size > 0) {
                    selected_divider1a.visibility = View.GONE
                    selected_divider1b.visibility = View.VISIBLE
                    selected_divider1b.text = "${toppingsSelectedArray.size} Selected"
                } else if (toppingsSelectedArray.size == 0) {
                    selected_divider1a.visibility = View.VISIBLE
                    selected_divider1b.visibility = View.GONE
                }

            }
        })
    }

    fun updateToppingsPoolRecycler() {
        toppingsPoolRecycler.layoutManager = LinearLayoutManager(context)
        toppingsPoolRecycler.isNestedScrollingEnabled = false
        toppingsPoolRecycler.adapter = GenericRecyclerAdapter(context, R.layout.item_topping, toppingsPoolArray, { view: View ->
            ToppingsPoolViewHolder(view, { topping, position ->

                /** When I use the commented-out code below, the RecylerView isn't entirely
                 * re-render at once (it only renders the itemView) and you can see when
                 * that itemView renders -- hence I'm not using it (it doesn't look as good) */
                toppingsSelectedArray.add(topping)
                toppingsSelectedRecycler.adapter.notifyDataSetChanged()
//              val index = toppingsSelectedArray.indexOfFirst { it.name == topping.name }
//              if (index >= 0) {
//                    toppingsSelectedRecycler.adapter.notifyItemInserted(index)
//              }

                toppingsPoolRecycler.adapter.notifyDataSetChanged()


                /** Sends toppings to be priced */
                createPizzaPrice(null, null, toppingsSelectedArray.size)


                /** Select vs Selected heading in fragment_create_pizza*/
                if (toppingsSelectedArray.size > 0) {
                    selected_divider1a.visibility = View.GONE
                    selected_divider1b.visibility = View.VISIBLE
                    selected_divider1b.text = "${toppingsSelectedArray.size} Selected"
                }

            }, { it in toppingsSelectedArray })
        })
    }

    fun orderToppingsList(): ArrayList<Toppings> {

        /** Made an array of objects, because want to sort array according to Topping type */
        /** i.e Toppings.SAUCE, Toppings.MEAT, Toppings.SECONDARY (topping)*/

        val sauceList = arrayListOf(Toppings("Regular Sauce", Toppings.SAUCE),
                Toppings("BBQ Sauce", Toppings.SAUCE), Toppings("Thai Sauce", Toppings.SAUCE),
                Toppings("Chicken Alfredo Sauce", Toppings.SAUCE))

        val meatsList = arrayListOf(Toppings("Pepperoni", Toppings.MEAT), Toppings("Chicken", Toppings.MEAT),
                Toppings("Bacon", Toppings.MEAT), Toppings("Canadian Bacon", Toppings.MEAT))

        val secondaryToppingsList = arrayListOf(Toppings("Pineapple", Toppings.SECONDARY), Toppings("Bell Peppers", Toppings.SECONDARY),
                Toppings("Onions", Toppings.SECONDARY), Toppings("Mushroom", Toppings.SECONDARY))


        val toppingsList: ArrayList<Toppings> = arrayListOf()

        toppingsList.addAll(sauceList)
        toppingsList.addAll(meatsList)
        toppingsList.addAll(secondaryToppingsList)
        toppingsList.add(Toppings("No Cheese", Toppings.CUSTOM_PIZZA))
        toppingsList.add(Toppings("Cheese Pizza", Toppings.CUSTOM_PIZZA))

        return toppingsList
    }


    fun createPizzaName() {
        /** Not going to include topping (pizzaTopping1 & pizzaTopping2) in Pizza name */
        /** I'll pull from SQLite and add that info in the AddPizzaFragment */
        pizzaName = "${pizzaSize.safe(append = " ")}${pizzaType.safe(append = " ")}${pizzaTopping1.safe(append = " ")}${pizzaTopping2.safe(append = " ")}Pizza"
        pizza_name.text = pizzaName
    }

    fun String?.safe(prepend: String = "", append: String = "") = if (isNullOrEmpty()) "" else "$prepend$this$append"


    fun createPizzaPrice(pizzaSizeSpinnerPosition: Int? = null, pizzaTypeSpinnerPosition: Int? = null, numToppings: Int = 0) {

        pizzaSizeSpinnerPosition.let {
            when (pizzaSizeSpinnerPosition) {
            /** Price in US cents */
                0 -> sizePrice = 599  /** Small $5.99 */
                1 -> sizePrice = 799  /** Medium $7.99 */
                2 -> sizePrice = 999  /** Large $9.99 */
                3 -> sizePrice = 1299 /** Colossus $12.99 */
            }
        }

        pizzaTypeSpinnerPosition.let {
            when (pizzaTypeSpinnerPosition) {
            /** Price in US cents */
                0 -> typePrice = 0   /** No extra for Regular Crust */
                1 -> typePrice = 0   /** No extra for Thin Crust */
                2 -> typePrice = 150 /** Stuffed Crust = $1.50 */
                3 -> typePrice = 150 /** Deep Dish = $1.50 */
            }
        }

        /** equals overridden in Toppings.kt */
        val regSauce = Toppings("Regular Sauce", Toppings.SAUCE) in toppingsSelectedArray
        // Same as...
        // toppingsSelectedArray.contains(Toppings("Regular Sauce", Toppings.SAUCE))


        /** Price in US cents */
        toppingsPrice =
                /** 3 toppings free -- Regular Sauce does not count as a topping */
                if (regSauce && numToppings > 4) {
                    (numToppings - 4) * 50
                }
                else if (!regSauce && numToppings > 3) {
                    (numToppings - 3) * 50
                } else 0


        pizzaPrice = sizePrice + typePrice + toppingsPrice

        val dollars = pizzaPrice!!.toFloat() / 100f
        pizza_price.text = "$%.2f".format(dollars)

        /** Show Fab when all items have been selected */
        if (pizzaSize != "" && pizzaType != "" && toppingsSelectedArray.isNotEmpty()) {
            fabCreatePizza.visibility = View.VISIBLE
            fabCreatePizza.setOnClickListener {

            }
        } else {
            fabCreatePizza.visibility = View.GONE
        }

    }
}