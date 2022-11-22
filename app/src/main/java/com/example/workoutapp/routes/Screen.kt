package com.example.workoutapp.routes

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object StartWorkoutScreen : Screen("start_workout_screen")
    object CreateRouteScreen : Screen("create_route_screen")
    object EditRoutineScreen : Screen("edit_routine_screen")
    object StatsScreen : Screen("stats_screen")
    object DayOfWeekScreen : Screen("day_of_week_screen")
    object SettingsScreen : Screen("settings_screen")
    object DeleteConfirmScreen : Screen("delete_confirm_screen")


}
