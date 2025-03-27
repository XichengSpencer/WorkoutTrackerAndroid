package com.spencer.workouttracker.module

import android.content.Context
import androidx.room.Room
import com.spencer.workouttracker.database.WorkoutCategoryDao
import com.spencer.workouttracker.database.WorkoutDao
import com.spencer.workouttracker.database.WorkoutDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWorkoutDatabase(
        @ApplicationContext context: Context
    ): WorkoutDatabase {
        return Room.databaseBuilder(
            context,
            WorkoutDatabase::class.java,
            WorkoutDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideWorkoutDao(database: WorkoutDatabase): WorkoutDao {
        return database.workoutDao()
    }

    @Provides
    fun provideWorkoutCategoryDao(database: WorkoutDatabase): WorkoutCategoryDao {
        return database.workoutCategoryDao()
    }
}