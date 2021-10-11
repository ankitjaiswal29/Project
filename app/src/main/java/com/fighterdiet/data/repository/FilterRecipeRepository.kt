package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService

class FilterRecipeRepository(private val apiService: ApiService) {
    suspend fun getAllergyApi()=apiService.getAllergyApi()
    suspend fun getVolumeApi()=apiService.getVolumeApi()
    suspend fun getMealApi()=apiService.getMealApi()
}