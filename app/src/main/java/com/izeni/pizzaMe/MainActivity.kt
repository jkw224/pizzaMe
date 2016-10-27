package com.izeni.pizzaMe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.izeni.pizzaMe.add_pizza.AddPizzaFragment
import com.izeni.pizzaMe.create_pizza.CreatePizzaFragment
import com.izeni.pizzaMe.R
import org.threeten.bp.Clock
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.FormatStyle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // loading AddPizzaFragment into fragment_container in activity_main.xml
        moveToAddPizzaList()


        /** Meh ThreeTen code */
        /*val dateTimeStamp = LocalDateTime.now().atZone(ZoneId.of("GMT")).format(DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.of("GMT"))).toString()
        Log.i("== dateTimeStamp ==", "$dateTimeStamp")*/

        /** Usable ThreeTen code -- for PizzaMe*/
        /*val currentTimeAndDate = ZonedDateTime.now()
        Log.i("== zonedDateTime ==", "$currentTimeAndDate")

        val parsedZoned = ZonedDateTime.parse(currentTimeAndDate.toString())
        Log.i("== parsedZoned ==", "$parsedZoned")

        val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formattedDateTime = dateTimeFormatter.format(parsedZoned)
        Log.i("==formattedParsedZone==", "$formattedDateTime")*/


        // Reading from a database
//        val readableDB = dbMan.readableDatabase
//        readableDB.query(Toppings.TABLE_NAME, null, "${Pizza.PIZZA_ID}=?", arrayOf("Some pizza id"), null, null, null)
    }

    fun moveToAddPizzaList() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddPizzaFragment())
                .commit()
    }


    fun moveToCreatePizzaView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CreatePizzaFragment())
                .addToBackStack(null)
                .commit()
    }

}
