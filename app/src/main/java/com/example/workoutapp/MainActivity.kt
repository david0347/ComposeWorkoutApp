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
            Routine("Chest Day", "1/1/1", "MONDAY", "bench,Machine fly,dumbbell fly,push ups, dips, dumbbell bench"),
            Routine("Leg Day", "1/2/1", "TUESDAY", "squat,leg press,step ups"),
            Routine("Arm Day", "1/3/1", "WEDNESDAY", "bench,curl,preacher "),
            Routine("Back Day", "1/4/1", "THURSDAY", "deadlift,rows,pull ups"),
            Routine("Shoulder Day", "1/5/1", "FRIDAY", "military press,land mines,shoulder raise"),
            Routine("Rest Day 1", "1/5/1", "SATURDAY", "nothing,sleep in,eat breakfast"),
            Routine("Rest Day 2", "1/5/1", "SUNDAY", "video games,sleep in,eat breakfast")
        )

        val workouts = listOf(
            Workout(0, "01/08/2022", "Chest Day","1,2,3|1,2,3|1,2,3|1,2,3|1,2,3|,1,2,3|"),
            Workout(1, "01/12/2022", "Chest Day","1,2,3|1,2,3|1,2,3|1,2,3|1,2,3|,1,2,3|"),
            Workout(2, "01/13/2022", "Leg Day","1,2,3|1,2,3|1,2,3|"),
            Workout(3, "01/13/2022", "Arm Day","1,2,3|1,2,3|1,2,3|")
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

