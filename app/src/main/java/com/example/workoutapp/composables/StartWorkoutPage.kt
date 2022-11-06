package com.example.workoutapp.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle.State
import androidx.navigation.NavController
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.viewModel.WorkoutState
import com.example.workoutapp.viewModel.getParsedWorkout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun StartWorkoutPageScaffold(navController: NavController, state: WorkoutState, context : Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { startWorkoutPage(state, context) }
    )
}

@Composable
fun startWorkoutPage(state : WorkoutState, context: Context){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(text = state.weekday)
        testWorkouts(
            state = state,
            context = context)

    }
}

//Composable to create a Lazy Column for all the workouts
@Composable
fun testWorkouts(state : WorkoutState, context: Context){
    //A LaunchedEffect is needed to call the function inside a composable
    //Function gets the parsed workout and sends it to state.workouts
    LaunchedEffect(state.workouts) { getParsedWorkout(context, state) }

    Spacer(modifier = Modifier.padding(top = 10.dp))
    //Lazy Column to create a scrollable column with n items
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(state.workouts){ workout ->
            WorkoutCard(workoutName = workout)
            Spacer(modifier = Modifier.padding(top = 10.dp))
        }
    }
}

//Composable to have each workout on its' own card
//Takes a string in as the workout name
//Basic UI...Needs work lol, but will work for now
@Composable
fun WorkoutCard(workoutName : String){
    Box(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .clip(RoundedCornerShape(15.dp))
            .border(width = 3.dp, color = darkBlue, shape = RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Workout name
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, color = darkBlue),
                text = workoutName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Sets")
                Text(
                    textAlign = TextAlign.Center,
                    text = "Reps")
            }

            Spacer(modifier = Modifier.padding(0.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                BasicTextField(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .border(3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp)),
                    value = "",
                    onValueChange ={} )
                BasicTextField(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .border(3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp)),
                    value = "",
                    onValueChange = {})
            }
            
            Spacer(modifier = Modifier.padding(top = 15.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Weight"
            )
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .clip(RoundedCornerShape(5.dp))
                    .border(3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp)),
                value = "",
                onValueChange = {})

            Spacer(modifier = Modifier.padding(top = 15.dp))
        }
    }
}