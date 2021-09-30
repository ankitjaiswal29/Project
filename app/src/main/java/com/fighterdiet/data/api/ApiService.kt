package com.fighterdiet.data.api

import com.fighterdiet.data.model.ApiResponse
import com.fighterdiet.data.model.requestModel.LoginRequestModel
import com.fighterdiet.data.model.requestModel.RegisterRequestModel
import com.fighterdiet.data.model.responseModel.LoginResponseModel

import com.fighterdiet.data.model.responseModel.RegistrationResponseModel
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun registerApi(@Body registerRequestModel: RegisterRequestModel): ApiResponse<RegistrationResponseModel>


    @POST("login")
    suspend fun loginApi(@Body registerRequestModel: LoginRequestModel): ApiResponse<LoginResponseModel>

}