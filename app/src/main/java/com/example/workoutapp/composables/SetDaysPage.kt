package com.example.workoutapp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SetDaysScaffold(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { SetDays() }
    )
}

@Composable
fun SetDays(){
    Header(text = "Set Days")
}