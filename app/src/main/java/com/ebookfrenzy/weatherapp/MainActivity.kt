package com.ebookfrenzy.weatherapp

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {


    private val zip = 60013

    private val weatherApi: String = "8b970b39fc2bb866c9554350173976d7"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GetWeather().execute()
    }


    inner class GetWeather : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg params: String): String? {


            return try {

                URL("https://api.openweathermap.org/data/2.5/weather?zip=$zip,us&units=imperial&appid=$weatherApi").readText(
                    Charsets.UTF_8
                )


            } catch (e: Exception) {
                null.toString()

            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("err", "here")
            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val currentTemp = main.getString("temp").toDouble().roundToInt()
                val realFeel = main.getString("feels_like").toDouble().roundToInt()
                val humidity = main.getString("humidity")
                val minTemp = main.getString("temp_min")
                val maxTemp = main.getString("temp_max")
                findViewById<TextView>(R.id.txtMinTemp).text = ("$minTemp°F")
                findViewById<TextView>(R.id.txtMaxTemp).text = ("$maxTemp°F")
                findViewById<TextView>(R.id.txtRealFeel).text = ("Feels like: $realFeel°")
                findViewById<TextView>(R.id.txtTemp).text = ("$currentTemp°F")
                findViewById<TextView>(R.id.forecastTxt).text = ("Local Forecast for $zip")
                findViewById<TextView>(R.id.txtHumidity).text = ("$humidity%")

            } catch (e: Exception) {

            }
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}