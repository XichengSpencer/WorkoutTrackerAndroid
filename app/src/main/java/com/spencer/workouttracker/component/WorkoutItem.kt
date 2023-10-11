package com.spencer.workouttracker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spencer.workouttracker.R
import com.spencer.workouttracker.Workout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutItem(workout: Workout, onWeightSumChange: (Int) -> Unit)  {
    var workoutWeight by remember { mutableStateOf("${workout.weight}") }
    var workoutRepetitions by remember { mutableStateOf("${workout.repetitions}") }
    var workoutSets by remember { mutableStateOf("${workout.sets}") }
    var globalWeightSum = 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = workout.name,
            style = TextStyle(fontSize = 20.sp)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_add_circle_24),
            contentDescription = "Add",
            modifier = Modifier
                .clickable {
                    // Handle add action here
                    val weight = workoutWeight.toIntOrNull() ?: 0
                    val repetitions = workoutRepetitions.toIntOrNull() ?: 0
                    val sets = workoutSets.toIntOrNull() ?: 0
                    if (weight > 0 && repetitions > 0 && sets > 0) {
                        globalWeightSum += weight * repetitions * sets
                        onWeightSumChange(globalWeightSum)
                    }
                }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = workoutWeight,
            onValueChange = { workoutWeight = it },
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            isError = workoutWeight.toIntOrNull() == null,
        )

        OutlinedTextField(
            value = workoutRepetitions,
            onValueChange = { workoutRepetitions = it },
            label = { Text("Rep") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            isError = workoutRepetitions.toIntOrNull() == null,
        )

        OutlinedTextField(
            value = workoutSets,
            onValueChange = { workoutSets = it },
            label = { Text("Sets") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            isError = workoutSets.toIntOrNull() == null,
        )
    }
}
