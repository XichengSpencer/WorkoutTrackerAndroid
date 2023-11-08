package com.spencer.workouttracker.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val weight: Int = 1,
    val repetitions: Int = 1,
    val sets: Int = 1,
    val categoryId: Int
)

@Entity
data class WorkoutCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val weightSum: Int = 0,
    //TODO: Make use of workout class to create a seperate table
    var workouts: List<Workout>
)
data class WorkoutCategoryWithWorkouts(
    @Embedded val workoutCategory: WorkoutCategory, // Includes the WorkoutCategory object in the WorkoutCategoryWithWorkouts object
    @Relation(
        parentColumn = "id", // The primary key of the WorkoutCategory entity
        entityColumn = "categoryId" // The foreign key in the Workout entity that references the WorkoutCategory entity
    )
    val workouts: List<Workout> // Includes a list of Workout objects in the WorkoutCategoryWithWorkouts object
)