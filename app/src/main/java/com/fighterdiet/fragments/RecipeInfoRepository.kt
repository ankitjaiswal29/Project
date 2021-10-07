package com.fighterdiet.fragments

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.*

class RecipeInfoRepository(private val apiService: ApiService) {
    suspend fun getRecipeContentApi(model: RecipeContentRequestModel) = apiService.getRecipeContentApi(model)
    suspend fun addRecipeToFavApi(model: AddToFavRequestModel) = apiService.getRecipeToFavApi(model)
    suspend fun addNotesApi(model: AddNotesRequestModel) = apiService.addNotesApi(model)
    suspend fun updateNotesApi(model: UpdateNotesRequestModel) = apiService.updateNotesApi(model)
}