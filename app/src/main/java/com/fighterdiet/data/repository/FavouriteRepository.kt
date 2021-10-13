package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService

class FavouriteRepository(private val apiService: ApiService) {
    suspend fun favouriteListApi(offset:Int, limit:Int)=apiService.getFavouriteListApi(offset,limit)
}