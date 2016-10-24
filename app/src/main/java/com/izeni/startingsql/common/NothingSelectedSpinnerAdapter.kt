package com.izeni.startingsql.common

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.SpinnerAdapter

/**
 * Decorator Adapter to allow a Spinner to show a 'Nothing Selected...' initially
 * displayed instead of the first choice in the Adapter.
 */

/**
 * Use this constructor to Define your 'Select One...' layout as the first
 * row in the returned choices.
 * If you do this, you probably don't want a prompt on your spinner or it'll
 * have two 'Select' rows.
 * @param spinnerAdapter wrapped Adapter. Should probably return false for isEnabled(0)
 * *
 * @param nothingSelectedLayout layout for nothing selected, perhaps you want
 * * text grayed out like a prompt...
 * *
 * @param nothingSelectedDropdownLayout layout for your 'Select an Item...' in
 * * the dropdown.
 * *
 * @param context
 */
class NothingSelectedSpinnerAdapter (var spinnerAdapter: SpinnerAdapter, var nothingSelectedLayout: Int, var nothingSelectedDropdownLayout: Int, var context: Context) : SpinnerAdapter, ListAdapter {

    var layoutInflater: LayoutInflater

    /**
     * Use this constructor to have NO 'Select One...' item, instead use
     * the standard prompt or nothing at all.
     * @param spinnerAdapter wrapped Adapter.
     * *
     * @param nothingSelectedLayout layout for nothing selected, perhaps
     * * you want text grayed out like a prompt...
     * *
     * @param context
     */
    constructor(spinnerAdapter: SpinnerAdapter, nothingSelectedLayout: Int, context: Context) : this(spinnerAdapter, nothingSelectedLayout, -1, context) {
    }

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // This provides the View for the Selected Item in the Spinner, not
        // the dropdown (unless dropdownView is not set).
        if (position == 0) {
            return getNothingSelectedView(parent)
        }

        return spinnerAdapter.getView(position - EXTRA, null, parent) // Could re-use
        // the convertView if possible.
    }

    /**
     * View to show in Spinner with Nothing Selected
     * Override this to do something dynamic... e.g. "37 Options Found"
     * @param parent
     * *
     * @return
     */
    fun getNothingSelectedView(parent: ViewGroup): View {
        return layoutInflater.inflate(nothingSelectedLayout, parent, false)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Android BUG! http://code.google.com/p/android/issues/detail?id=17128 -
        // Spinner does not support multiple view types
        if (position == 0) {
            return if (nothingSelectedDropdownLayout == -1)
                View(context)
            else
                getNothingSelectedDropdownView(parent)
        }

        // Could re-use the convertView if possible, use setTag...
        return spinnerAdapter.getDropDownView(position - EXTRA, null, parent)
    }

    /**
     * Override this to do something dynamic... For example, "Pick your favorite
     * of these 37".
     * @param parent
     * *
     * @return
     */
    fun getNothingSelectedDropdownView(parent: ViewGroup): View {
        return layoutInflater.inflate(nothingSelectedDropdownLayout, parent, false)
    }

    override fun getCount(): Int {
            val count = spinnerAdapter.count
            return if (count == 0) 0 else count + EXTRA
        }

    override fun getItem(position: Int): Any? {
        return if (position == 0) null else spinnerAdapter.getItem(position - EXTRA)
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long {
        return (
                if (position >= EXTRA) spinnerAdapter.getItemId(position - EXTRA)
                else { (position - EXTRA).toLong() })
    }

    override fun hasStableIds(): Boolean {
        return spinnerAdapter.hasStableIds()
    }

    override fun isEmpty(): Boolean = spinnerAdapter.isEmpty

    override fun registerDataSetObserver(observer: DataSetObserver) {
        spinnerAdapter.registerDataSetObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        spinnerAdapter.unregisterDataSetObserver(observer)
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0 // Don't allow the 'nothing selected'
        // item to be picked.
    }

    companion object {

        val EXTRA = 1
    }

}