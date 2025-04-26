package com.spencer.workouttracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface WorkoutCategoryDao {
    @Query("SELECT * FROM workoutcategory")
    suspend fun getAll(): List<WorkoutCategory>

    @Transaction
    @Query("SELECT * FROM Workout WHERE categoryId = :categoryId")
    suspend fun getWorkoutsForCategory(categoryId: Int): List<Workout>

    @Query("SELECT * FROM workoutcategory WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): WorkoutCategory?

    @Insert
    suspend fun insertAll(vararg workoutCategories: WorkoutCategory)
    @Insert
    fun insert(workoutCategory: WorkoutCategory)
    @Update
    fun update(workoutCategory: WorkoutCategory)
    @Delete
    fun delete(workoutCategory: WorkoutCategory)
}