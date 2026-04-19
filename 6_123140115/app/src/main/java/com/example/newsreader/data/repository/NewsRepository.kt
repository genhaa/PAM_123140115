package com.example.newsreader.data.repository

import com.example.newsreader.data.api.NewsApiService
import com.example.newsreader.data.model.Article

class NewsRepository(private val apiService: NewsApiService) {
    suspend fun getArticles(): Result<List<Article>> {
        return try {
            Result.success(apiService.getArticles())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}