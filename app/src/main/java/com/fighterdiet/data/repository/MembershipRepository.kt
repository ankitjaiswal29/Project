package com.fighterdiet.data.repository

import com.fighterdiet.data.api.ApiService
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.requestModel.PaymentRequestModel

class MembershipRepository(val apiService: ApiService) {
    suspend fun callPaymentApi(paymentRequestModel: PaymentRequestModel) = apiService.callSubscriptionApi(paymentRequestModel)
}