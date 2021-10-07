package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.FaqRepository

class FaqViewModelProvider(private val faqRepository: FaqRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FaqViewModel(faqRepository) as T
    }

}