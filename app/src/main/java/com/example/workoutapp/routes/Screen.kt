package com.example.workoutapp.routes

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object StartWorkoutScreen : Screen("start_workout_screen")
    object CreateRouteScreen : Screen("create_route_screen")
    object SetDaysScreen : Screen("set_days_screen")
    object StatsScreen : Screen("stats_screen")
    object DayOfWeekScreen : Screen("day_of_week_screen")
    object SettingsScreen : Screen("settings_screen")


}
