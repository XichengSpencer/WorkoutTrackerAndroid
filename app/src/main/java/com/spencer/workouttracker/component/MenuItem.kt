package com.spencer.workouttracker.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spencer.workouttracker.R
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteAndToggleStarItem(
    bodyArea: String,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    if (delta > 0) { // Only allow dragging to the right
                        offsetX += delta
                    }
                },
                onDragStopped = { velocity ->
                    if (offsetX > (screenWidth / 4)) {
                        onItemDeleted()
                    } else {
                        offsetX = 0f
                    }
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var isStarFilled by remember { mutableStateOf(false) }
            val starIcon =
                if (isStarFilled) R.drawable.ic_star_24 else R.drawable.ic_star_outline_24

            Icon(
                painter = painterResource(id = starIcon),
                contentDescription = "Star",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { isStarFilled = !isStarFilled }
            )
            Text(
                text = bodyArea,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}




fun onItemDeleted() {
    TODO("Not yet implemented")
}