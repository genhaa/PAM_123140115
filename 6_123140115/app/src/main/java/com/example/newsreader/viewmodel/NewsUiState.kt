package com.example.newsreader.viewmodel

import com.example.newsreader.data.model.Article

// Sealed class merepresentasikan 3 state: Loading, Success, Error
sealed class NewsUiState {
    object Loading : NewsUiState()

    data class Success(
        val articles: List<Article>
    ) : NewsUiState()

    data class Error(
        val message: String
    ) : NewsUiState()
}