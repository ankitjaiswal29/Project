package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.TrendingRepository

class TrendingViewModelProvider(private val trendingRepository: TrendingRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrendingViewModel(trendingRepository)as T
    }
}