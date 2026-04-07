package com.example.myprofileapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myprofileapp.screens.AddNoteScreen
import com.example.myprofileapp.screens.EditNoteScreen
import com.example.myprofileapp.screens.FavoritesScreen
import com.example.myprofileapp.screens.NoteDetailScreen
import com.example.myprofileapp.screens.NoteListScreen
import com.example.myprofileapp.screens.ProfileScreen
import com.example.myprofileapp.viewmodel.NoteViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    noteViewModel: NoteViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NoteList.route,
        modifier = modifier
    ) {
        // ── Bottom Nav Screens ──────────────────────────────────
        composable(Screen.NoteList.route) {
            NoteListScreen(
                viewModel = noteViewModel,
                onNoteClick = { noteId ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                },
                onAddClick = {
                    navController.navigate(Screen.AddNote.route)
                }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = noteViewModel,
                onNoteClick = { noteId ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(viewModel = profileViewModel)
        }

        // ── Detail Screens ──────────────────────────────────────
        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            NoteDetailScreen(
                noteId = noteId,
                viewModel = noteViewModel,
                onEditClick = { navController.navigate(Screen.EditNote.createRoute(noteId)) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.AddNote.route) {
            AddNoteScreen(
                viewModel = noteViewModel,
                onBackClick = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            EditNoteScreen(
                noteId = noteId,
                viewModel = noteViewModel,
                onBackClick = { navController.popBackStack() },
                onSaved = {
                    navController.popBackStack(Screen.NoteDetail.createRoute(noteId), inclusive = false)
                }
            )
        }
    }
}