package com.spencer.workouttracker.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [WorkoutCategory::class, Workout::class], version = 1)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutCategoryDao(): WorkoutCategoryDao
    abstract fun workoutDao(): WorkoutDao

    companion object {
        const val DATABASE_NAME = "workout_database"
    }
}

