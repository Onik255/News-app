package com.example.everything.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("news_table")
data class NewsEntity(
    val author: String,
    @PrimaryKey val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
) : Serializable

fun NewsEntity.toNewsClient(): News {
    return News(
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt
    )
}