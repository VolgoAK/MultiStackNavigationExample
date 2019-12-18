package com.example.singleactivityexample.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.singleactivityexample.domain.NewsRepository
import com.example.singleactivityexample.model.User
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class UsersViewModel(
    private val repository: NewsRepository
): ViewModel() {

    val usersLiveData = flow <UsersScreenState>{
        emit(UsersScreenState.UsersState(repository.getAllUsers()))
    }.onStart {
        emit(UsersScreenState.LoadingState)
    }.asLiveData()

    sealed class UsersScreenState {
        data class UsersState(val users: List<User>): UsersScreenState()

        object LoadingState: UsersScreenState()
    }
}