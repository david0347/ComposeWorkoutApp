package com.example.workoutapp.composables

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle.State
import androidx.navigation.NavController
import com.example.workoutapp.viewModel.WorkoutState

@Composable
fun StartWorkoutPageScaffold(navController: NavController, state: WorkoutState){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { startWorkoutPage(state) }
    )
}

@Composable
fun startWorkoutPage(state : WorkoutState){
    Header(text = state.weekday)
}
