package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService

class HomeRepository(private val apiService: ApiService) {
    suspend fun recipeListApi(offset:Int, limit:Int, search:String) = apiService.getRecipeListApi(offset,limit,search)
}