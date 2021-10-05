package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.VerifyOtpRequestModel

class VerifyOtpRepository(private val apiService: ApiService) {
    suspend fun verifyotpApi(verifyOtpRequestModel: VerifyOtpRequestModel)=apiService.verifyotpApi(verifyOtpRequestModel)
}