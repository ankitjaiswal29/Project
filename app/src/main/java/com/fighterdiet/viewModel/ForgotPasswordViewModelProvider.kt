package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.ForgotPasswordRepository

class ForgotPasswordViewModelProvider(private val forgotPasswordRepository: ForgotPasswordRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForgotPasswordViewModel(forgotPasswordRepository)as T
    }

}