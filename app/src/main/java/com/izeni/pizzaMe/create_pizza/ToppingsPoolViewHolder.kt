package com.izeni.pizzaMe.create_pizza

import android.view.View
import com.izeni.pizzaMe.common.ViewHolder
import com.izeni.pizzaMe.database.tables.Toppings
import kotlinx.android.synthetic.main.item_topping.view.*

/**
 * Created by jonny on 10/21/16.
 */
class ToppingsPoolViewHolder(view: View, val onItemClicked: ((Toppings, Int) -> Unit)? = null, val isSelected: (Toppings) -> Boolean): ViewHolder<Toppings>(view/*, onItemClicked*/) {

    override fun bind(item: Toppings) {

        if (isSelected(item)) {
            itemView.topping_text_view.visibility = View.GONE
        } else {
            itemView.topping_text_view.visibility = View.VISIBLE
        }

        itemView.topping_text_view.text = item.name

        itemView.setOnClickListener {

            onItemClicked?.invoke(item, adapterPosition)

        }
    }
}