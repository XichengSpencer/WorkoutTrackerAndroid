package com.spencer.workouttracker.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class WorkoutRepository (private val context: Context) {
    //generate preference key by id
    private object PreferencesKeys {
        val weightSum = intPreferencesKey("weight_sum")
    }
    companion object {
        private var INSTANCE: WorkoutRepository? = null
        fun getInstance(context: Context): WorkoutRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: WorkoutRepository(context.applicationContext).also { INSTANCE = it }
            }
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "workout_pref")
    //get data by key
    val weightSumFlow: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // TODO: Create dynamic GLOBAL_WEIGHT_SUM_KEY as ID to retreive each workout record
            preferences[PreferencesKeys.weightSum] ?: 0
        }

    suspend fun updateWeightSum(weight: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.weightSum] = weight
        }
    }


}