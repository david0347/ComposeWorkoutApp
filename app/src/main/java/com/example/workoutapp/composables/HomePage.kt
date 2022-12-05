package com.example.workoutapp.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workoutapp.routes.Screen
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.ui.theme.mediumBlue
import com.example.workoutapp.ui.theme.offWhite
import com.example.workoutapp.viewModel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
**** Home Page ****
Big lesson I learned in this is to wrap almost everything in a box if you want
the content to be centered
 */
//String to put in dayOfWeekLazyColumn
val daysOfWeekString = listOf("M", "T", "W", "TH", "F", "SA", "SU")

//Holds all the composables that make up homeScreen without scaffolding
@Composable
fun HomePage(navController: NavController, state : WorkoutState, context: Context){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(offWhite)
    ) {

        Header(text = "Welcome Back!")
        //This message will change based on day of the week
        DayOfWeekMessage(messageOfDay = "Today is " + dayOfWeekText(state).lowercase())
        DaysOfWeekRow(navController, state, context = context)
        Spacer(Modifier.padding(top = 20.dp))
        ButtonBox(navController, context, state)
    }
}

//Holds homePage and utilizes scaffolding
@Composable
fun HomePageScaffold(navController: NavController, state : WorkoutState, context : Context){
    Scaffold(
        bottomBar = {BottomNavBar(navController)},
        content = {HomePage(navController, state, context)}
    )
}

//Composable for header, says Welcome back, I utilize this on every screen
//If making it a header like bottom nav bar is more efficient I will do that
@Composable
fun Header(
    text: String,
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.1f)
            .background(darkBlue),
        contentAlignment = Alignment.Center
    ){
        Text(
            color = offWhite,
            fontSize = 32.sp,
            fontWeight = Bold,
            text = text,
        )
    }
}

//Composable to create a lazy column to have a button for each day of the week
@Composable
fun DaysOfWeekRow(navController: NavController, state : WorkoutState, context : Context){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.14f)
            .background(offWhite),
        contentAlignment = Alignment.Center
    ){
        LazyRow(
            modifier = Modifier.
                    padding(start = 4.dp)

        ){
            //How to add a lazy column for items in a list
            items(daysOfWeekString){ day ->
                DaysOfWeekButton(dayOfWeek = day, navController, state, context = context)
                Spacer(modifier = Modifier.padding(2.dp))
            }
        }
    }
}

//Composable to create the button called in the lazy column
//I call the getWorkoutInfo function to successfully get the data from the database
//It was not working inside the DaysOfWeekPage because the coroutine was running along side
//Everything else and it was being inconsistent giving me a one off error
//This fixed the problem and I should use something like this with the statistics page
@Composable
fun DaysOfWeekButton(
    dayOfWeek: String,
    navController: NavController,
    state : WorkoutState,
    context : Context
){
    Box (modifier = Modifier
        .size(70.dp)
        .clip(CircleShape)
        .background(lightBlue)
        .border(width = 2.dp, color = darkBlue, shape = CircleShape)
        .clickable {
            navController.navigate(Screen.DayOfWeekScreen.route)
            state.dayOfWeekSelected = dayOfWeek
            GlobalScope.launch(Dispatchers.IO) {
                getWorkoutInfo(state, context)

            }
        },
        contentAlignment = Alignment.Center
    ){
        Text(
            fontSize = 40.sp,
            fontWeight = Bold,
            color = offWhite,
            text = dayOfWeek
        )
    }
}

//Composable for a message bar below the header
@Composable
fun DayOfWeekMessage(
    messageOfDay: String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.05f)
            .background(mediumBlue),
        contentAlignment = Alignment.Center
    ){
        Text(
            fontSize = 15.sp,
            color = offWhite,
            text = messageOfDay
        )
    }
}

//Composable for the main 4 buttons on the screen
//Takes in a string for button name, navController for navigation, and a pageRoute which is a string
@Composable
fun MainButtons(
    buttonText: String,
    navController: NavController,
    pageRoute: String
){
    Box(
        modifier = Modifier
            .fillMaxWidth(.7f)
            .clip(RoundedCornerShape(15.dp))
            .height(75.dp)
            .background(lightBlue)
            .border(width = 2.dp, color = darkBlue, shape = RoundedCornerShape(15.dp))
            .clickable { navController.navigate(pageRoute) },
        contentAlignment = Alignment.Center
    ){
        Text(
            color = offWhite,
            fontSize = 20.sp,
            fontWeight = Bold,
            text = buttonText
        )
    }
}

//Composable to hold and call all the buttons
@Composable
fun ButtonBox(
    navController: NavController,
    context : Context,
    state : WorkoutState
){
    Box(
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight(.8f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //Commented out MainButtons call is for buttons that needed a coroutine call when
            //clicked on, before page load
            //MainButtons("Start Workout", navController, Screen.StartWorkoutScreen.route)
            StartWorkoutButton(buttonText = "Start Workout", navController =navController , context = context, state = state)
            MainButtons("Create New Routine", navController, Screen.CreateRouteScreen.route)
            //MainButtons("Edit Routine", navController, Screen.EditRoutineScreen.route)
            EditRoutineButton(buttonText = "Edit Routine", navController = navController, context = context, state = state)
            //MainButtons("Statistics", navController, Screen.StatsScreen.route)
            StatisticButton(buttonText = "Statistics", navController = navController, context = context, state = state)
        }
    }
}

//Needed to move the hasWorkedOutToday call inside of the onclick function because this was
//Also causing an error when it was on page load
@Composable
fun StartWorkoutButton(
    buttonText: String,
    navController: NavController,
    context : Context,
    state : WorkoutState
){
    Box(
        modifier = Modifier
            .fillMaxWidth(.7f)
            .clip(RoundedCornerShape(15.dp))
            .height(75.dp)
            .background(lightBlue)
            .border(width = 2.dp, color = darkBlue, shape = RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate(Screen.StartWorkoutScreen.route)
                GlobalScope.launch(Dispatchers.IO) {
                    hasWorkedOutToday(context, state)
                    getRoutinesWithWorkouts(context, state)
                }
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            color = offWhite,
            fontSize = 20.sp,
            fontWeight = Bold,
            text = buttonText
        )
    }
}

//Composable for the main 4 buttons on the screen
//Takes in a string for button name, navController for navigation, and a pageRoute which is a string
//Did not totally fix problem, but I think this is the right idea
@Composable
fun StatisticButton(
    buttonText: String,
    navController: NavController,
    context : Context,
    state : WorkoutState
){
    Box(
        modifier = Modifier
            .fillMaxWidth(.7f)
            .clip(RoundedCornerShape(15.dp))
            .height(75.dp)
            .background(lightBlue)
            .border(width = 2.dp, color = darkBlue, shape = RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate(Screen.StatsScreen.route)
                GlobalScope.launch(Dispatchers.IO) {
                    getRoutinesWithWorkouts(context, state)
                }
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            color = offWhite,
            fontSize = 20.sp,
            fontWeight = Bold,
            text = buttonText
        )
    }
}

@Composable
fun EditRoutineButton(
    buttonText: String,
    navController: NavController,
    context : Context,
    state : WorkoutState
){
    Box(
        modifier = Modifier
            .fillMaxWidth(.7f)
            .clip(RoundedCornerShape(15.dp))
            .height(75.dp)
            .background(lightBlue)
            .border(width = 2.dp, color = darkBlue, shape = RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate(Screen.EditRoutineScreen.route)
                GlobalScope.launch(Dispatchers.IO) {
                    getRoutines(context, state)
                }
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            color = offWhite,
            fontSize = 20.sp,
            fontWeight = Bold,
            text = buttonText
        )
    }
}