package com.example.workoutapp.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle.State
import androidx.navigation.NavController
import com.example.workoutapp.viewModel.WorkoutState
import com.example.workoutapp.viewModel.getParsedWorkout

@Composable
fun StartWorkoutPageScaffold(navController: NavController, state: WorkoutState, context : Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { startWorkoutPage(state, context) }
    )
}

@Composable
fun startWorkoutPage(state : WorkoutState, context: Context){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(text = state.weekday)
        testWorkouts(
            state = state,
            context = context)

    }
}

@Composable
fun testWorkouts(state : WorkoutState, context: Context){
    getParsedWorkout(context, state)
    var testing = listOf("animal", "bird")
    Log.d("testing", state.workouts.toString())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ){
        items(state.workouts){ workout ->
            Log.d("Test", workout)
            Text(text = workout)
        }
    }
    Text("Test")
}