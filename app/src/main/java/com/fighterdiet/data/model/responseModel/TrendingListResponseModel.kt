package com.fighterdiet.data.model.responseModel

data class TrendingListResponseModel(
    val is_subscribed: String,
    val result: List<Result>
){
    data class Result(
        val calories: String,
        val cook_time: String,
        val id: Int,
        val meal: String,
        val prep_time: String,
        val protein: String,
        val recipe_image: String,
        val recipe_name: String,
        val serving_for: String,
        val total: Int,
        val volume: String
    )
}