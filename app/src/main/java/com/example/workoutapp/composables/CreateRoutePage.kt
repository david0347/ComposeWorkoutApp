package com.example.workoutapp.composables

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CreateRouteScaffold(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { testing() }
    )
}

@Composable
fun testing(){
    Header(text = "Create Routine")
}