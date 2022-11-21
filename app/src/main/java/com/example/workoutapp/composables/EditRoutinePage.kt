package com.example.workoutapp.composables

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun EditRoutineScaffold(navController: NavController){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { EditRoutine() }
    )
}

@Composable
fun EditRoutine(){
    Header(text = "Edit Routine")
}