package com.fighterdiet.data.repository
import com.fighterdiet.data.api.ApiService
class TrendingRepository(private val apiService: ApiService) {
    suspend fun trendingListApi(offset:Int, limit:Int)=apiService.getTrendingListApi(offset, limit)
}