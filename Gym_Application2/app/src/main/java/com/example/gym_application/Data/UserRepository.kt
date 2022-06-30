package com.example.gym_application.Data


import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class UserRepository(val userDao: UserDao) {

    val allWords: Flow<List<Exercises>> = userDao.readAllData()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addUser(user: Exercises){
        userDao.addUser(user)
    }
}