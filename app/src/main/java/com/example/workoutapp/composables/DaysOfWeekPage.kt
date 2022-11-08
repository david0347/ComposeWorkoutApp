package com.example.workoutapp.composables

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workoutapp.viewModel.WorkoutState
import com.example.workoutapp.viewModel.capitalizeFirstLetter
import com.example.workoutapp.viewModel.dayOfWeekText
import com.example.workoutapp.viewModel.getWorkoutInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun DayOfWeekScaffold(navController: NavController, state: WorkoutState, context: Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { DayOfWeek(state, context) }
    )
}

@Composable
fun DayOfWeek(
    state : WorkoutState,
    context : Context
){
    SideEffect {
        GlobalScope.launch(Dispatchers.IO) { 
            getWorkoutInfo(state, context )
        }
    }
    Column(modifier = Modifier
        .fillMaxSize())
    {
        Header(text = dayOfWeekText(state))
        DayOfWeekMessage(messageOfDay = "Your routine last time you " +
                "worked out on " +
                capitalizeFirstLetter(dayOfWeekText(state))
        )
        if(state.isDayOfWeekSelected){
            Text(state.workoutName)
        }else{
            Text(text = "Issues with day of week")
        }
    }
}