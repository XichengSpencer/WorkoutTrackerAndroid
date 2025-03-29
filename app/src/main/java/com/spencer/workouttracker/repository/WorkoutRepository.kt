package com.spencer.workouttracker.repository


import com.spencer.workouttracker.database.Workout
import com.spencer.workouttracker.database.WorkoutCategory
import com.spencer.workouttracker.database.WorkoutCategoryDao
import com.spencer.workouttracker.database.WorkoutDao

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val workoutCategoryDao: WorkoutCategoryDao
) {

    //TODO: convert List to Flow when data size grows and update corresponding Dao and view model methods
    suspend fun getAllWorkoutCategories(): List<WorkoutCategory> {
        return workoutCategoryDao.getAll()
    }

    suspend fun insertWorkoutCategory(category: WorkoutCategory) {
        workoutCategoryDao.insert(category)
    }
    suspend fun getWorkoutsByCategory(categoryId: Int): List<Workout> {
        return workoutCategoryDao.getWorkoutsForCategory(categoryId)
    }
    suspend fun updateWorkoutCategory(category: WorkoutCategory) {
        workoutCategoryDao.update(category)
    }
    suspend fun getWeightSum(categoryId: Int): Int {
        return getCategoryById(categoryId)?.let { category ->
           category.weightSum
        } ?: 0
    }
    fun getCategoryById(categoryId: Int): WorkoutCategory? {
        return workoutCategoryDao.getCategoryById(categoryId)
    }
    suspend fun deleteWorkoutCategory(category: WorkoutCategory) {
        workoutCategoryDao.delete(category)
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