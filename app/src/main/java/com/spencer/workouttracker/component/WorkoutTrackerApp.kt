package com.spencer.workouttracker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spencer.workouttracker.R
import com.spencer.workouttracker.database.WorkoutCategory
import com.spencer.workouttracker.viewmodel.WorkoutViewModel

//TODO: Sort out the mvvm architecture with the new room intergration

@Composable
fun WorkoutTrackerApp() {
    //for each body area, list of workout(stored), total weight
    val viewModel: WorkoutViewModel = hiltViewModel()
    val categories by viewModel.workoutCategories.observeAsState(initial = emptyList())
    val selectedCategory by viewModel.selectedCategory.observeAsState(initial = null)
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
                    .fillMaxWidth()
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Black)
            )
            //TODO: center the icon in the box
            {
                var isSetting by rememberSaveable { mutableStateOf(true) } // Kept your original state logic
                val iconRes = if (isSetting) R.drawable.ic_settings_24 else R.drawable.ic_menu_24

                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = if (isSetting) "Settings" else "Menu",
                    modifier = Modifier
                        .clickable { isSetting = !isSetting }
                        .align(Alignment.Center),
                    tint = Color.White // Added tint for visibility


                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
                    .verticalScroll(rememberScrollState())
            )
            {
                categories.let { categories ->
                    categories.forEach { category: WorkoutCategory ->
                        SwipeToDeleteAndToggleStarItem(
                            bodyArea = category,
                            onDelete = { /* Handle delete action */ },
                            selectedCategory
                        ) {
                            viewModel.setSelectedCategory(category)
                            // Navigate to the corresponding Composable when clicked
                        }
                    }
                }
            }
            //TODO: Add a button to add new workout category
            Spacer(modifier = Modifier.height(8.dp)) // Add space above the add section

            // State for toggling the add UI and storing input text
            var isAddingCategory by rememberSaveable { mutableStateOf(false) }
            var newCategoryName by rememberSaveable { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .background(color = Color.White), // Padding for the whole add section
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add New Workout Category",
                    tint = Color.White // Set icon color
                )
            }


        }
        // Workout Fragments
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            selectedCategory?.let {
                WorkoutFragment(workoutCategoryId = it.id, viewModel) // Pass viewModel if needed by WorkoutFragment
            } ?: run {
                // Optional: Show a placeholder when no category is selected
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Select a category", color = Color.White.copy(alpha = 0.7f))
                }
            }
        }
    }
}
