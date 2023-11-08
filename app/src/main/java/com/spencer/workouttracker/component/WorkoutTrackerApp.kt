package com.spencer.workouttracker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.spencer.workouttracker.R
import com.spencer.workouttracker.Workout
import com.spencer.workouttracker.WorkoutCategory


@Preview
@Composable
fun WorkoutTrackerApp() {
    //for each body area, list of workout(stored), total weight
    val workoutCategories = defaultWorkoutCategoryGenerator()

    var selectedCategory by remember { mutableStateOf(workoutCategories.first()) }
    Row (
        modifier = Modifier.background(
            color = Color(0xFF313866)
        )
    ){
        // Vertical menu
        Column(
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .background(
                    color = Color(0xFF504099)
                )
        ) {
            Box (
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally)
            )
            {
                var isSetting by remember { mutableStateOf(true) }
                val starIcon =
                    if (isSetting) R.drawable.ic_settings_24 else R.drawable.ic_menu_24

                Icon(
                    painter = painterResource(id = starIcon),
                    contentDescription = "Star",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { isSetting = !isSetting }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            )
            {
                workoutCategories.forEach { category ->
                    SwipeToDeleteAndToggleStarItem(
                        bodyArea = category,
                        onDelete = { /* Handle delete action */ },
                        selectedCategory
                    ) {
                        selectedCategory = category
                        // Navigate to the corresponding Composable when clicked

                    }
                }
            }

        }
        // Workout Fragments
        WorkoutFragment(workoutCategory = selectedCategory)

    }
}
fun defaultWorkoutCategoryGenerator(): MutableList<WorkoutCategory> {
    val bodyAreas = listOf("Arms", "Legs", "Chest", "Back")
    val workoutCategories = mutableListOf<WorkoutCategory>()

    bodyAreas.forEach { bodyArea ->
        val workouts = when (bodyArea) {
            "Arms" -> listOf(
                Workout(name = "Bicep Curls", weight = 20, sets = 10, repetitions = 2),
                Workout(name = "Hammer Curls", weight = 25, sets = 8, repetitions = 3)
            )
            "Legs" -> listOf(
                Workout(name = "Squats", weight = 30, sets = 10, repetitions = 2),
                Workout(name = "Lunges", weight = 35, sets = 8, repetitions = 3)
            )
            "Chest" -> listOf(
                Workout(name = "Bench Press", weight = 40, sets = 10, repetitions = 2),
                Workout(name = "Incline Dumbbell Press", weight = 45, sets = 8, repetitions = 3)
            )
            "Back" -> listOf(
                Workout(name = "Pull-ups", weight = 50, sets = 10, repetitions = 2),
                Workout(name = "Lat Pulldowns", weight = 55, sets = 8, repetitions = 3)
            )
            else -> emptyList()
        }
        workoutCategories.add(WorkoutCategory(name = bodyArea, weightSum = 0, workouts = workouts))
    }
    return workoutCategories
}