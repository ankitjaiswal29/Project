package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.ChangePasswordRepository
import com.fighterdiet.data.repository.LoginRepository

class ChangePassViewModelProvider(private val changePasswordRepository: ChangePasswordRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChangePassViewModel(changePasswordRepository) as T
    }
}