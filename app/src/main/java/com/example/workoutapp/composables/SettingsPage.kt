package com.example.workoutapp.composables

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.workoutapp.RoutineDatabase
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.ui.theme.offWhite
import com.example.workoutapp.viewModel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//Get navController for navigation
//Get context for database
@Composable
fun SettingsPageScaffold(navController: NavController, context: Context, state: WorkoutState){

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController)},
        content = { Settings(context, state) }
    )
}
//Get context to pass to composables
@Composable
fun Settings(context: Context, state: WorkoutState){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(text = "Settings")
        deleteTable(context = context, state)
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Description()
    }

}

//Get context for dao
@Composable
fun deleteTable(context: Context, state: WorkoutState){

    //Get dao functionality
    val dao = RoutineDatabase.getInstance(context).routineDao

    //This was a temporary button used to test most of the app's features
    //I do not need this in the final code
    //Box(
      //  modifier = Modifier
        //    .fillMaxWidth()
            //GlobalScope is a coroutine, Dispatchers.IO is a thread used for DB
          //  .clickable {
            //    GlobalScope.launch(Dispatchers.IO) {
              //      var countWorkout = dao.countWorkout()
                    //Log.d("Database Size: ", count.toString())
                 //   Log.d("Workout table: ", countWorkout.toString())
                   // getWorkoutInfo(state, context)
                    //Tests to see if a workout has been done today
                    //Test function to see if this works(It does!)
                    //getRoutineFromDayOfWeek(context)
                    //getParsedWorkout(context)
                    //lastWorkoutDay(context)
                //}

            //},
        //contentAlignment = Alignment.Center
    //){
     //   Text(text = "Count Database (Temporary Button)")
    //}

    Spacer(Modifier.padding(top = 20.dp))

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
                    //GlobalScope.launch is a coroutine which is needed
                    //for a suspend function which is in dao
                    GlobalScope.launch(Dispatchers.IO) {
                        dao.deleteRoutine()
                        dao.deleteWorkout()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = offWhite,
                text = "Delete All Records"
            )
        }
    }
}

@Composable
fun Description(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = "This app was made by David Duh for class CSC 487." +
                    " It Demonstrates the use of Kotlin, Jetpack Compose, " +
                    "Room Database, Coroutines/threading, and using current data. " +
                    "While this app does work, it is not intended to be released, " +
                    "just for personal use.                                                  " +
                    "In order to start workouts, create multiple routines and set them " +
                    "to different days of the week. View your progress on the days of the week " +
                    "row at the top of the home page, or the statistics page"
        )
    }
}

