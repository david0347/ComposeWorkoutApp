package com.example.workoutapp.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workoutapp.routes.Screen
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.viewModel.WorkoutState

@Composable
fun EditRoutineScaffold(navController: NavController, state : WorkoutState, context : Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { EditRoutine(state = state, navController = navController) }
    )
}

@Composable
fun EditRoutine(navController : NavController, state : WorkoutState){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(text = "Edit Routine")
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Routines(navController = navController, state = state)
    }
}

@Composable
fun Routines(
    navController: NavController,
    state : WorkoutState
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(state.routinesList){routine ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .clip(RoundedCornerShape(5.dp))
                    .border(color = darkBlue, shape = RoundedCornerShape(5.dp), width = 3.dp)
                    .shadow(shape = RoundedCornerShape(5.dp), elevation = 100.dp)
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                        ){
                    Text("   " + routine.routineName)
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(color = lightBlue)
                            .clickable {
                                navController.navigate(Screen.DeleteConfirmScreen.route)
                                state.routineName = routine.routineName
                            }
                            .padding(10.dp)
                    ){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            text = "Delete")
                    }

                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}