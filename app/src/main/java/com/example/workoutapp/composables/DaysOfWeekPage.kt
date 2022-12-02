package com.example.workoutapp.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workoutapp.ui.theme.offWhite
import com.example.workoutapp.viewModel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun DayOfWeekScaffold(navController: NavController, state: WorkoutState, context: Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { DayOfWeek(state, context) }
    )
}

//I get an error when trying to go to a smaller day of week list
//Composable holding most of the information
//This composable gets the info parsed and displays it
@Composable
fun DayOfWeek(
    state : WorkoutState,
    context : Context
){
    //When the page is clicked on from the menu it calls the function to get the data from the database
    //It fixed the big issue with the data being a one off error
    //May be able to use this information for the statistics page
    //Main Column of information
    Column(modifier = Modifier
        .fillMaxSize())
    {
        Header(text = dayOfWeekText(state))
        DayOfWeekMessage(messageOfDay = "Your routine last time you " +
                "worked out on " +
                capitalizeFirstLetter(dayOfWeekText(state))
        )
        //If there is previous data display it
        if(state.isDayOfWeekSelected){

            //Lazy Column to display n rows of data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                //index the items and display
                item { labels() }
                itemsIndexed(state.workoutLength){ index, workouts ->
                    val parsedWorkoutInfo = state.workoutInfo[index].split(",")
                    Log.d("index", index.toString())
                    Log.d("state", state.workoutInfo.toString())
                    WorkoutInfo(workoutName = workouts,
                        sets = parsedWorkoutInfo[0],
                        reps = parsedWorkoutInfo[1],
                        weight = parsedWorkoutInfo[2],
                        index = index)
                }
            }
        }else{
            Text(modifier = Modifier
                .fillMaxWidth(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                text = "You have no previous data for " + dayOfWeekText(state).lowercase()
            )
        }
    }
}

@Composable
fun WorkoutInfo(workoutName : String, sets : String, reps : String, weight : String, index : Int){

    if(index % 2 == 0){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(offWhite)
        ){
            Text(
                modifier = Modifier
                    .fillMaxSize(.4f),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                text = workoutName
            )
            Text(
                modifier = Modifier
                    .width(60.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                text = sets
            )
            Text(
                modifier = Modifier
                    .width(60.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                text = reps
            )
            Text(
                modifier = Modifier
                    .width(60.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                text = weight
            )
        }
    }else{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ){
            Text(
                modifier = Modifier
                    .fillMaxSize(.4f),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,

                text = workoutName
            )
            Text(
                modifier = Modifier
                    .width(60.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,

                text = sets
            )
            Text(
                modifier = Modifier
                    .width(60.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                text = reps
            )
            Text(
                modifier = Modifier
                    .width(60.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                text = weight
            )
        }
    }
}

@Composable
fun labels(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .border(width = 1.dp, color = Color.Black)
    ){
        Text(
            modifier = Modifier
                .fillMaxSize(.4f),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = "Workout"
        )
        Text(
            modifier = Modifier
                .width(60.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = "Sets"
        )
        Text(
            modifier = Modifier
                .width(60.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = "Reps"
        )
        Text(
            modifier = Modifier
                .width(60.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = "Weight"
        )
    }
}