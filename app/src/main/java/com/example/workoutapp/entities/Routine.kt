package com.example.workoutapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Name of table is the name of the class
@Entity
data class Routine(

    //Primary key is routine name. Check if one already exists with same name when
    //doing business logic
    @PrimaryKey(autoGenerate = false)
    val routineName : String,
    //For dayOfCreation most likely change to Date type
    val dayOfCreation : String,
    //String for each day of the week
    val dayAssigned: String
)
