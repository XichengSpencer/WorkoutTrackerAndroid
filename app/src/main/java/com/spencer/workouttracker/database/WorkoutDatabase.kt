package com.spencer.workouttracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spencer.workouttracker.Workout
import com.spencer.workouttracker.WorkoutCategory

@Database(entities = [WorkoutCategory::class, Workout::class], version = 1)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutCategoryDao(): WorkoutCategoryDao
    abstract fun workoutDao(): WorkoutDao
}



