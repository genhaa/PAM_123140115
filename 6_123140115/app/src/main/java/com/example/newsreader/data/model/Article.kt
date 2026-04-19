package com.example.newsreader.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val title: String = "",
    val description: String? = null,
    val content: String? = null,
    val url: String = "",
    val image: String? = null,
    val publishedAt: String = "",
    val source: Source? = null
)

@Serializable
data class Source(
    val name: String = "",
    val url: String = ""
)

@Serializable
data class NewsResponse(
    val totalArticles: Int = 0,
    val articles: List<Article> = emptyList()
)