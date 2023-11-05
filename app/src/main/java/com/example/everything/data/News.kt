package com.example.everything.data

import java.io.Serializable

data class News(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
) : Serializable

fun News.toNewsEntity(): NewsEntity {
    return NewsEntity(
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt
    )
}