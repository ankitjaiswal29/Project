package com.fighterdiet.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.viewModel.FilterRecipeViewModel
import com.fighterdiet.viewModel.HomeViewModel

class FilterRecipeModelProvider(private val filterRepo: FilterRecipeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FilterRecipeViewModel(filterRepo) as T
    }
}