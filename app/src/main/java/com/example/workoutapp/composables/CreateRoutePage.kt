package com.example.workoutapp.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.viewModel.WorkoutState
import com.example.workoutapp.viewModel.WorkoutViewModel
import com.example.workoutapp.viewModel.addToDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


//Takes in a navcontroller for navigation
//Takes in a state to update the edit texts
//Takes in a viewModel to call functions inside of WorkoutViewModel
//This is the main composable to hold the scaffolding
@Composable
fun CreateRouteScaffold(navController: NavController,
                        state : WorkoutState,
                        viewModel: WorkoutViewModel,
                        context : Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { CreateRoutine(state = state, viewModel = viewModel,context = context) }
    )
}

//Passes state and viewModel down so that other composables can use them
//Holds a column to actually hold the composables
@Composable
fun CreateRoutine(state : WorkoutState, viewModel: WorkoutViewModel, context : Context){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(text = "Create Routine")
        InfoColumn(state = state, viewModel, context)
    }
}

//Passes state and viewModel down again
//Creates a column to hold the information within the "Create Routine" section
@Composable
fun InfoColumn(state: WorkoutState, viewModel: WorkoutViewModel, context : Context){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.8f)
    ) {
        routineName(state = state, onTextChange = viewModel::updateRoutineNameField)
        Spacer(Modifier.padding(top = 20.dp))
        dayOfWeekDropDown(state, viewModel::updateDayOfWeekField)
        Spacer(Modifier.padding(top = 20.dp))
        WorkoutNames(state = state, onTextChange = viewModel::updateWorkoutNamesField)
        Spacer(Modifier.padding(top = 20.dp))
        CreateRoutineButton(context)

    }
}

//Edit Text for Routine
@Composable
fun routineName(
    state : WorkoutState,
    onTextChange:(String)-> Unit
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        textForDesc(string = "Enter A Routine Name")

        BasicTextField(
            modifier = Modifier
                .border(width = 3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp))
                .fillMaxWidth(.8f)
                .height(30.dp),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 20.sp),
            value = state.routineNameTextField,
            onValueChange = onTextChange
        )
    }
}

@Composable
fun dayOfWeekDropDown(
    state: WorkoutState,
    onTextChange: (String) -> Unit
){

    Box(){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            textForDesc(string = "Set To A Day Of The Week")

            BasicTextField(
                modifier = Modifier
                    .border(width = 3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth(.8f)
                    .height(30.dp),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp),
                value = state.dayOfWeekTextField,
                onValueChange = onTextChange
            )

        }
    }
}

@Composable
fun WorkoutNames(
    state: WorkoutState,
    onTextChange : (String)->Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        textForDesc(string = "Enter Workout Names")

        Text(
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            text = "Enter workout names separated by commas, ex: bench,flies,..."
        )
        BasicTextField(
            modifier = Modifier
                .border(width = 3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp))
                .fillMaxWidth(.8f)
                .height(90.dp),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 20.sp),
            value = state.workoutNamesTextField,
            onValueChange = onTextChange
        )
    }
}

@Composable
fun CreateRoutineButton(context : Context){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .clip(RoundedCornerShape(15.dp))
                .height(75.dp)
                .background(lightBlue)
                .border(width = 2.dp, color = darkBlue, shape = RoundedCornerShape(15.dp))
                .clickable {
                    addToDatabase(context)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Add Routine")
        }
    }
}

@Composable
fun textForDesc(string : String){
    Text(modifier = Modifier
        .fillMaxWidth(),
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        text = string)
}