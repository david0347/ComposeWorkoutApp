package com.example.workoutapp.viewModel

import android.content.Context
import android.util.Log
import com.example.workoutapp.RoutineDatabase

//Convert the list used to put a string on every button into a weekday
fun dayOfWeekText(state : WorkoutState) : String{

    return when(state.dayOfWeekSelected){
        "M"-> "MONDAY"
        "T"-> "TUESDAY"
        "W"-> "WEDNESDAY"
        "TH" -> "THURSDAY"
        "FR" -> "FRIDAY"
        "SA" -> "SATURDAY"
        else -> "SUNDAY"
    }
}

//Supposed to capitalize the first letter, instead just lower casing everything
//Will get back to later
fun capitalizeFirstLetter(day: String): String {
    var lowercase = day.lowercase()
    lowercase[0].uppercase()
    return lowercase
}

//Works to an extent
//So far it gets the routine and workout needed for the day and if it can not be retrieved
//It changes state.isDayOfWeekSelected to false, else true
//Use this to set up UI for 2 different outcomes
//This function will also send the routine name and workout info to state
suspend fun getWorkoutInfo(
    state : WorkoutState,
    context: Context,
) {
    var dao = RoutineDatabase.getInstance(context).routineDao

    try {
        //Gets the routine to enter when trying to get routineWithWorkout
        var routineDay = dao.getWorkoutFromDate(dayOfWeekText(state))

        //Gets the routineWithWorkout object
        var routineWithWorkout = dao.getRoutineWithWorkouts(routineDay.routineName)

        //Gets the routine out of the object
        var routine = routineWithWorkout.last().routine
        //Gets the workout out of the object
        var workout = routineWithWorkout.last().workouts.last()
        parsedWorkoutNames(routine.workout, state)
        parsedWorkouts(workout.workoutInfo, state)
        state.workoutName = routine.routineName
        state.isDayOfWeekSelected = true
    }catch (e : java.lang.Exception){

        state.isDayOfWeekSelected = false
    }
}

//returns a list of the workout names
fun parsedWorkoutNames(workoutNames : String, state: WorkoutState) : List<String>{
    var workouts = workoutNames.split(",")

    state.workoutLength = workouts
    Log.d("workouts parsed", workouts.toString())
    return workouts
}

//Returns a mutable list that looks like "1,2,3","1,2,3"(Sets, reps, and weight)
fun parsedWorkouts(workoutData : String, state: WorkoutState) : MutableList<String>{
    var workouts : MutableList<String> = workoutData.split("|").toMutableList()
    workouts.removeLast()
    Log.d("workout data parsed", workouts.toString())

    state.workoutInfo = workouts
    return workouts
}

