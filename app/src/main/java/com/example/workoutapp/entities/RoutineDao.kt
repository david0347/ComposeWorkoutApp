package com.example.workoutapp.entities

import androidx.room.*
import com.example.workoutapp.entities.relations.RoutineWithWorkout

@Dao
interface RoutineDao {

    //Insert routine into table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine)

    //Insert workout into table
    //Not sure if I should use the onConflict or do it in business logic
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    //Use transaction if pulling data from multiple tables
    //Query to get the routine from using routineName
    @Transaction
    @Query("SELECT * FROM routine WHERE routineName = :routineName")
    suspend fun getRoutineWithWorkouts(routineName : String) : List<RoutineWithWorkout>

    //Query to get all routines and return a list of them
    @Query("SELECT * FROM routine")
    suspend fun getAllRoutines() : MutableList<Routine>

    //Query to get all workouts based on the routine name, returns list of Workout
    @Query("SELECT * FROM workout WHERE routineName = :routineName")
    suspend fun getAllWorkoutsForRoutine(routineName : String) : MutableList<Workout>

    //Delete routine table
    @Query("DELETE FROM Routine")
    suspend fun deleteRoutine()

    //Delete workout table
    @Query("DELETE FROM Workout")
    suspend fun deleteWorkout()

    //Count items in routine table
    @Query("SELECT COUNT(routineName) FROM Routine")
    suspend fun countRoutine(): Int

    //Count items in workout table
    @Query("SELECT COUNT(workoutID) FROM Workout")
    suspend fun countWorkout(): Int

    //Return last element in workout table
    @Query("SELECT * FROM workout ORDER BY workoutID DESC LIMIT 1")
    suspend fun returnLastWorkout() : Workout

    //Query to get a routine from the day of the week
    @Query("SELECT * FROM Routine WHERE dayAssigned = :dayOfWeek")
    suspend fun getWorkoutFromDate(dayOfWeek : String) : Routine

    //Query to delete a routine based on routine name
    @Query("DELETE FROM Routine WHERE routineName = :routine")
    suspend fun deleteRoutineFromRoutineTable(routine : String)

    @Query("DELETE FROM Workout WHERE routineName = :routine")
    suspend fun deleteRoutineFromWorkoutTable(routine : String)
}