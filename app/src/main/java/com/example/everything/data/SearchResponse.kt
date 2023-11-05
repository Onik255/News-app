package com.example.everything.data

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class SearchResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<SearchResult>
) : Serializable

@Keep
data class SearchResult(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) : Serializable

@Keep
data class Source(
    val id: String?,
    val name: String?
) : Serializable

fun SearchResult.toNewsClient(): News? {
    return News(
        author ?: return null,
        title ?: return null,
        description ?: return null,
        url ?: return null,
        urlToImage ?: return null,
        publishedAt ?: return null
    )
}