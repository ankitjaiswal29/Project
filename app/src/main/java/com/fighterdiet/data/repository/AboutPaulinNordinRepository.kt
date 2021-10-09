package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService

class AboutPaulinNordinRepository(private val apiService: ApiService) {
    suspend fun aboutListApi()=apiService.getaboutApi()
}