package com.izeni.startingsql.create_pizza

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.SQLException
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
import com.izeni.startingsql.MainActivity
import com.izeni.startingsql.R
import com.izeni.startingsql.common.GenericRecyclerAdapter
import com.izeni.startingsql.common.NothingSelectedSpinnerAdapter
import com.izeni.startingsql.database.DatabaseManager
import com.izeni.startingsql.database.tables.Crust
import com.izeni.startingsql.database.tables.Pizza
import com.izeni.startingsql.database.tables.Size
import com.izeni.startingsql.database.tables.Toppings
import kotlinx.android.synthetic.main.fragment_create_pizza.*
import kotlinx.android.synthetic.main.fragment_create_pizza.view.*
import java.util.*


/**
 * Created by jonny on 10/18/16.
 */
class CreatePizzaFragment: Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fabCreatePizza: FloatingActionButton
    lateinit var toppingsSelectedRecycler: RecyclerView
    lateinit var toppingsPoolRecycler: RecyclerView
    lateinit var crustSpinner: Spinner
    lateinit var sizeSpinner: Spinner
    lateinit var toppingsSelectedArray: ArrayList<Toppings>
    lateinit var toppingsPoolArray: ArrayList<Toppings>

    var pizzaName: String = ""
    var pizzaPrice: Int = 0

    var pizzaSize: String = ""
    var sizePrice = 0

    var crustType: String = ""
    var crustPrice = 0

    var pizzaTopping1: String = ""
    var pizzaTopping2: String = ""
    var toppingsPrice = 0



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
                    crustType = ""
                } else {
                    val correctedPosition = (position - 1)
                    crustType = resources.getStringArray(R.array.crusts_spinner_array)[correctedPosition]
                    createPizzaName()
                    createPizzaPrice(null, correctedPosition, 0)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is MainActivity) {
            mainActivity = context
        }

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
        pizzaName = "${pizzaSize.safe(append = " ")}${crustType.safe(append = " ")}${pizzaTopping1.safe(append = " ")}${pizzaTopping2.safe(append = " ")}Pizza"
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
                0 -> crustPrice = 0   /** No extra for Regular Crust */
                1 -> crustPrice = 0   /** No extra for Thin Crust */
                2 -> crustPrice = 150 /** Stuffed Crust = $1.50 */
                3 -> crustPrice = 150 /** Deep Dish = $1.50 */
            }
        }

        /** equals method (for Toppings class) overridden in Toppings.kt */
        /** if regular sauce is selected ... */
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


        pizzaPrice = sizePrice + crustPrice + toppingsPrice

        val dollars = pizzaPrice.toFloat() / 100f
        pizza_price.text = "$%.2f".format(dollars)


        /** Show Fab when all items have been selected */
        if (pizzaSize != "" && crustType != "" && toppingsSelectedArray.isNotEmpty()) {
            fabCreatePizza.visibility = View.VISIBLE

            /** Pressing FAB will save all pizza info to database tables and move back to AddPizzaFragment */
            fabCreatePizza.setOnClickListener {

                val writeableDb = DatabaseManager(context).writableDatabase

                try {
                    /** insert into database as a batch */
                    writeableDb.beginTransaction()

                    try {
                        // https://developer.android.com/training/basics/data-storage/databases.html#DbHelper
                        /** Pizza Table */
                        val pizzaObject = Pizza(pizzaName, pizzaPrice)
                        val pizzaId = writeToDb(writeableDb, Pizza.TABLE_NAME, pizzaObject.pizzaContentValues())

                        /** Toppings Table */
                        for (i in toppingsSelectedArray.indices) {

                            /** Calculate toppings price */
                            var pricePerTopping: Int
                            if (regSauce)
                                if (i < 4) {
                                    pricePerTopping = 0
                                } else {
                                    pricePerTopping = 50
                                }
                            else if (!regSauce) {
                                if (i < 3) {
                                    pricePerTopping = 0
                                } else {
                                    pricePerTopping = 50
                                }
                            } else {
                                pricePerTopping = 0
                            }

                            val toppingContentValuesWithPizzaId = toppingsSelectedArray[i].contentValues(numToppings, regSauce, pricePerTopping).apply { put(Pizza.PIZZA_ID, pizzaId) }
                            writeToDb(writeableDb, Toppings.TABLE_NAME, toppingContentValuesWithPizzaId)
                        }

                        /** Crust Table */
                        val crust = Crust(crustType, crustPrice)
                        writeToDb(writeableDb, Crust.TABLE_NAME, crust.contentValues().apply { put(Pizza.PIZZA_ID, pizzaId) })

                        /** Size Table */
                        val size = Size(pizzaSize, sizePrice)
                        writeToDb(writeableDb, Size.TABLE_NAME, size.contentValues().apply { put(Pizza.PIZZA_ID, pizzaId) })

                    } catch(e: SQLException) {
                        e.printStackTrace()
                    } finally {
                            writeableDb.setTransactionSuccessful()
                    }

                }  catch (e: SQLException){
                    e.printStackTrace()
                }  finally {
                    writeableDb.endTransaction()
                }


                writeableDb.close()

                mainActivity.moveToAddPizzaList()

            }
        /** When all inputs not selected, hide FAB */
        } else {
            fabCreatePizza.visibility = View.GONE
        }

    }

    fun writeToDb(writableDb: SQLiteDatabase, tableName: String, contentValues: ContentValues): Long {
        return writableDb.insert(tableName, null, contentValues)
    }
}