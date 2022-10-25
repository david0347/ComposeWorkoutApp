package com.example.workoutapp.composables

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.workoutapp.RoutineDatabase
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
            .clickable {
                GlobalScope.launch { var count = dao.countRoutine()
                    Log.d("Database Size: ", count.toString())}

            }
    ){
        Text(text = "Count Database")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                GlobalScope.launch {
                    dao.deleteRoutine()
                    dao.deleteWorkout()
                }
            }
    ){
        Text("Testing deletion")
    }
}

