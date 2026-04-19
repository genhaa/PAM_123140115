package com.example.newsreader.data.api

import com.example.newsreader.BuildConfig
import com.example.newsreader.data.model.Article
import com.example.newsreader.data.model.NewsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NewsApiService {
    private val apiKey = BuildConfig.NEWS_API_KEY

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getArticles(): List<Article> {
        val response: NewsResponse = client.get("https://gnews.io/api/v4/top-headlines") {
            parameter("category", "general")
            parameter("lang", "id")
            parameter("country", "id")
            parameter("apikey", apiKey)
        }.body()
        return response.articles
    }
}