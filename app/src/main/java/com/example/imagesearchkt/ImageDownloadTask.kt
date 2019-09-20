package com.example.imagesearchkt

import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedInputStream
import java.net.URL

class ImageDownloadTask : AsyncTask<Pair<Image, Int>, Void, Int>() {

    var recyclerViewAdapter: RecyclerView.Adapter<ImageAdapter.ViewHolder>? = null

    override fun doInBackground(vararg pair: Pair<Image, Int>): Int {
        val image = pair[0]?.first
        val position = pair[0]?.second
        var url = URL(image.thumbnailUrl)
        var connection = url.openConnection()
        connection.connect()
        var inputStream = connection.getInputStream()
        var bufferedInputStream = BufferedInputStream(inputStream)
        var bitmap = BitmapFactory.decodeStream(bufferedInputStream)
        bufferedInputStream.close()
        inputStream.close()
        image.bitmap = bitmap
        // how do i catch exceptions???
        return position
    }

    override fun onPostExecute(position: Int) {
        recyclerViewAdapter?.notifyItemChanged(position)
    }
}