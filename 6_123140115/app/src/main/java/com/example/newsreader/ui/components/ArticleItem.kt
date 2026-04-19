package com.example.newsreader.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsreader.data.model.Article

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(article.title, maxLines = 2) },
        supportingContent = {
            Text(
                article.description ?: "No description",
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingContent = {
            Surface(
                modifier = Modifier.size(56.dp),
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
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}