package com.example.workoutapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(

    //Every new workout will have a new unique auto generated workout ID
    @PrimaryKey
    var workoutID : Int,
    //The day the workout was logged, possibly change to Date type
    var dayOfWorkout : String,
    //routine name to link
    var routineName : String,
    //Since the columns can not be dynamic I will put all workout data into a string and parse later
    var workoutInfo : String


)