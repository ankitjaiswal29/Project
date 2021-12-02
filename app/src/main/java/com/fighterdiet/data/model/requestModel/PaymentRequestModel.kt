package com.fighterdiet.data.model.requestModel

data class PaymentRequestModel(
    val orderId: String,
    val packageName: String,
    val productId: String,
    val purchaseTime: String,
    val purchaseState: String,
    val purchaseToken: String,
    val quantity: Int,
    val autoRenewing: Boolean,
    val acknowledged: Boolean,
    var amount:String
)