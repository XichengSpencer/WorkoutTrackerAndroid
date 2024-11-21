package com.spencer.workouttracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WorkoutDao {
    @Insert
    fun insertAll(vararg workouts: Workout)

    @Delete
    fun delete(workout: Workout)
    @Update
    fun update(workout: Workout)
    @Insert
    fun insert(workout: Workout)
}