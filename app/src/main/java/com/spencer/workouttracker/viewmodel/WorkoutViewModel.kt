package com.spencer.workouttracker.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.spencer.workouttracker.database.WorkoutCategory
import com.spencer.workouttracker.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val _workoutCategories = MutableLiveData<List<WorkoutCategory>>()
    val workoutCategories: LiveData<List<WorkoutCategory>> = _workoutCategories

    init {
        viewModelScope.launch {
            _workoutCategories.value = workoutRepository.getAllWorkoutCategories()
        }
    }

    fun addWorkoutCategory(category: WorkoutCategory) {
        viewModelScope.launch {
            workoutRepository.insertWorkoutCategory(category)
        }
    }
    fun getWeightSum(): Flow<Int> {
        return workoutRepository.getWeightSum()
    }
    fun updateWeightSum(weight: Int) {
        viewModelScope.launch {
            workoutRepository.updateWeightSum(weight)
        }
    }

    // Other methods to interact with the database, such as updateWorkoutCategory(), deleteWorkoutCategory(), etc.
}