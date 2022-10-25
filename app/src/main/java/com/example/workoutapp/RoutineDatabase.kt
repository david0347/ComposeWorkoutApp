package com.example.workoutapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutapp.entities.Routine
import com.example.workoutapp.entities.RoutineDao
import com.example.workoutapp.entities.Workout

//Increase version if I alter the database
@Database(entities = [Routine::class, Workout::class], version = 1)
abstract class RoutineDatabase : RoomDatabase() {

    abstract val routineDao : RoutineDao

    companion object {
        @Volatile
        private var INSTANCE: RoutineDatabase? = null

        fun getInstance(context: Context) : RoutineDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RoutineDatabase::class.java,
                    "routine_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}