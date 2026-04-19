package com.example.newsreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.BuildConfig
import com.example.newsreader.data.api.NewsApiService
import com.example.newsreader.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository(NewsApiService())
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init { fetchArticles() }

    fun fetchArticles() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            android.util.Log.d("NewsDebug", "API Key: ${BuildConfig.NEWS_API_KEY}")
            repository.getArticles()
                .onSuccess {
                    android.util.Log.d("NewsDebug", "Success: ${it.size} articles")
                    _uiState.value = NewsUiState.Success(it)
                }
                .onFailure {
                    android.util.Log.e("NewsDebug", "Error: ${it.message}")
                    _uiState.value = NewsUiState.Error(it.message ?: "Error")
                }
        }
    }
}