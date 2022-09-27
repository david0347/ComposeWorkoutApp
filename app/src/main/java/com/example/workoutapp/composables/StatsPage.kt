package com.example.workoutapp.composables

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun StatsPageScaffold(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { StatsPage()}
    )
}

@Composable
fun StatsPage(){
    Header(text = "Statistics")
}