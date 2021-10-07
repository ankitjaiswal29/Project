package com.fighterdiet.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.fragments.RecipeInfoRepository
import com.fighterdiet.viewModel.HomeViewModel
import com.fighterdiet.viewModel.RecipeInfoViewModel

class RecipeInfoViewModelProvider(private val recipeInfoRepository: RecipeInfoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecipeInfoViewModel(recipeInfoRepository) as T
    }
}