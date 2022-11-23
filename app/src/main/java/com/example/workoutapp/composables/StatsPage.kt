package com.example.workoutapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.viewModel.*


//StatsPageScaffold that includes the navbar
@Composable
fun StatsPageScaffold(navController: NavController, state : WorkoutState){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { StatsPage(state)}
    )
}

//Column to hold all the data on the page
@Composable
fun StatsPage(state : WorkoutState){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(text = "Statistics")
        Spacer(modifier = Modifier.padding(top = 10.dp))
        StatisticsCards(state)
    }
}

//Workout cards to hold all the data including routine and workout info
//Since I moved the coroutine call to the onclick function this page now works properly!
@Composable
fun StatisticsCards(state: WorkoutState){

    //Lazy Column to create each card
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Loop through all the routineWithWorkout arrays
        items(state.routineWithWorkout){routineWithWorkout ->
            //Return parsed workout names such as "squats" "leg Press" ...
            val parsedWorkouts = parsedWorkoutNames(routineWithWorkout.routine.workout, state)

            //Box to add border and hold the columns of data
            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .clip(RoundedCornerShape(10.dp))
                    .border(shape = RoundedCornerShape(10.dp), color = darkBlue, width = 3.dp),
            ){
                //Column to hold all the data in the workout
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //This row displays the header for each card
                    RoutineInfoRow(routineWithWorkout.routine)

                    //Loop through the different routines separated by dates
                    for(workouts in routineWithWorkout.workouts){
                        Text(
                            modifier = Modifier
                                .background(Color.DarkGray)
                                .fillMaxWidth(),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            text = workouts.dayOfWorkout)

                        //Uses labels from DaysOfWeek composables
                        labels()

                        //Parse the workout data string into sets, reps, and weight blocks
                        parsedWorkouts(workouts.workoutInfo, state)

                        //Loop through the workout info
                        state.workoutInfo.forEachIndexed{workoutIndex, workout ->
                            //divide the blocks of data "1,2,3" into "1","2","3"
                            val parsedWorkoutInfo = parsedWorkoutNames(workout, state)

                            //Composable from DaysOfWeekScreen, it just displays the data formatted
                            WorkoutInfo(
                                parsedWorkouts[workoutIndex],
                                parsedWorkoutInfo[0],
                                parsedWorkoutInfo[1],
                                parsedWorkoutInfo[2],
                                workoutIndex
                                )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
        item{ Spacer(modifier = Modifier.padding(bottom = 60.dp))}
    }
}

//Creates the routine header
@Composable
fun RoutineInfoRow(routine : Routine){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = lightBlue),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoutineNameTextCustomization("  " + routine.dayAssigned.lowercase())
        RoutineNameTextCustomization(routine.routineName)
        RoutineNameTextCustomization(routine.dayOfCreation + "  ")
    }
}

//A text format I liked that I had to use 3 times so I made a composable for it
@Composable
fun RoutineNameTextCustomization(text : String){
    Text(
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        fontSize = 15.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        text = text
    )
}