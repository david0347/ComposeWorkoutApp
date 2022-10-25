package com.example.workoutapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.composables.Navigation
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.Workout
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = RoutineDatabase.getInstance(this).routineDao

        val routines = listOf(
            Routine("Chest Day", "1/1/1", "Monday"),
            Routine("Leg Day", "1/2/1", "Tuesday"),
            Routine("Arm Day", "1/3/1", "Wednesday"),
            Routine("Back Day", "1/4/1", "Thursday"),
            Routine("Shoulder Day", "1/5/1", "Friday")
        )

        val workouts = listOf(
            Workout(0, "1/8/1", "Chest Day","Bench 3x5x135"),
            Workout(1, "1/12/1", "Chest Day","Bench 3x5x145"),
            Workout(2, "1/13/1", "Leg Day","Squats 3x10x145")
        )

        lifecycleScope.launch{
            routines.forEach{dao.insertRoutine(it)}
            workouts.forEach{dao.insertWorkout(it)}
        }

        setContent {
            //Call navigation to start home page
            Navigation()
        }
    }
}

