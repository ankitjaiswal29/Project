package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.LogOutRepository

class LogOutViewModelProvider(private val logOutRepository: LogOutRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogOutViewModel(logOutRepository)as T
    }
}