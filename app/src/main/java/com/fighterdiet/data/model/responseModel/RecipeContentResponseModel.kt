package com.fighterdiet.data.model.responseModel

data class RecipeContentResponseModel(
    val directions: List<Direction>,
    var favourite: Int,
    val info: List<Info>,
    val ingredients: List<Ingredient>,
    val recipe_note: Any,
    val tips: List<Tip>
){
    data class Direction(
        val created_at: String,
        val direction: String,
        val id: Int,
        val title: String,
        val unit: Any,
        val weight: Any
    )

    data class Info(
        val calories: String,
        val cook_time: String,
        val prep_time: String,
        val protein: String,
        val recipe_meal: Any,
        val recipe_volume: String,
        val serving_for: String
    )

    data class Ingredient(
        val `data`: List<Data>,
        val titie: String
    ){
        data class Data(
            val category_id: Int,
            val category_name: String,
            val created_at: Any,
            val id: Int,
            val ingredients: String,
            val unit: String,
            val weight: String
        )
    }

    data class Tip(
        val created_at: String,
        val id: Int,
        val recipe_id: Int,
        val tips: String,
        val updated_at: String
    )

    data class RecipeNote(
        val id: Int,
        val user_id: Int,
        val receipe_id: Int,
        val type: Int,
        val description: String,
        val created_at: String,
        val updated_at: String
    )
}