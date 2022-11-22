package com.example.workoutapp.viewModel

import androidx.compose.ui.geometry.Size
import com.example.workoutapp.composables.WorkoutSegment
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.Workout

data class WorkoutState(
    var weekday : String = getWeekDay(),
    //variables to edit textfields in routinePage
    var routineNameTextField : String =  "",
    var dayOfWeekTextField : String = "",
    var workoutNamesTextField : String = "",
    //variables to edit textfields in startWorkoutPage
    var workouts : List<String> = listOf(),
    var workoutSegments : List<WorkoutSegment> = mutableListOf(),
    var hasWorkedOut : Boolean = false,
    //variables to edit data in DaysOfWeekPage
    var dayOfWeekSelected : String = "",
    var isDayOfWeekSelected : Boolean = false,
    var workoutName : String = "",
    var workoutNames : String = "",
    var workoutInfo : List<String> = listOf(),
    var workoutLength : List<String> = listOf(),
    //variables for statistics
    var routinesList : MutableList<Routine> = mutableListOf(),
    var workoutsList : MutableList<Workout> = mutableListOf(),
    var isRoutineRetrieved : Boolean = false,
    var isWorkoutRetrieved : Boolean = false,
    //variables for EditRoutinePage
    var routineName : String = ""
)
