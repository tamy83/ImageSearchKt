package com.example.imagesearchkt

import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var images : List<Image> = ArrayList<Image>()
    fun setImages(images: List<Image>) {
        this.images = images
        for (image in images) {
            var imageDownloadTask = ImageDownloadTask()
            imageDownloadTask.recyclerViewAdapter = this
            ImageDownloadTask().execute(Pair(image, images.indexOf(image)))
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // can use val instead of var since it's never modified
        val image = images.get(position)
        val bitmap = image.bitmap
        holder.imageView?.setImageBitmap(bitmap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    // primary constructor
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView : ImageView? = null
        // initialization block for primary constructor
        init {
            imageView = view.findViewById(R.id.imageView)
        }
        // all secondary constructors MUST call the primary constructor
        constructor(view: View, i: Int) : this(view) {
            // overloaded constructor example
        }
    }
}