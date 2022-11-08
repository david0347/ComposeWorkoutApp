package com.example.workoutapp.viewModel

import androidx.compose.ui.geometry.Size
import com.example.workoutapp.composables.WorkoutSegment

data class WorkoutState(
    var weekday : String = getWeekDay(),
    var routineNameTextField : String =  "",
    var dayOfWeekTextField : String = "",
    var workoutNamesTextField : String = "",
    var workouts : List<String> = listOf(),
    var workoutSegments : List<WorkoutSegment> = mutableListOf()
)
