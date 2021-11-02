package com.fighterdiet.data.model.responseModel

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

data class RecipeContentResponseModel(
    val directions: List<Direction>,
    var favourite: Int,
    val info: List<Info>,
    val ingredients: List<Ingredient>,
    var recipe_note: Any,
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

    @Parcelize
    data class RecipeNote(
        val id: Double,
        val user_id: Double,
        val receipe_id: Double,
        val type: Double,
        val description: String,
        val created_at: String,
        val updated_at: String
    ): Parcelable
}