package com.example.workoutapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DayOfWeekScaffold(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { DayOfWeek() }
    )
}

@Composable
fun DayOfWeek(){
    Header(text = "Days of week Screen")
}