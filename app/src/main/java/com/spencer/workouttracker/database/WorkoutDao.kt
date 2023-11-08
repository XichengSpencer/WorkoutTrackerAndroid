package com.spencer.workouttracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.spencer.workouttracker.Workout

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout")
    fun getAllByCategory(categoryId: Int): List<Workout>

    @Insert
    fun insertAll(vararg workouts: Workout)

    @Delete
    fun delete(workout: Workout)
}