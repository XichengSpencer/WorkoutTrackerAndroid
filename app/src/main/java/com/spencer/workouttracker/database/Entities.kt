package com.spencer.workouttracker.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
// TODO: keep the Room for local storage, if possible add remote database for online storage
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
)