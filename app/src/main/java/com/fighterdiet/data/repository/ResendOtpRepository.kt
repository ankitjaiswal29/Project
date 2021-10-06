package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.ForgotPasswordRequestModel

class ResendOtpRepository(private val apiService: ApiService) {
    suspend fun resendOtpApi(forgotPasswordRequestModel: ForgotPasswordRequestModel)=apiService.resendOtpApi(forgotPasswordRequestModel)
}