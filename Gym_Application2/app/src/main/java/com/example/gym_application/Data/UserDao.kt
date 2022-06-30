package com.example.gym_application.Data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
// replace instead of igore

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addUser(exercises: Exercises)

    @Query("SELECT * FROM Exercises_Table ORDER BY id ASC")
     fun readAllData(): Flow<List<Exercises>>

    @Update(entity = Exercises::class)
    fun updateFunc(exercises: Exercises)

    @Query("DELETE FROM Exercises_Table")
    suspend fun deleteAll()





}