package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.AboutPaulinNordinRepository

class AboutPaulinNordinViewModelProvider(private val aboutPaulinNordinRepository: AboutPaulinNordinRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return AboutPaulinNordinViewModel(aboutPaulinNordinRepository)as T
    }
}