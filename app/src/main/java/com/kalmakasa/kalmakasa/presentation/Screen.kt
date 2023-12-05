package com.kalmakasa.kalmakasa.presentation

sealed class Screen(val route: String) {
    object AuthGraph : Screen("auth-graph")
    object Welcome : Screen("welcome")
    object SignIn : Screen("sign-in")

    object Launcher : Screen("launcher")
    object Register : Screen("register")

    object Home : Screen("home?isNewUser={isNewUser}") {
        fun createRoute(isNewUser: Boolean = false) = "home?isNewUser=$isNewUser"
    }

    object History : Screen("history")
    object Profile : Screen("profile")

    object Question : Screen("question/{isSkippable}") {
        fun createRoute(isSkippable: Boolean) = "question/$isSkippable"
    }

    object ListDoctor : Screen("list-doctor")
    object DoctorDetail : Screen("doctor-detail/{id}") {
        fun createRoute(id: String) = "doctor-detail/$id"
    }

    object AddJournal : Screen("journal-add")

    companion object {
        val withBottomBar = listOf(
            Home.route,
            History.route,
            Profile.route,
        )
    }

}