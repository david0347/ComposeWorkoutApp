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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.workoutapp.RoutineDatabase
import com.example.workoutapp.ui.theme.darkBlue
import com.example.workoutapp.ui.theme.lightBlue
import com.example.workoutapp.viewModel.getParsedWorkout
import com.example.workoutapp.viewModel.getRoutineFromDayOfWeek
import com.example.workoutapp.viewModel.getWeekDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//Get navController for navigation
//Get context for database
@Composable
fun SettingsPageScaffold(navController: NavController, context: Context){

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController)},
        content = { Settings(context) }
    )
}
//Get context to pass to composables
@Composable
fun Settings(context: Context){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(text = "Settings")
        deleteTable(context = context)
    }

}

//Get context for dao
@Composable
fun deleteTable(context: Context){

    //Get dao functionality
    val dao = RoutineDatabase.getInstance(context).routineDao
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //GlobalScope is a coroutine, Dispatchers.IO is a thread used for DB
            .clickable {
                GlobalScope.launch(Dispatchers.IO) {
                    var count = dao.countRoutine()
                    var countWorkout = dao.countWorkout()
                    Log.d("Database Size: ", count.toString())
                    Log.d("Workout table: ", countWorkout.toString())
                    //Test function to see if this works(It does!)
                    //getRoutineFromDayOfWeek(context)
                    //getParsedWorkout(context)
                }

            },
        contentAlignment = Alignment.Center
    ){
        Text(text = "Count Database (Temporary Button)")
    }

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
            Text("Delete All Records")
        }
    }
}

