package com.izeni.startingsql.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.izeni.startingsql.R
import kotlinx.android.synthetic.main.item_add_pizza.view.*
import java.util.*

/**
 * Created by jonny on 10/18/16.
 */
class GenericRecyclerAdapter<T>(var context: Context, val layoutId: Int, var list: MutableList<T>, val createViewHolder: (View) -> ViewHolder<T>): RecyclerView.Adapter<ViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)

        val viewHolder = createViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder<T>?, position: Int) {
        holder?.bind(list[position])
    }


    override fun getItemCount(): Int = list.size


}