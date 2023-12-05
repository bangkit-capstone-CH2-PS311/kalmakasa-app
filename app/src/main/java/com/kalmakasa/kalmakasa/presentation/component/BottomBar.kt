package com.kalmakasa.kalmakasa.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kalmakasa.kalmakasa.presentation.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Outlined.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "History",
                icon = Icons.Outlined.HistoryEdu,
                screen = Screen.History
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Outlined.Person,
                screen = Screen.Profile
            )
        )

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }

}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen,
)

