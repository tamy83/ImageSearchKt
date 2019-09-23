package com.example.imagesearchkt

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.net.URLEncoder
import java.util.*
import javax.net.ssl.HttpsURLConnection

// default visibility is public
class MainActivity : AppCompatActivity() {

    // lazy or lateinit
    // https://www.bignerdranch.com/blog/kotlin-when-to-use-lazy-or-lateinit/
    var imagesRecyclerView : RecyclerView? = null

    // not null type. Will throw exception if tried to access before it is set on line 37
    //lateinit var imagesRecyclerView : RecyclerView

    // non null type, initializes by the supplied lambda unless it has been already assigned
    //val imagesRecyclerView by lazy { findViewById<RecyclerView>(R.id.imagesRecyclerView) }
    var imageAdapter : ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagesRecyclerView = findViewById<RecyclerView>(R.id.imagesRecyclerView)
        imageAdapter = ImageAdapter()

        var layoutManager = GridLayoutManager(this.applicationContext, 2)

        imagesRecyclerView?.setLayoutManager(layoutManager)
        imagesRecyclerView?.setItemAnimator(DefaultItemAnimator())
        imagesRecyclerView?.setAdapter(imageAdapter)
        imagesRecyclerView?.setItemViewCacheSize(50)

    }

    fun search(view: View) {
        val searchTermsTextView = findViewById<TextView>(R.id.searchTermsTextView)
        val searchTerms = searchTermsTextView.getText().toString()
        val errorMessageBuilder = StringBuilder()
        if (searchTerms.isEmpty()) {
            errorMessageBuilder.append("Please enter search terms.\n")
        }

        val countTextView = findViewById<TextView>(R.id.countTextView)
        val countString = countTextView.getText().toString()
        try {
            val count = Integer.parseInt(countString)
            if (!isPrime(count)) {
                errorMessageBuilder.append("Please enter a Prime Number.")
            }
        } catch (ex: NumberFormatException) {
            errorMessageBuilder.append("Please enter a Prime Number.")
        }

        val errorMessage = errorMessageBuilder.toString()
        if (!errorMessage.isEmpty()) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            return
        }

        val imageSearchTask = ImageSearchTask()
        imageSearchTask.setAdapter(imageAdapter)
        imageSearchTask.execute(searchTerms, countString)
    }

    internal inner class ImageSearchTask : AsyncTask<String, Void, List<Image>>() {

        var subscriptionKey = "c836d36105aa4386878b3f87c71b74c1"
        var host = "https://api.cognitive.microsoft.com"
        var path = "/bing/v7.0/images/search"
        //var imageAdapter: ImageAdapter ?= null

        fun setAdapter(adapter: ImageAdapter?) {
            imageAdapter = adapter
        }

        override fun doInBackground(vararg params: String): List<Image>? {
            try {
                val url = URL(
                    host + path
                            + "?q=" + URLEncoder.encode(params[0], "UTF-8")
                            + "&count=" + URLEncoder.encode(params[1], "UTF-8")
                )
                // casting
                val connection = url.openConnection() as HttpsURLConnection
                connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey)

                val stream = connection.inputStream
                val response = Scanner(stream).useDelimiter("\\A").next()

                stream.close()

                val parser = JsonParser()
                // convenience method, should check isJsonObject before calling
                //val json = parser.parse(response).asJsonObject
                val json = parser.parse(response) as JsonObject

                val images = ArrayList<Image>()
                val results = json.getAsJsonArray("value")
                //for (i in 0 until results.size()) {
                for (i in 0..results.size()) {
                    val imageJson = results.get(i) as JsonObject
                    val image = BingImage(imageJson)
                    images.add(image)
                }
                return images
            } catch (ex: Exception) {
                Log.e("ImageSearchKt", ex.message!!) // due to !! will crash is message is null
                return ArrayList<Image>()
            }
        }

        override fun onPostExecute(images: List<Image>) {
            imageAdapter?.setImages(images)
            imageAdapter?.notifyDataSetChanged()
        }
    }
}
