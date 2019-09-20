package com.example.imagesearchkt

import com.google.gson.JsonObject

class BingImage : Image {
    var webSearchUrl : String? = null
    var hostPageUrl : String? = null
    // ...

    // secondary constructor
    constructor(json: JsonObject) {
        // need to use ?. ???
        name = json.get("name").asString
        thumbnailUrl = json.get("thumbnailUrl").asString
        url = json.get("contentUrl").asString
        width = json.get("width").asInt
        height = json.get("height").asInt
        thumbnailWidth = json.get("thumbnail").asJsonObject.get("width").asInt
        thumbnailHeight = json.get("thumbnail").asJsonObject.get("height").asInt
        encondingFormat = json.get("encodingFormat").asString
    }
}