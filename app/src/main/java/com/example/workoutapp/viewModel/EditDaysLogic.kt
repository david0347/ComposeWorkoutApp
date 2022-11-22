package com.example.workoutapp.viewModel

import android.content.Context
import com.example.workoutapp.RoutineDatabase

suspend fun deleteRoutine(
    state : WorkoutState,
    context : Context
){
    var dao = RoutineDatabase.getInstance(context).routineDao

    dao.deleteRoutineFromRoutineTable(state.routineName)
    dao.deleteRoutineFromWorkoutTable(state.routineName)
}