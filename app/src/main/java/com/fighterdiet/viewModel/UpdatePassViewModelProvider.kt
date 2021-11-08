package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.ChangePasswordRepository
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.data.repository.UpdatePasswordRepository

class UpdatePassViewModelProvider(private val updatePasswordRepository: UpdatePasswordRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdatePassViewModel(updatePasswordRepository) as T
    }
}