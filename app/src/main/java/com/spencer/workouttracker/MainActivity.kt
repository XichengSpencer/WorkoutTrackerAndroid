package com.spencer.workouttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spencer.workouttracker.component.WorkoutTrackerApp
import com.spencer.workouttracker.ui.theme.WorkoutTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WorkoutTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WorkoutTrackerApp()
                }
            }
        }
    }

}

data class Workout(val name: String, val weight: Int, val repetitions: Int, val sets: Int){
    constructor(name: String):this(
        name = name,
        weight = 1,
        repetitions = 1,
        sets = 1
    )
}

data class WorkoutCategory(
    val name: String,
    val weightSum: Int,
    val workouts: List<Workout>
)
