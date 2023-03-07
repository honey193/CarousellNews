package com.example.carousellnews.repository

import com.example.carousellnews.models.Article
import com.google.gson.Gson

class JSONParser(private val gson: Gson) {

    fun parseJsonString(jsonString: String?): Array<Article> {
        return gson.fromJson(jsonString, Array<Article>::class.java)
    }
}
