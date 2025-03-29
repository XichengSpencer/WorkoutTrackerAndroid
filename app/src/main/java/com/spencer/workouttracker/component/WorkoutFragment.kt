package com.spencer.workouttracker.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spencer.workouttracker.database.Workout
import com.spencer.workouttracker.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WorkoutFragment(workoutCategoryId: Int, workoutViewModel: WorkoutViewModel) {

    //preload the workouts for the selected category into the view model using id
    LaunchedEffect(workoutCategoryId) {
        workoutViewModel.loadWorkoutsForCategory(workoutCategoryId)
    }
    //scope the coroutine with the fragment
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    //track the selected workouts and set default value to empty list
    val workoutsForSelectedCategory by workoutViewModel.workoutsForSelectedCategory.observeAsState(emptyList())

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column (
                modifier = Modifier.padding(16.dp)
            ){
                var exerciseName by remember { mutableStateOf("") }
                TextField(
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    label = { Text("Workout Name") },
                    trailingIcon = {
                        IconButton(onClick = {
                            // Add to list
                            if (exerciseName.isNotBlank()) {
                                // Create new Workout and add to database
                                val newWorkout = Workout(
                                    name = exerciseName,
                                    categoryId = workoutCategoryId // Link to the selected category
                                )
                                workoutViewModel.insertWorkout(newWorkout)
                            }
                            // Clear text
                            exerciseName = ""
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Icon", tint = Color.Black)
                        }
                    },
                    visualTransformation = VisualTransformation.None,
                    textStyle = TextStyle(color = Color(0xFF504099)),

                    )
            }
        }
    ){




        Column {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                //TODO: fix the weight sum part
                items(workoutsForSelectedCategory) { workout ->
                    WorkoutItem(
                        workout = workout,
                        onWeightSumChange = { updatedWorkout ->
                            workoutViewModel.updateWorkout(updatedWorkout)
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var showDialog by remember { mutableStateOf(false) }
                val weightSum by workoutViewModel.weightSum.observeAsState(0)

                Text(
                    modifier = Modifier.weight(4f),
                    text = "Total: $weightSum",
                    fontSize = 20.sp
                )

                FloatingActionButton(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f), // Set the aspect ratio to 1:1
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show() // Show the bottom sheet when clicked
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }

                Spacer(modifier = Modifier.width(16.dp)) // Add a spacer for 16.dp gap

                FloatingActionButton(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f), // Set the aspect ratio to 1:1
                    onClick = { showDialog = true }
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Clear")
                }
                if (showDialog) {
                    val textColor = Color(0xFF6528F7)
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Confirmation", color = textColor) },
                        text = { Text("Would you want to clear the total weight?", color = textColor) },
                        confirmButton = {
                            TextButton(onClick = {
                                /* Handle clear action here */
                                //TODO: Store the result local
                                //TODO: Store the result online
                                workoutViewModel.updateWeightSum(0)
                                showDialog = false
                            }) {
                                Text("Yes", color = textColor)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                            }
                            ) {
                                Text("No", color = textColor)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.width(16.dp)) // Add a spacer for 16.dp gap
                //submit button
//                                FloatingActionButton(
//                                    modifier = Modifier
//                                        .weight(1f)
//                                        .aspectRatio(1f), // Set the aspect ratio to 1:1
//                                    onClick = { /* Handle add action here */ }
//                                ) {
//                                    Icon(Icons.Filled.Check, contentDescription = "Submit")
//                                }
            }
        }



    }
}
