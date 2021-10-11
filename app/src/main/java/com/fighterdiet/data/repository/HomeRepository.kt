package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService

class HomeRepository(private val apiService: ApiService) {
    suspend fun recipeListApi(
        offset: Int,
        limit: Int,
        search: String,
        selectedDietaryMap: HashMap<String, Int>,
        selectedVolumeMap: HashMap<String, Int>,
        selectedMealMap: HashMap<String, Int>
    ) = apiService.getRecipeListApi(offset,limit,search, selectedDietaryMap, selectedVolumeMap, selectedMealMap)
}