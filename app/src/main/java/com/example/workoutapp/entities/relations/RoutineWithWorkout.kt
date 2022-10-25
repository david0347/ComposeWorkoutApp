package com.example.workoutapp.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.Workout

data class RoutineWithWorkout(
    @Embedded val routine : Routine,
    @Relation(
        parentColumn = "routineName",
        entityColumn = "routineName"
    )
    val workouts : List<Workout>
)
