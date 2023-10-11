package com.spencer.workouttracker.viewmodel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.spencer.workouttracker.Workout
import com.spencer.workouttracker.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    val weightSum: LiveData<Int> = workoutRepository.weightSumFlow.asLiveData()

    //TODO: make bodyArea stored in the dataStore as a list of Strings
    private val bodyAreas = listOf("Arms", "Legs", "Chest", "Back")
    //TODO: Use proto to store Workout list
    private val _workouts: MutableLiveData<List<Workout>> by lazy {
        MutableLiveData<List<Workout>>().also { it.value = workouts }
    }

    private val _selectedBodyArea = mutableStateOf(bodyAreas[0])
    var selectedBodyArea: String by mutableStateOf(bodyAreas[0])
//        get() = _selectedBodyArea.value
//        private set(value) {
//            _selectedBodyArea.value = value
//        }


    var workouts: List<Workout> by mutableStateOf(
        //proto buffer needed
        listOf<Workout>(
            Workout("Bicep Curls", 20, 10, 2),
            Workout("Tricep Extensions", 15, 12, 2),
            Workout("Hammer Curls", 25, 8, 3)
        )
    )

    fun updateWeightSum(newWeight: Int){
        viewModelScope.launch {
            workoutRepository.updateWeightSum(newWeight)
        }
    }

    //get weight Sum to let all logic stay in view model
    fun getWeightSum(){
        //get weight sum

        //update id
        //return weight sum
    }
    fun getPrevWeightSum(){}

}