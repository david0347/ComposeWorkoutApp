package com.example.workoutapp.composables

//Data class to add the workout segments from the start workout screen
//into an editable place holder so the user can edit reps and sets in
//different orders
data class WorkoutSegment(
    var order : Int,
    var sets : String = "",
    var reps : String = "",
    var weight :  String = ""
)
