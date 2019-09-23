package com.example.imagesearchkt

import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedInputStream
import java.io.IOException
import java.net.URL

class ImageDownloadTask : AsyncTask<Pair<Image, Int>, Void, Int>() {

    var recyclerViewAdapter: RecyclerView.Adapter<ImageAdapter.ViewHolder>? = null

    override fun doInBackground(vararg pair: Pair<Image, Int>): Int {
        val image = pair[0]?.first
        val position = pair[0]?.second
        var url = URL(image.thumbnailUrl)
        try {
            var connection = url.openConnection()
            connection.connect()
            var inputStream = connection.getInputStream()
            var bufferedInputStream = BufferedInputStream(inputStream)
            var bitmap = BitmapFactory.decodeStream(bufferedInputStream)
            bufferedInputStream.close()
            inputStream.close()
            image.bitmap = bitmap
            // no checked exceptions
            // http://wiki.c2.com/?CheckedExceptionsAreOfDubiousValue
            // http://radio-weblogs.com/0122027/stories/2003/04/01/JavasCheckedExceptionsWereAMistake.html
            return position // return value
        } catch (e: Throwable) {
            return -1 // return value
        }
    }

    override fun onPostExecute(position: Int) {
        if (position != -1) {
            recyclerViewAdapter?.notifyItemChanged(position)
        }
    }
}