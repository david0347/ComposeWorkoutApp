package com.example.workoutapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WorkoutViewModel: ViewModel() {
    var state by mutableStateOf(WorkoutState())

    private set

    fun updateRoutineNameField(str: String) {
        state = state.copy(routineNameTextField = str)
    }
}