package com.example.newsreader.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsreader.data.model.Article
import com.example.newsreader.viewmodel.NewsUiState
import com.example.newsreader.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(viewModel: NewsViewModel, onArticleClick: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("News Reader") }) }
    ) { padding ->
        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = uiState is NewsUiState.Loading,
            onRefresh = { viewModel.fetchArticles() },
            modifier = Modifier.padding(padding)
        ) {
            when (uiState) {
                is NewsUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is NewsUiState.Error -> {
                    val errorMsg = (uiState as NewsUiState.Error).message
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Error!! $errorMsg", color = MaterialTheme.colorScheme.error)
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = { viewModel.fetchArticles() }) {
                                Text("Coba Lagi (Retry)")
                            }
                        }
                    }
                }
                is NewsUiState.Success -> {
                    val articles = (uiState as NewsUiState.Success).articles
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(articles) { article ->
                            ArticleItem(article) { onArticleClick(articles.indexOf(article)) }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(article.title, maxLines = 1) },
        supportingContent = {
            Text(
                article.description ?: "No description",
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingContent = {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        article.source?.name?.take(2)?.uppercase() ?: "?",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    )
}