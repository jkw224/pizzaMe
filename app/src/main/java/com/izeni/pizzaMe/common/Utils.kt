package com.izeni.pizzaMe.common

/**
 * Created by jonny on 10/26/16.
 */
object Utils {

    fun convertCentsToDollars(cents: Int): Float {

        val dollars = cents.toFloat() / 100f
        return dollars

    }

}