package com.spencer.workouttracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.spencer.workouttracker.Workout
import com.spencer.workouttracker.WorkoutCategory

@Dao
interface WorkoutCategoryDao {
    @Query("SELECT * FROM workoutcategory")
    fun getAll(): List<WorkoutCategory>

    @Transaction
    @Query("SELECT * FROM Workout WHERE categoryId = :categoryId")
    fun getWorkoutsForCategory(categoryId: Int): List<Workout>
    @Insert
    fun insertAll(vararg workoutCategories: WorkoutCategory)

    @Delete
    fun delete(workoutCategory: WorkoutCategory)
}