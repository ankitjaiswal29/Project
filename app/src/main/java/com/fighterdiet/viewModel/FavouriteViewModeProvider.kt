package com.fighterdiet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.data.repository.FavouriteRepository

class FavouriteViewModeProvider(private val favouriteRepository: FavouriteRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouriteViewModel(favouriteRepository) as T
    }
}