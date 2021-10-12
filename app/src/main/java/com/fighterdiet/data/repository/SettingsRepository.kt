package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.model.requestModel.ChangePasswordRequestModel
import com.fighterdiet.data.model.requestModel.LogoutRequestModel

class SettingsRepository(private val apiService: ApiService) {
    suspend fun logoutAPI(logoutRequestModel: LogoutRequestModel)=apiService.logOutApi(logoutRequestModel)
}