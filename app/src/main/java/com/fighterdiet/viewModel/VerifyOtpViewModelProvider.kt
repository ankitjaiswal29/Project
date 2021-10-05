package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.VerifyOtpRepository

class VerifyOtpViewModelProvider(private val verifyOtpRepository: VerifyOtpRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VerifyOtpViewModel(verifyOtpRepository)as T
    }
}