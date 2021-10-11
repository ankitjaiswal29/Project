package com.fighterdiet.utils

import com.fighterdiet.data.model.responseModel.GetDietaryResponseModel
import com.fighterdiet.data.model.responseModel.GetMealResponseModel
import com.fighterdiet.data.model.responseModel.GetVolumeResponseModel
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel

object Constants {
    const val OPERATION_DELETE = 0
    const val OPERATION_REPORT_SPAM = 1

    var isQuestonnaireCompleted: Boolean = false
    var HUNDRED = 100
    var ZERO = 0
    var ISSUBSCRIPTION = false

    const val SERVER_URL = "http://quytech.net/fighter_diet_backend/public/api/"
    const val LIVE_URL = "https://fighterdietrecipe.com/api/"


    const val RECIPE_MODEL = "model_recipe"
    const val RECIPE_ID = "recipe_id"
    const val RECIPE_IMAGE = "recipe_image"
    const val RECIPE_NAME = "recipe_name"

    object RecipeDetails{
        var recipeNotes: String = ""
    }

    object RecipeFilter{
        var selectedDietaryFilter: HashMap<Int, GetDietaryResponseModel.Result> = HashMap()
        var selectedVolumeFilter: HashMap<Int, GetVolumeResponseModel.Result> = HashMap()
        var selectedMealFilter: HashMap<Int, GetMealResponseModel.Result> = HashMap()
    }
}