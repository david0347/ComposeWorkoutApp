package com.example.workoutapp.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.viewModel.WorkoutState
import com.example.workoutapp.viewModel.WorkoutViewModel



//Takes in a navcontroller for navigation
//Takes in a state to update the edit texts
//Takes in a viewModel to call functions inside of WorkoutViewModel
//This is the main composable to hold the scaffolding
@Composable
fun CreateRouteScaffold(navController: NavController, state : WorkoutState, viewModel: WorkoutViewModel){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { CreateRoutine(state = state, viewModel = viewModel) }
    )
}

//Passes state and viewModel down so that other composables can use them
//Holds a column to actually hold the composables
@Composable
fun CreateRoutine(state : WorkoutState, viewModel: WorkoutViewModel){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(text = "Create Routine")
        InfoColumn(state = state, viewModel)
    }
}

//Passes state and viewModel down again
//Creates a column to hold the information within the "Create Routine" section
@Composable
fun InfoColumn(state: WorkoutState, viewModel: WorkoutViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.8f),
        ) {
        routineName(state = state, onTextChange = viewModel::updateRoutineNameField)
    }
}

//Edit Text for Routine
@Composable
fun routineName(
    state : WorkoutState,
    onTextChange:(String)-> Unit
){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column() {
            Text(modifier = Modifier
                .fillMaxWidth(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                text = "Enter A Workout Name")

            BasicTextField(
                modifier = Modifier
                    .border(width = 3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth(.8f)
                    .height(30.dp),

                value = state.routineNameTextField,
                onValueChange = onTextChange
            )
        }
    }
}