package com.example.workoutapp.viewModel

import android.content.Context
import android.util.Log
import com.example.workoutapp.RoutineDatabase
import com.example.workoutapp.entities.Routine
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