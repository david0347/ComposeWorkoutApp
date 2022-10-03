package com.example.workoutapp.composables

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

/*
**** Home Page ****
Big lesson I learned in this is to wrap almost everything in a box if you want
the content to be centered
 */
//String to put in dayOfWeekLazyColumn
val daysOfWeekString = listOf("M", "T", "W", "TH", "F", "SA", "SU")

//Holds all the composables that make up homeScreen without scaffolding
@Composable
fun HomePage(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(offWhite)
    ) {

        Header(text = "Welcome Back!")
        //This message will change based on day of the week
        DayOfWeekMessage(messageOfDay = "Today is rest day")
        DaysOfWeekRow(navController)
        Spacer(Modifier.padding(top = 20.dp))
        ButtonBox(navController)
    }
}

//Holds homePage and utilizes scaffolding
@Composable
fun HomePageScaffold(navController: NavController){
    Scaffold(
        bottomBar = {BottomNavBar(navController)},
        content = {HomePage(navController)}
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
fun DaysOfWeekRow(navController: NavController){
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
                DaysOfWeekButton(dayOfWeek = day, navController)
                Spacer(modifier = Modifier.padding(2.dp))
            }
        }
    }
}

//Composable to create the button called in the lazy column
@Composable
fun DaysOfWeekButton(
    dayOfWeek: String,
    navController: NavController
){
    Box (modifier = Modifier
        .size(70.dp)
        .clip(CircleShape)
        .background(lightBlue)
        .border(width = 2.dp, color = darkBlue, shape = CircleShape)
        .clickable { navController.navigate(Screen.DayOfWeekScreen.route) },
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
fun ButtonBox(navController: NavController){
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
            MainButtons("Start Workout", navController, Screen.StartWorkoutScreen.route)
            MainButtons("Create New Routine", navController, Screen.CreateRouteScreen.route)
            MainButtons("Set Days", navController, Screen.SetDaysScreen.route)
            MainButtons("Statistics", navController, Screen.StatsScreen.route)
        }
    }
}