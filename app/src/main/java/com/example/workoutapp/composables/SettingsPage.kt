package com.example.workoutapp.composables

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SettingsPageScaffold(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController)},
        content = { Settings() }
    )
}

@Composable
fun Settings(){
    Header(text = "Settings")
}