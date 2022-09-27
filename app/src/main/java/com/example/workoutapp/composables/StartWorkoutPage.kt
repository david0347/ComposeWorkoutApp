package com.example.workoutapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun StartWorkoutPageScaffold(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { startWorkoutPage() }
    )
}

@Composable
fun startWorkoutPage(){
    Header(text = "Start Workout")
}
