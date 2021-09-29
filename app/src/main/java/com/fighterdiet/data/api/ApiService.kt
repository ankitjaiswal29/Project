package com.fighterdiet.data.api

import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.RegisterRequestModel
import com.fighterdiet.data.model.responseModel.RegisterResponseModel
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun registerApi(@Body registerRequestModel: RegisterRequestModel): ApiResponse<ArrayList<RegisterResponseModel>>


}