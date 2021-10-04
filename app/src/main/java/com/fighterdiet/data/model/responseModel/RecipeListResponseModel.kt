package com.fighterdiet.data.model.responseModel

data class RecipeListResponseModel(
    val active: String,
    val is_subscribed: String,
    val result: List<Recipies>,
    val totalRecord: Int
){
    data class Recipies(
        val id: Int?,
        val recipe_name: String?,
        val recipe_image: String?,
        val calories: String?,
        val protein: String?,
        val serving_for: String?,
        val prep_time: String?,
        val cook_time: String?,
        val tips: String?,
        val meal: String?,
        val volume: String?,
        var isSelected: Boolean = false,
        var isDescOpened:Boolean = false
    )
}
