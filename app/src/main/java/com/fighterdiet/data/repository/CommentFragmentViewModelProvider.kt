package com.fighterdiet.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.fragments.RecipeInfoRepository
import com.fighterdiet.viewModel.CommentFragmentViewModel
import com.fighterdiet.viewModel.HomeViewModel
import com.fighterdiet.viewModel.RecipeInfoViewModel

class CommentFragmentViewModelProvider(private val recipeCommentRepository: CommentFragmentRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentFragmentViewModel(recipeCommentRepository) as T
    }
}