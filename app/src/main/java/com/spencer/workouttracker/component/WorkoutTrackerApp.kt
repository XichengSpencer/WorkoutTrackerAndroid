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


@Preview
@Composable
fun WorkoutTrackerApp() {
    //for each body area, list of workout(stored), total weight
    val bodyAreas = listOf("Arms", "Legs", "Chest", "Back")
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
                val selectedItem = remember { mutableStateOf<String?>(null) }
                bodyAreas.forEach { bodyArea ->
                    SwipeToDeleteAndToggleStarItem(
                        bodyArea = bodyArea,
                        onDelete = { /* Handle delete action */ },
                        selectedItem.value
                    ) {
                        selectedItem.value = bodyArea
                        // Navigate to the corresponding Composable when clicked
                        navController.navigate(bodyArea)
                    }
                }
            }

        }
        // Workout Fragments
        WorkoutFragment(navController)

    }
}