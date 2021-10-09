package com.fighterdiet.data.model.responseModel

data class CommentListResponseModel(
    val comment_recipe: List<CommentRecipe>,
    val comment_recipe_total: Int
){
    data class CommentRecipe(
        val comments: String,
        val created_at: String,
        val id: Int,
        val receipe_id: Int,
        val recipe_image: String,
        val status: Int,
        val updated_at: String,
        val user_id: Int,
        val user_name: String
    )
}