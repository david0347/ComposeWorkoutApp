package com.example.workoutapp.viewModel

import android.content.Context
import com.example.workoutapp.RoutineDatabase

//This function will add a Routine to the database if the information on
//Create Routine Page is correct
fun addToDatabase(context : Context){

    //Get dao functionality
    val dao = RoutineDatabase.getInstance(context).routineDao

}