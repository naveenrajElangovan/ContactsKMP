package com.example.contactssample.android

import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder

class JsonParser {
    private val gson = Gson()


    fun <T> toJson(type : T) : String{
        val encodedJson = URLEncoder.encode(gson.toJson(type), "UTF-8")

        return gson.toJson(type)
    }

    fun <T> fromJson(json: String, type: Class<T>): T {
//        val decodedJson = URLDecoder.decode(json, "UTF-8")
        return gson.fromJson(json, type)
    }

    fun <T> fromJsonArray(json: String, type: Class<Array<T>>): Array<T> {
//        val decodedJson = URLDecoder.decode(json, "UTF-8")
        return gson.fromJson(json, type)
    }
}