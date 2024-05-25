package com.example.quotes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quotes.ui.favourites.presentation.FavouritesScreen
import com.example.quotes.ui.home.presentation.screen.HomeScreen
import com.example.quotes.ui.search.presentation.screens.SearchAdviceScreen


// the class that describes the various bottom bar screens
sealed class BottomBarScreen(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    data object Home: BottomBarScreen(
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    data object Search: BottomBarScreen(
        route = "search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    data object Favourites: BottomBarScreen(
        route = "favourites",
        selectedIcon = Icons.Filled.BookmarkAdd,
        unselectedIcon = Icons.Outlined.BookmarkAdd
    )
}

// the list of screens
val items = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Search,
    BottomBarScreen.Favourites
)

// creating the bottom Navigation with the controller
@Composable
fun BottomNavigationScreens(
    modifier: Modifier,
    bottomBarNavController: NavHostController,
) {
    NavHost(
        navController = bottomBarNavController,
        modifier = modifier,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchAdviceScreen()
        }
        composable(route = BottomBarScreen.Favourites.route) {
            FavouritesScreen()
        }
    }
}

/*
* A composable function with a scaffold that houses the scaffold for the bottom Bar*/
@Composable
fun AppMain() {
    val bottomBarNavController = rememberNavController()
    val navBackStackEntry by bottomBarNavController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.height(65.dp)
            ) {
                items.forEach { item ->
                    val isSelected = item.route == navBackStackEntry?.destination?.route
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        selected = isSelected,
                        onClick = {
                            bottomBarNavController.navigate(item.route) {
                                popUpTo(bottomBarNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        BottomNavigationScreens(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            bottomBarNavController = bottomBarNavController
        )
    }
}