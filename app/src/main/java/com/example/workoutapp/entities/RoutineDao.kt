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

    @Query("DELETE FROM Routine")
    suspend fun deleteRoutine()

    @Query("DELETE FROM Workout")
    suspend fun deleteWorkout()

    @Query("SELECT COUNT(routineName) FROM Routine")
    suspend fun countRoutine(): Int
}