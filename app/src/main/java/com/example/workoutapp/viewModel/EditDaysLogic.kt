package com.example.workoutapp.viewModel

import android.content.Context
import com.example.workoutapp.RoutineDatabase

//Deletes the routine selected
suspend fun deleteRoutine(
    state : WorkoutState,
    context : Context
){
    var dao = RoutineDatabase.getInstance(context).routineDao

    dao.deleteRoutineFromRoutineTable(state.routineName)
    dao.deleteRoutineFromWorkoutTable(state.routineName)

    state.workouts = listOf()
    state.workoutSegments = mutableListOf()
}