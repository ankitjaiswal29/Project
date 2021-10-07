package com.fighterdiet.data.model.responseModel

data class FaqListResponseModel(
    val result: List<Result>
){
    data class Result(
        val answer: String,
        val id: Int,
        val question: String
    )
}