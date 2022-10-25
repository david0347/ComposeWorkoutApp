package com.example.workoutapp.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workoutapp.routes.Screen

/*
Navigation page to set up routes between all the composable pages
We call navigation in MainActivity with no parameters and navigation
uses the navController to determine where to start.
*/

@Composable
fun Navigation(){
    //get the navigation information, rememberNavController returns NavHostController
    val navController = rememberNavController()
    //Getting context for the database(this works if you pass through the nav composables)
    val context = LocalContext.current
    //Call NavHost and pass the navController as well as start destination
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        //Home screen composable
        composable(route = Screen.HomeScreen.route){
            HomePageScaffold(navController)
        }
        //Create route screen composable
        composable(route = Screen.CreateRouteScreen.route){
            CreateRouteScaffold(navController)
        }
        //Statistics page screen composable
        composable(route = Screen.StatsScreen.route){
            StatsPageScaffold(navController)
        }
        //Settings page screen composable
        composable(route = Screen.SettingsScreen.route){
            SettingsPageScaffold(navController, context)
        }
        //Start workout screen composable
        composable(route = Screen.StartWorkoutScreen.route){
            StartWorkoutPageScaffold(navController)
        }
        //Set day screen composable
        composable(route = Screen.SetDaysScreen.route){
            SetDaysScaffold(navController)
        }
        //Set days of week screen composable
        composable(route = Screen.DayOfWeekScreen.route){
            DayOfWeekScaffold(navController)
        }
    }
}

