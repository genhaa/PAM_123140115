package com.example.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myprofileapp.navigation.AppNavGraph
import com.example.myprofileapp.navigation.Screen
import com.example.myprofileapp.navigation.bottomNavItems
import com.example.myprofileapp.ui.theme.*
import com.example.myprofileapp.viewmodel.NoteViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val noteViewModel: NoteViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    MainAppContent(
        navController = navController,
        noteViewModel = noteViewModel,
        profileViewModel = profileViewModel
    )
}

@Composable
fun MainAppContent(
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    profileViewModel: ProfileViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = setOf(
        Screen.NoteList.route,
        Screen.Favorites.route,
        Screen.Profile.route
    )
    val showBottomNav = currentRoute in bottomNavRoutes

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NavigationBar(
                    containerColor = WhiteSage,
                    tonalElevation = androidx.compose.ui.unit.Dp.Unspecified
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.screen.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    // Pop up to start destination to avoid stacking
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = HotPink,
                                selectedTextColor = HotPink,
                                indicatorColor = SoftSage,
                                unselectedIconColor = BoldSage,
                                unselectedTextColor = BoldSage
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            noteViewModel = noteViewModel,
            profileViewModel = profileViewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    MyProfileAppTheme {
        val navController = rememberNavController()
        val noteViewModel: NoteViewModel = viewModel()
        val profileViewModel: ProfileViewModel = viewModel()
        MainAppContent(
            navController = navController,
            noteViewModel = noteViewModel,
            profileViewModel = profileViewModel
        )
    }
}