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

    object Profile : Screen("profile")

    object Chatbot : Screen("chatbot")

    object Question : Screen("question/{isSkippable}") {
        fun createRoute(isSkippable: Boolean) = "question/$isSkippable"
    }

    object ListHealthTestResult : Screen("health-test-list")
    object DetailHealthTestResult : Screen("health-test-detail/{id}") {
        fun createRoute(id: String) = "health-test-detail/$id"
    }


    object ListConsultant : Screen("list-consultant")
    object ConsultantDetail : Screen("consultant-detail/{id}") {
        fun createRoute(id: String) = "consultant-detail/$id"
    }

    object ListJournal : Screen("journal-list")
    object AddJournal : Screen("journal-add")

    object ListArticle : Screen("article-list")

    object DetailArticle : Screen("article-detail/{id}") {
        fun createRoute(id: String) = "article-detail/$id"

    }

    object ListReservation : Screen("Reservation-list")
    object DetailReservation : Screen("Reservation-detail/{id}") {
        fun createRoute(id: String) = "reservation-detail/$id"
    }

    object ConsultantGraph : Screen("consultant-graph")
    object ListAppointment : Screen("appointment-list")
    object DetailAppointment : Screen("appointment-detail/{id}") {
        fun createRoute(id: String) = "appointment-detail/$id"
    }

    object ListPatient : Screen("patients-list")
    object ProfileConsultant : Screen("profile-consultant")

    companion object {
        val withBottomBar = listOf(
            Home.route,
            ListConsultant.route,
            ListReservation.route,
            Profile.route,
        )

        val consultantBottomBar = listOf(
            ListAppointment.route,
            ListPatient.route,
            ProfileConsultant.route,
        )
    }

}