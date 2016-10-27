package com.izeni.pizzaMe.add_pizza

import android.view.View
import com.izeni.pizzaMe.common.Utils
import com.izeni.pizzaMe.common.ViewHolder
import com.izeni.pizzaMe.database.tables.Pizza
import kotlinx.android.synthetic.main.item_add_pizza.view.*

/**
 * Created by jonny on 10/21/16.
 */
class AddPizzaViewHolder(view: View): ViewHolder<Pizza>(view) {

    override fun bind(item: Pizza) {
        itemView.rv_text.text = item.pizName

        val dollarPrice = Utils.convertCentsToDollars(item.pizPrice)
        itemView.add_pizza_price.text = "$$dollarPrice"
    }
}