package com.spencer.workouttracker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spencer.workouttracker.R
import com.spencer.workouttracker.Workout
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.IconButton
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spencer.workouttracker.repository.WorkoutRepository
import com.spencer.workouttracker.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun WorkoutTrackerApp() {
    val context = LocalContext.current
    val workoutViewModel = WorkoutViewModel(WorkoutRepository.getInstance(context))
    val bodyAreas = listOf("Arms", "Legs", "Chest", "Back")
    var selectedBodyArea by remember { mutableStateOf(bodyAreas[0]) }
    var workouts by remember { mutableStateOf(listOf<Workout>(
        Workout("Bicep Curls", 20, 10,2),
        Workout("Tricep Extensions", 15, 12,2),
        Workout("Hammer Curls", 25, 8,3)
    )) }
    // Create a NavController
    val navController = rememberNavController()
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
                bodyAreas.forEach { bodyArea ->
                    SwipeToDeleteAndToggleStarItem(
                        bodyArea = bodyArea,
                        onDelete = { /* Handle delete action */ }
                    ) {
                        // Navigate to the corresponding Composable when clicked
                        navController.navigate(bodyArea)
                    }
                }
            }

        }
        // Workout Fragments
        val coroutineScope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

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
                                workouts += Workout(exerciseName)
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
            NavHost(navController, startDestination = bodyAreas[0]) {
                bodyAreas.forEach { bodyArea ->
                    composable(bodyArea) {


                        Column {
                            LazyColumn(
                                modifier = Modifier.weight(1f)
                            ) {
                                items(workouts) { workout ->
                                    WorkoutItem(workout) { newWeightSum ->
                                        workoutViewModel.weightSum.value?.let { it1 ->
                                            workoutViewModel.updateWeightSum(it1+newWeightSum)

                                        }
                                    }
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
                                                //TODO: Store the result to DataStore
                                                workoutViewModel.updateWeightSum(0)
                                                showDialog = false
                                            }) {
                                                Text("Yes", color = textColor)
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(onClick = {
                                                workoutViewModel.updateWeightSum(0)
                                                showDialog = false
                                            }
                                            ) {
                                                Text("No", color = textColor)
                                            }
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp)) // Add a spacer for 16.dp gap

                                FloatingActionButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f), // Set the aspect ratio to 1:1
                                    onClick = { /* Handle add action here */ }
                                ) {
                                    Icon(Icons.Filled.Check, contentDescription = "Submit")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}