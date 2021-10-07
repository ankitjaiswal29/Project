package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService

class FaqRepository(private val apiService: ApiService) {
    suspend fun faqListApi(limit:Int,offset:Int)=apiService.getFaqListApi(limit,offset)
}