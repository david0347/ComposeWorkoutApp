package com.example.workoutapp.viewModel

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toUpperCase
import com.example.workoutapp.RoutineDatabase
import com.example.workoutapp.composables.WorkoutSegment
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.Workout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Thread.State
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

//This function will add a Routine to the database if the information on
//Create Routine Page is correct
suspend fun addToDatabase(context : Context, state: WorkoutState){

    //Create routine
    //currentDate is a function I created to return the date
    val routine : Routine = Routine(
        routineName = state.routineNameTextField,
        dayOfCreation = currentDate(),
        dayAssigned = state.dayOfWeekTextField.uppercase(),
        workout = state.workoutNamesTextField)
    //Get dao functionality
    val dao = RoutineDatabase.getInstance(context).routineDao

    //Call insertRoutine to the routine object and add to database
    dao.insertRoutine(routine)

    //Reset the text fields
    state.routineNameTextField = ""
    state.dayOfWeekTextField = ""
    state.workoutNamesTextField = ""
}

//Function to return weekday string, thanks YouTube
fun getWeekDay() : String{
    val calendar = Calendar.getInstance()
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).uppercase()
}

//Function to get the string of workouts from the routine table based on the day of the week
//Returns a routine for the day of week
suspend fun getRoutineFromDayOfWeek(context: Context) : Routine{
    val dao = RoutineDatabase.getInstance(context).routineDao

    return dao.getWorkoutFromDate(getWeekDay())
}

//Function to parse the string from getRoutineFromDayOfWeek function
// into a string array
//Returns a string array
fun getParsedWorkout(context : Context, state : WorkoutState){

    var parsedWorkout: List<String>
    GlobalScope.launch {
        //Call Function to get the routine based on the day of the week
        var routine = getRoutineFromDayOfWeek(context)

        //Parse the workout string into an array
        parsedWorkout = routine.workout.split(",")
        //Save the array to state
        state.workouts = parsedWorkout
    }

}

//Function is triggered when the startWorkoutPage is loaded
//It creates a workoutSegment object to temporarily hold the sets, reps, and weight
//for each card and then puts it into an array in state.
fun addWorkoutSegment(workoutCardOrder : Int, state: WorkoutState){
    var workoutSegment : WorkoutSegment = WorkoutSegment(workoutCardOrder)
    state.workoutSegments += workoutSegment
}

//Edits the workout segment created by addWorkoutSegment whenever the text in any of the
//text fields are updated
//Takes in workoutCardNumber to match with index of the state variable,
//the editedTextBox name such as "sets" or "reps",
//editedText of the actual changed value,
//and you can not forget about state
fun editWorkoutSegment(
    workoutCardNumber : Int,
    editedTextBox : String,
    editedText : String,
    state : WorkoutState
){
    //Loop through the workouts and if the workout matches ID,
    //then update that ID
    for(workouts in state.workoutSegments){
        if(workouts.order == workoutCardNumber){
            //If the edit text is for sets edit the sets attribute of the object, and so on
            //Change to a when statement if I feel comfortable doing so
            if(editedTextBox == "sets"){
                workouts.sets = editedText
            }else if(editedTextBox == "reps"){
                workouts.reps = editedText
            }else{
                workouts.weight = editedText
            }
        }
    }
}

suspend fun addWorkoutToDatabase(
    state : WorkoutState,
    context : Context
){

    var dao = RoutineDatabase.getInstance(context).routineDao
    var workoutString : String = ""
    var workout = Workout(workoutID = 0, dayOfWorkout = "", routineName = "", workoutInfo = "")

    for(workouts in state.workoutSegments){
        workoutString += workouts.sets + "," + workouts.reps + "," + workouts.weight + "|"
    }

    workout.workoutID = dao.countWorkout()
    workout.workoutInfo = workoutString
    workout.dayOfWorkout = currentDate()
    workout.routineName = getRoutineFromDayOfWeek(context).routineName

    dao.insertWorkout(workout)

    state.workoutSegments = mutableListOf()
}

//Function to return the date in a string format
fun currentDate() : String{

    var dateFormat = SimpleDateFormat("MM/dd/yyyy")
    return dateFormat.format(Date())
}