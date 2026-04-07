package com.example.myprofileapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    // Bottom Nav Screens
    object NoteList    : Screen("note_list")
    object Favorites   : Screen("favorites")
    object Profile     : Screen("profile")

    // Detail Screens
    object NoteDetail  : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object AddNote     : Screen("add_note")
    object EditNote    : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)

val bottomNavItems = listOf(
    BottomNavItem("Notes",     Icons.Default.Note,     Screen.NoteList),
    BottomNavItem("Favorites", Icons.Default.Favorite, Screen.Favorites),
    BottomNavItem("Profile",   Icons.Default.Person,   Screen.Profile)
)