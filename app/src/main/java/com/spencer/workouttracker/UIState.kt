package com.spencer.workouttracker
//For error handling, we will use sealed classes to represent the different states of the UI
sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}