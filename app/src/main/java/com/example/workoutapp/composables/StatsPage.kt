package com.example.workoutapp.composables

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import android.view.WindowInsets.Side
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.viewModel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.logging.Handler

@Composable
fun StatsPageScaffold(navController: NavController, state : WorkoutState, context : Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { StatsPage(state, context)}
    )
}

@Composable
fun StatsPage(state : WorkoutState, context: Context){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(text = "Statistics")
        Spacer(modifier = Modifier.padding(top = 10.dp))
        //Routines(state, context)
        testColumn(context = context, state = state)
    }
}

@Composable
fun Routines(state : WorkoutState, context : Context){

    //Side effect calls getRoutines which returns all the routines in state
    SideEffect {
        GlobalScope.launch(Dispatchers.IO) {
            getRoutines(context, state)
            delay(1000L)
            //getAllWorkouts(context, state, state.routinesList[0].routineName)
        }
    }

    LazyColumn(

    ) {
        //Loops through the routinesList which is created by above side effect
        itemsIndexed(state.routinesList) { index, routines ->
            //This side effect calls getAllWorkouts which sets state based on routine name
            SideEffect {
                GlobalScope.launch(Dispatchers.Unconfined) {
                    getAllWorkouts(context, state, routines.routineName)
                    delay(2000L)
                    //Log.d("Routine", routines.routineName)
                    //Log.d("workout day", state.workoutsList.toString())
                    Log.d("index", index.toString())
                }
            }

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .border(shape = RoundedCornerShape(5.dp), color = darkBlue, width = 3.dp)
                    .fillMaxWidth(.95f)
            ) {
                Column(

                ) {

                    RoutineInformation(routine = routines)
                    Text(routines.routineName)

                    //Log.d("Workout List", state.workoutsList.toString())

                    //The issue is that this runs before the call to getAllWorkouts
                    //above somehow
                    Log.d("index outside of for", index.toString())
                    while (state.workoutsList.isNotEmpty()){

                        Log.d("index in for", index.toString())
                        Text(state.workoutsList[0].dayOfWorkout)
                        state.workoutsList.removeAt(0)
                        //Log.d("Workout List While", state.workoutsList.toString())

                    }
                    //for(workouts in state.workoutsList){

                      //  Log.d("Workout Date",workouts.dayOfWorkout)
                      //  Text(workouts.dayOfWorkout)
                    //}
                    Text(routines.routineName)

                }


            }
            Spacer(modifier = Modifier.padding(top = 10.dp))
        }
    }
}


@Composable
fun RoutineInformation(routine : Routine){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "   " + routine.dayOfCreation,
            fontSize = 20.sp
        )
        Text(
            text = routine.routineName,
            fontSize = 20.sp
        )
        Text(
            text = routine.dayAssigned.lowercase() + "   ",
            fontSize = 20.sp
        )
    }
}

//Test Column to try and get the data to not be one off
//Half successful, the one off error has to do with the coroutines and I semi fixed it by
//changing the Dispatcher to unconfined for the second coroutine then delaying the threads
//Works maybe 70% of the time, still have to find a solid fix for it, but it will work for now
@Composable
fun testColumn(context: Context, state : WorkoutState){
    LazyColumn(

    ){
        item {
            //Side effect calls getRoutines which returns all the routines in state
            //I do not need the SideEffect and I can use this line below, not sure how it effects
            //accuracy
            //GlobalScope.launch (Dispatchers.IO){ getRoutines(context, state)
            //delay(10000L)}
            SideEffect {
                GlobalScope.launch(Dispatchers.IO) {
                    getRoutines(context, state)
                    getAllWorkouts(context, state, state.routinesList[0].routineName)
                    //The delay changes the results! I have to mess around more with this
                    //to understand how to fix it
                    delay(10000L)
                }
            }

            //For loop to go through the all the routines
            for(routines in state.routinesList){

                //Global Scope to get all workouts based on routine name
                GlobalScope.launch(Dispatchers.Unconfined) { getAllWorkouts(context, state, routines.routineName)
                    //Delay which improves accuracy, have to test it more
                delay(10000000L) }

                //Get the workout names affiliated with each routine
                var routineNames = routines.workout
                var parsedRoutines = parsedWorkoutNames(routineNames, state)

                //Box to wrap all content in
                Box (
                      modifier = Modifier
                          .fillMaxWidth(.95f)
                          .clip(RoundedCornerShape(5.dp))
                          .border(color = darkBlue, shape = RoundedCornerShape(5.dp), width = 3.dp)
                        ){
                    //Column to go inside box
                    Column() {
                        //Routine name
                        Text(routines.routineName)
                        //Log.d("workouts List length", state.workoutsList.size.toString())

                        //For loop to go through each workout based on date
                        for(workouts in state.workoutsList){
                            //Print date of workout
                            Text(workouts.dayOfWorkout)

                            //Parse workout data into "1,2,3" blocks for each workout
                            var workouts = parsedWorkouts(workouts.workoutInfo, state)
                            Log.d("parsed workout", parsedRoutines.toString())

                            //For each workout print the workout name and the information
                            workouts.forEachIndexed{index, workoutInfo ->
                                //Parse the data into an array
                                var workoutInformation = parsedWorkoutNames(workoutInfo, state)

                                //Print workout data to screen. Problem with parsedRoutines Index
                                Text( parsedRoutines[index] + workoutInformation[0] + workoutInformation[1] + workoutInformation[2])
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))
            }
        }
        item { Spacer(modifier = Modifier.padding(bottom = 60.dp)) }
    }
}