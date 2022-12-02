package com.example.workoutapp.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.material.Text
import com.example.workoutapp.RoutineDatabase
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.Workout

//Function to get all the routines and return them
suspend fun getRoutines(context : Context, state : WorkoutState){

    var dao = RoutineDatabase.getInstance(context).routineDao
    state.routinesList = dao.getAllRoutines()
}

//Sets state.routineWithWorkout to the joined routine and workout table
suspend fun getRoutinesWithWorkouts(
    context : Context,
    state : WorkoutState,
){
    var dao = RoutineDatabase.getInstance(context).routineDao

    state.routineWithWorkout = dao.getAllRoutinesAndWorkouts()
}

