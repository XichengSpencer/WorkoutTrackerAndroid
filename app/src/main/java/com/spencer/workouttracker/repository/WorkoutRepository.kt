package com.spencer.workouttracker.repository

import android.annotation.SuppressLint
import android.content.Context
import com.spencer.workouttracker.database.Workout
import com.spencer.workouttracker.database.WorkoutCategory
import com.spencer.workouttracker.database.WorkoutDatabase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val workoutDatabase: WorkoutDatabase
) {
    private val workoutCategoryDao = workoutDatabase.workoutCategoryDao()
    private val workoutDao = workoutDatabase.workoutDao()

    suspend fun getAllWorkoutCategories(): List<WorkoutCategory> {
        return workoutCategoryDao.getAll()
    }

    suspend fun insertWorkoutCategory(category: WorkoutCategory) {
        workoutCategoryDao.insert(category)
    }

    suspend fun updateWorkoutCategory(category: WorkoutCategory) {
        workoutCategoryDao.update(category)
    }

    suspend fun deleteWorkoutCategory(category: WorkoutCategory) {
        workoutCategoryDao.delete(category)
    }

    suspend fun getWorkoutsByCategory(categoryId: Int): List<Workout> {
        return workoutCategoryDao.getWorkoutsForCategory(categoryId)
    }

    suspend fun insertWorkout(workout: Workout) {
        workoutDao.insert(workout)
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutDao.update(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.delete(workout)
    }
}