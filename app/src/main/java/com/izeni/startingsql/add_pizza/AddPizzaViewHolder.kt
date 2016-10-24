package com.izeni.startingsql.add_pizza

import android.view.View
import com.izeni.startingsql.common.ViewHolder
import kotlinx.android.synthetic.main.item_add_pizza.view.*

/**
 * Created by jonny on 10/21/16.
 */
class AddPizzaViewHolder(view: View): ViewHolder<String>(view) {

    override fun bind(item: String) {
        itemView.rv_text.text = item
        itemView.add_pizza_price.text = "Working"
    }
}