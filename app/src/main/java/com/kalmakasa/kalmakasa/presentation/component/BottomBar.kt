package com.kalmakasa.kalmakasa.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PendingActions
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.presentation.Screen

@Composable
fun BottomBar(
    navigationItems: List<NavigationItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier,
    ) {
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.screen.route)
                            item.icon else item.iconOutline,
                        contentDescription = item.title,
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(Screen.Launcher.route) {
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

@Composable
fun AppBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home,
            iconOutline = Icons.Outlined.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.consultation),
            icon = Icons.Default.QuestionAnswer,
            iconOutline = Icons.Outlined.QuestionAnswer,
            screen = Screen.ListConsultant
        ),
        NavigationItem(
            title = stringResource(R.string.reservations),
            icon = Icons.Default.Schedule,
            iconOutline = Icons.Outlined.Schedule,
            screen = Screen.ListReservation
        ),
        NavigationItem(
            title = stringResource(R.string.profile),
            icon = Icons.Default.Person,
            iconOutline = Icons.Outlined.Person,
            screen = Screen.Profile
        )
    )
    BottomBar(navigationItems, navController, modifier)
}

@Composable
fun ConsultantBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = "Appointment",
            icon = Icons.Default.PendingActions,
            iconOutline = Icons.Outlined.PendingActions,
            screen = Screen.ListAppointment
        ),
        NavigationItem(
            title = "Patients",
            icon = Icons.Default.Groups,
            iconOutline = Icons.Outlined.Groups,
            screen = Screen.ListPatient
        ),
        NavigationItem(
            title = stringResource(R.string.profile),
            icon = Icons.Default.Person,
            iconOutline = Icons.Outlined.Person,
            screen = Screen.ProfileConsultant
        ),
    )
    BottomBar(navigationItems, navController, modifier)
}


data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val iconOutline: ImageVector,
    val screen: Screen,
)

