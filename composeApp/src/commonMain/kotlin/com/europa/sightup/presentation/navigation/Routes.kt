package com.europa.sightup.presentation.navigation

/** Each route has to have a unique name.
    On RootScreen we will defined the main tabs and the onboarding screen
    Below on leafScreen we defined the screens inside each main tab
**/

sealed class RootScreen(val route: String) {
    data object Home : RootScreen("home_root")
    data object Exercise: RootScreen("exercise_root")
    data object Test : RootScreen("test_root")
    data object Record : RootScreen("record_root")
    data object Account : RootScreen("account_root")
    data object Onboarding : RootScreen("onboarding_root")
}

sealed class LeafScreen(val route: String) {
    data object Home : LeafScreen("home")
    data object HomeExample : LeafScreen("home_example")


    data object Exercise : LeafScreen("exercise")
    data object ExerciseDetail : LeafScreen("exercise_detail")

    data object Test : LeafScreen("test")
    // Add in here nested routes for each screen

    data object Record : LeafScreen("record")

    data object Account : LeafScreen("account")

    data object Onboarding : LeafScreen("onboarding")
}

/** After a new route is added it has to be called from the NavigationGraph.kt file **/