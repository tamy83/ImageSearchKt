package com.example.imagesearchkt;

import android.graphics.Bitmap

// open, opposite of java final
// by default all classes in Kotlin are final
open class Image {
    var name : String? = null
    var url : String? = null
    var thumbnailUrl : String? = null
    var width : Int? = null
    var height : Int? = null
    var thumbnailWidth : Int? = null
    var thumbnailHeight : Int? = null
    var size : Int? = null
    var encondingFormat : String? = null
    var bitmap : Bitmap? = null
}
