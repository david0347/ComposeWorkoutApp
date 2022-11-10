package com.example.workoutapp.composables

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workoutapp.viewModel.*
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

//Composable holding most of the information
//This composable gets the info parsed and displays it
@Composable
fun DayOfWeek(
    state : WorkoutState,
    context : Context
){
    //Get the workout info and see if data exists for last workout
    SideEffect {
        GlobalScope.launch(Dispatchers.IO) { 
            getWorkoutInfo(state, context )
        }
    }
    //Main Column of information
    Column(modifier = Modifier
        .fillMaxSize())
    {
        Header(text = dayOfWeekText(state))
        DayOfWeekMessage(messageOfDay = "Your routine last time you " +
                "worked out on " +
                capitalizeFirstLetter(dayOfWeekText(state))
        )
        //If there is previous data display it
        if(state.isDayOfWeekSelected){

            //Lazy Column to display n rows of data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                //index the items and display
                itemsIndexed(state.workoutLength){ index, workouts ->
                    var parsedWorkoutInfo = state.workoutInfo[index].split(",")
                    Text(workouts + "sets: " + parsedWorkoutInfo[0]
                            + "reps: " + parsedWorkoutInfo[1]
                            + "weight: " + parsedWorkoutInfo[2])
                }
            }
        }else{
            Text(text = "Issues with day of week")
        }
    }
}