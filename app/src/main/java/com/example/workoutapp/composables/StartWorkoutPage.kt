package com.example.workoutapp.composables

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.max
import com.example.workoutapp.routes.Screen
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.viewModel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//Composable called in navigation
@Composable
fun StartWorkoutPageScaffold(navController: NavController, state: WorkoutState, context : Context){
    Scaffold(
        bottomBar = { BottomNavBar(navController)},
        content = { startWorkoutPage(state, context, navController) }
    )
}

//Composable to hold all other composables in a column
@Composable
fun startWorkoutPage(state : WorkoutState, context: Context, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.9f)
    ) {
        Header(text = state.weekday)
        //New
        SideEffect {
            GlobalScope.launch(Dispatchers.IO) {
                if(hasWorkedOutToday(context)){

                }

            }
        }
        WorkoutList(
            state = state,
            context = context,
            navController = navController)

    }
}

//Composable to create a Lazy Column for all the workouts
@Composable
fun WorkoutList(state : WorkoutState, context: Context, navController : NavController){
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
        //I used itemsIndexed instead of items because I need to return the index
        //of each card to hold the different set and rep data in a function
        itemsIndexed(state.workouts){ index,workout ->
            WorkoutCard(workoutName = workout, numberOfCard = index, state = state)

            //Adds a workout segment to a list of workoutSegments for each workout
            addWorkoutSegment(index, state)
            Spacer(modifier = Modifier.padding(top = 10.dp))

        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .clip(RoundedCornerShape(15.dp))
                    .height(75.dp)
                    .background(lightBlue)
                    .border(width = 2.dp, color = darkBlue, shape = RoundedCornerShape(15.dp))
                    .clickable {
                        GlobalScope.launch(Dispatchers.IO) {
                            addWorkoutToDatabase(state, context)

                        }
                        navController.navigate(Screen.HomeScreen.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Add Workout")
            }
        }
    }

}

//Composable to have each workout on its' own card
//Takes a string in as the workout name, an index of the card to pass to a function,
//and state of course
//Basic UI...Needs work lol, but will work for now
@Composable
fun WorkoutCard(
    workoutName : String,
    numberOfCard : Int,
    state : WorkoutState
){
    //Three variables to hold the state of the editText values
    var sets by remember{ mutableStateOf("")}
    var reps by remember{ mutableStateOf("")}
    var weight by remember{mutableStateOf("")}

    //Box to hold all the contents
    Box(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .clip(RoundedCornerShape(15.dp))
            .border(width = 3.dp, color = darkBlue, shape = RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ){
        //A Column to hold each layer, may need to nest a row to make it look better in the future
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

            //A row to hold the sets and reps "tags"
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

            //A row to hold the edit texts for the respective fields
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                BasicTextField(
                    modifier = Modifier
                        .height(30.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp)),
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                    //set the value equal to state variable sets
                    value = sets,
                    //on value change set value to it and call editWorkoutSegment
                    //The function puts the card into an object and then into an array
                    //with the other cards
                    onValueChange ={
                        sets = it
                        editWorkoutSegment(numberOfCard,"sets", it, state)
                        //Temp "debugger"
                        Log.d("sets", state.workoutSegments.toString())
                    } )

                //Look at comments above, same thing except for reps this time
                BasicTextField(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .border(3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp)),
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                    value = reps,
                    onValueChange = {
                        reps = it
                        editWorkoutSegment(numberOfCard,"reps", it, state)
                        Log.d("sets", state.workoutSegments.toString())
                    })
            }
            
            Spacer(modifier = Modifier.padding(top = 15.dp))

            //Weight "tag"
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Weight"
            )

            //Basic text field for weight, just like sets and reps
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .clip(RoundedCornerShape(5.dp))
                    .border(3.dp, color = darkBlue, shape = RoundedCornerShape(5.dp)),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                value = weight,
                onValueChange = {
                    weight = it
                    editWorkoutSegment(numberOfCard,"weight", it, state)
                    Log.d("sets", state.workoutSegments.toString())
                })

            Spacer(modifier = Modifier.padding(top = 15.dp))
        }
    }
}