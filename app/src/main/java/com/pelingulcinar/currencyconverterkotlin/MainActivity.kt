package com.pelingulcinar.currencyconverterkotlin

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getRate(view: View) {

        val download = Download()

        try {

            val url = "http://data.fixer.io/api/latest?access_key=79f7277f33ca57f5b0e9a81b9e66a66a&format=1"
            download.execute(url)

        } catch (e: Exception) {

        }
    }

    inner class Download: AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {

            var result = ""
            var url: URL
            var httpURLConnection: HttpURLConnection

            try {

                url = URL(params[0]) //backgrounddaki parametreyi alıyoruz
                httpURLConnection = url.openConnection() as HttpURLConnection

                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream) //veriyi burdan okuyor

                var data = inputStream.read() //okunan verileri gösteriyor

                while (data > 0) {

                    val character = data.toChar()
                    result += character

                    data = inputStream.read() //diğer datanın karakterini almak için bir daha yazıyorum

                }

                return result

            } catch (e: Exception) { //exception anlamadım

                return result

            }

        }

        override fun onPostExecute(result: String?) {


            try {

                val jsonObject = JSONObject(result)

                val base = jsonObject.getString("base")
                val rates = jsonObject.getString("rates")

                val jsonObject1 = JSONObject(rates)

                val turkishlira = jsonObject1.getString("TRY")
                val canadadollar = jsonObject1.getString("CAD")
                val macaup = jsonObject1.getString("MOP")
                val dollar = jsonObject1.getString("USD")

                tryText.text = "TRY: " + turkishlira
                cadText.text = "CAD: " + canadadollar
                mopText.text = "MOP: " + macaup
                usdText.text = "USD: " + dollar

            } catch (e: Exception){

            }

            super.onPostExecute(result)
        }


    }
}
