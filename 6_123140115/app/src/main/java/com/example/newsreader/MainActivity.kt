package com.example.newsreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsreader.ui.NewsNavigation
import com.example.newsreader.ui.theme.NewsReaderTheme
import com.example.newsreader.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsReaderTheme {
                val viewModel: NewsViewModel = viewModel()
                NewsNavigation(viewModel = viewModel)
            }
        }
    }
}