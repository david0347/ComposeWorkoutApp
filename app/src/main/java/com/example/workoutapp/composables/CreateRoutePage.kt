package com.example.workoutapp.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workoutapp.viewModel.WorkoutState

@Composable
fun CreateRouteScaffold(navController: NavController, state : WorkoutState){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { CreateRoutine(state = state) }
    )
}

@Composable
fun CreateRoutine(state : WorkoutState){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(text = "Create Routine")
        InfoColumn(state = state)
    }
}

@Composable
fun InfoColumn(state: WorkoutState){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.8f),
        verticalArrangement = Arrangement.SpaceAround
        ) {
        routineName(state = state, onTextChange = { })
    }
}

@Composable
fun routineName(state : WorkoutState,
onTextChange:(String)->Unit){
    BasicTextField(
        value = state.routineNameTextField,
        onValueChange = onTextChange)
}