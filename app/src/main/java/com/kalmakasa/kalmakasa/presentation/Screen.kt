package com.kalmakasa.kalmakasa.presentation

sealed class Screen(val route: String) {
    data object AuthGraph : Screen("auth-graph")
    data object Welcome : Screen("welcome")
    data object SignIn : Screen("sign-in")

    data object Launcher : Screen("launcher")
    data object Register : Screen("register")

    data object Home : Screen("home?isNewUser={isNewUser}") {
        fun createRoute(isNewUser: Boolean = false) = "home?isNewUser=$isNewUser"
    }

    data object Profile : Screen("profile")

    data object Chatbot : Screen("chatbot")

    data object Question : Screen("question/{isSkippable}") {
        fun createRoute(isSkippable: Boolean) = "question/$isSkippable"
    }

    data object ListHealthTestResult : Screen("health-test-list")
    data object DetailHealthTestResult : Screen("health-test-detail/{id}") {
        fun createRoute(id: String) = "health-test-detail/$id"
    }


    data object ListConsultant : Screen("list-consultant")
    data object ConsultantDetail : Screen("consultant-detail/{id}") {
        fun createRoute(id: String) = "consultant-detail/$id"
    }

    data object ListJournal : Screen("journal-list")
    data object AddJournal : Screen("journal-add")

    data object ListArticle : Screen("article-list")

    data object DetailArticle : Screen("article-detail/{id}") {
        fun createRoute(id: String) = "article-detail/$id"

    }

    data object ListReservation : Screen("Reservation-list")
    data object DetailReservation : Screen("Reservation-detail/{id}") {
        fun createRoute(id: String) = "reservation-detail/$id"
    }

    data object ConsultantGraph : Screen("consultant-graph")
    data object ListAppointment : Screen("appointment-list")
    data object DetailAppointment : Screen("appointment-detail/{id}") {
        fun createRoute(id: String) = "appointment-detail/$id"
    }

    data object ListPatient : Screen("patients-list")
    data object ListPatientAppointment : Screen("patients-appointment-list/{id}") {
        fun createRoute(id: String) = "patients-appointment-list/$id"

    }

    data object ProfileConsultant : Screen("profile-consultant")

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