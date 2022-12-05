package com.example.workoutapp.viewModel

import android.content.Context
import android.util.Log
import com.example.workoutapp.RoutineDatabase
import com.example.workoutapp.composables.WorkoutSegment
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.Workout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
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

//Function to add a workout to the table
//Takes in the usual parameters to use dao objects and state
suspend fun addWorkoutToDatabase(
    state : WorkoutState,
    context : Context
){

    //create an instance of the database
    var dao = RoutineDatabase.getInstance(context).routineDao
    var workoutString : String = ""
    //Create instance of a Workout object
    var workout = Workout(workoutID = 0, dayOfWorkout = "", routineName = "", workoutInfo = "")

    //Loop through workout segments to combine them into parsable data
    for(workouts in state.workoutSegments){
        //Current format is "sets, reps, weight|"
        workoutString += workouts.sets + "," + workouts.reps + "," + workouts.weight + "|"
    }

    //Count workout will get the number in the database(Which is 1 higher than the index
    //since indexing starts at 0)
    workout.workoutID = dao.countWorkout()
    //Workout string that was made in the for loop
    workout.workoutInfo = workoutString
    //Call function to get the current date
    workout.dayOfWorkout = currentDate()
    //Get the routine name from the day of the week
    workout.routineName = getRoutineFromDayOfWeek(context).routineName

    //Insert Workout into table
    dao.insertWorkout(workout)

}

//Function to return the date in a string format
fun currentDate() : String{

    var dateFormat = SimpleDateFormat("MM/dd/yyyy")
    return dateFormat.format(Date())
}

//Function to return the last date in the workout table
suspend fun lastWorkoutDay(context : Context) : String{
    var dao = RoutineDatabase.getInstance(context).routineDao
    var lastDate = ""

    try {
        //try getting last day of workout
        lastDate = dao.returnLastWorkout().dayOfWorkout
    }catch (e : Exception ){
        //Goes into another try catch block to try the other dao call
        try{
            //If there is a routine and it is the current day, return null, AKA false
            if(dao.getRoutineFromDayOfWeek(getWeekDay()).dayAssigned == getWeekDay()){
                Log.d("test", "error there")

                return "null"
                //else return "00/00/0000" AKA true
            }else{
                Log.d("test", "Error here")

                return "00/00/0000"
            }
        }catch (e : Exception){
            return "00/00/0000"
        }
    }
    return lastDate
}

//Function to set the T/F state of hasWorkedOut depending on the last date in the database
//and the current date
suspend fun hasWorkedOutToday(
    context : Context,
    state : WorkoutState
){
    var testingDate = lastWorkoutDay(context)

    when (testingDate) {
        //When testingDate == currentDate set state to true
        currentDate() -> {
            state.hasWorkedOut = true
        }
        //When date = 00/00/0000 from a previous fail safe return true
        "00/00/0000" -> {
            state.hasWorkedOut = true
        }
        //If table is empty
        "null" ->{
            state.hasWorkedOut = false
        }
        //Else return false
        else -> {
            state.hasWorkedOut = false
        }
    }
}

