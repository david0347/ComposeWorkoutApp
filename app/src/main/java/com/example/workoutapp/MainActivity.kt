package com.example.workoutapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutapp.composables.Navigation
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.Workout
import com.example.workoutapp.viewModel.WorkoutViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = RoutineDatabase.getInstance(this).routineDao

        val routines = listOf(
            Routine("Chest Day", "1/1/1", "Monday", "bench,Machine fly,dumbbell fly"),
            Routine("Leg Day", "1/2/1", "Tuesday", "squat,leg press,step ups"),
            Routine("Arm Day", "1/3/1", "Wednesday", "bench,curl,preacher "),
            Routine("Back Day", "1/4/1", "Thursday", "deadlift,rows,pull ups"),
            Routine("Shoulder Day", "1/5/1", "Friday", "military press,land mines,shoulder raise"),
            Routine("Rest Day 1", "1/5/1", "Saturday", "nothing,sleep in,eat breakfast"),
            Routine("Rest Day 2", "1/5/1", "Saturday", "video games,sleep in,eat breakfast")
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

        //Setting a single view model and state to be passed through
        setContent {
            //View Model to pass through navigation and the rest of the composables
            val viewModel = viewModel<WorkoutViewModel>()
            //state to pass through navigation and the rest of the composables
            val state = viewModel.state
            //Call navigation to start home page
            Navigation(state, viewModel)
        }
    }
}

