package com.example.gym_application.Data


import androidx.lifecycle.*
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val allWords: LiveData<List<Exercises>> = repository.allWords.asLiveData()

    fun addUser(user:Exercises) = viewModelScope.launch {
        withContext(Dispatchers.IO) {repository.addUser(user)}
    }

}

class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



