package com.example.carousellnews.models

import com.google.gson.annotations.SerializedName

data class Article(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("banner_url")
    val bannerUrl: String,
    @SerializedName("time_created")
    val creationTime: Long,
    val rank: Int
)
