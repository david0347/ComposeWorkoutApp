package com.example.workoutapp.viewModel

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import com.example.workoutapp.RoutineDatabase
import com.example.workoutapp.entities.Routine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

//This function will add a Routine to the database if the information on
//Create Routine Page is correct
suspend fun addToDatabase(context : Context, state: WorkoutState){

    //Create
    val routine : Routine = Routine(
        routineName = state.routineNameTextField,
        dayOfCreation = "10/26/2022",
        dayAssigned = state.dayOfWeekTextField,
        workout = state.workoutNamesTextField)
    //Get dao functionality
    val dao = RoutineDatabase.getInstance(context).routineDao

    //Call insertRoutine to the routine object and add to database
    dao.insertRoutine(routine)
}

//Function to return weekday string, thanks YouTube
fun getWeekDay() : String{
    val calendar = Calendar.getInstance()
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
}

//Function to get the string of workouts from the routine table based on the day of the week
//Returns a routine for the day of week
suspend fun getRoutineFromDayOfWeek(context: Context) : Routine{
    val dao = RoutineDatabase.getInstance(context).routineDao

    return dao.getWorkoutFromDate(getWeekDay())
}

//Function to parse the string from getRoutineFromDayOfWeek function
// into a string array
//Returns a string array
fun getParsedWorkout(context : Context, state : WorkoutState): List<String>{

    var parsedWorkout = listOf<String>()
    GlobalScope.launch {
        var routine = getRoutineFromDayOfWeek(context)

        parsedWorkout = routine.workout.split(",")

        Log.d("Workout for the day", parsedWorkout.toString())
    }
    state.workouts = parsedWorkout
    return parsedWorkout
}