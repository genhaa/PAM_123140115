package com.example.newsreader.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.newsreader.ui.screens.*
import com.example.newsreader.viewmodel.NewsViewModel

@Composable
fun NewsNavigation(viewModel: NewsViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list") {
        composable("list") { ArticleListScreen(viewModel) { id -> navController.navigate("detail/$id") } }
        composable("detail/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ArticleDetailScreen(id, viewModel) { navController.popBackStack() }
        }
    }
}