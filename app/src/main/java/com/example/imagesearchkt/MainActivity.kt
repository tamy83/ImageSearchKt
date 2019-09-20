package com.example.imagesearchkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

// default visibility is public
class MainActivity : AppCompatActivity() {

    // lazy or lateinit
    // https://www.bignerdranch.com/blog/kotlin-when-to-use-lazy-or-lateinit/
    var imagesRecyclerView : RecyclerView? = null
    var imageAdapter : ImageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagesRecyclerView = findViewById<RecyclerView>(R.id.imagesRecyclerView)
        imageAdapter : ImageAdapter(ArrayList<Image>())

    }
}
