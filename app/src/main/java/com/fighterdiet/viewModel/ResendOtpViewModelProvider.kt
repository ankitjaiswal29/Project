package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.ResendOtpRepository

class ResendOtpViewModelProvider(private val resendOtpRepository: ResendOtpRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ResendOtpViewModel(resendOtpRepository)as T
    }
}