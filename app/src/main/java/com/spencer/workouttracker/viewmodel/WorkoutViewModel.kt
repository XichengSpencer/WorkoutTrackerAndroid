package com.spencer.workouttracker.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.spencer.workouttracker.database.Workout
import com.spencer.workouttracker.database.WorkoutCategory
import com.spencer.workouttracker.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _workoutCategories = MutableLiveData<List<WorkoutCategory>>()
    val workoutCategories: LiveData<List<WorkoutCategory>> = _workoutCategories

    private val _selectedCategory = MutableLiveData<WorkoutCategory?>()
    val selectedCategory: LiveData<WorkoutCategory?> = _selectedCategory

    private val _workoutsForSelectedCategory = MutableLiveData<List<Workout>>()
    val workoutsForSelectedCategory: LiveData<List<Workout>> = _workoutsForSelectedCategory
    // LiveData for the selected category

    // Derived LiveData for weightSum
    val weightSum: LiveData<Int> = _selectedCategory.map { category ->
        category?.weightSum ?: 0
    }

    // Method to clear weightSum (affects the category directly)
    fun clearWeightSum() {
        _selectedCategory.value?.let { currentCategory ->
            _selectedCategory.value = currentCategory.copy(weightSum = 0)
        }
    }

    // Method to update the weightSum dynamically
    fun updateWeightSum(newWeightSum: Int) {
        _selectedCategory.value?.let { currentCategory ->
            _selectedCategory.value = currentCategory.copy(weightSum = newWeightSum)
        }
    }
    init {
//        viewModelScope.launch {
//            _workoutCategories.value = workoutRepository.getAllWorkoutCategories()
//        }
         viewModelScope.launch(Dispatchers.IO) {
            val categories = workoutRepository.getAllWorkoutCategories()
            // Switch back to Main thread to update LiveData if needed,
            // though setting LiveData.value is main-safe.
            _workoutCategories.postValue(categories)
         }
    }
    fun insertWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.insertWorkout(workout)
            loadWorkoutsForCategory(workout.categoryId)
        }
    }
    fun setSelectedCategory(category: WorkoutCategory?) {
        _selectedCategory.value = category
        if (category != null) {
            viewModelScope.launch {
                _workoutsForSelectedCategory.value = workoutRepository.getWorkoutsByCategory(category.id)
            }
        } else {
            _workoutsForSelectedCategory.value = emptyList()
        }
    }
    fun loadWorkoutsForCategory(categoryId: Int) {
        viewModelScope.launch {
            _workoutsForSelectedCategory.value = workoutRepository.getWorkoutsByCategory(categoryId)
        }
    }
    fun updateWorkoutCategory(category: WorkoutCategory) {
        viewModelScope.launch {
            workoutRepository.updateWorkoutCategory(category)
        }
    }
    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.updateWorkout(workout)
        }
    }
    fun addWorkoutCategory(category: WorkoutCategory) {
        viewModelScope.launch {
            workoutRepository.insertWorkoutCategory(category)
        }
    }

//    fun getWeightSum(): Flow<Int> {
//
//    }
//    fun updateWeightSum(weight: Int) {
//        viewModelScope.launch {
//            workoutRepository.updateWeightSum(weight)
//        }
//    }

    // Other methods to interact with the database, such as updateWorkoutCategory(), deleteWorkoutCategory(), etc.
}