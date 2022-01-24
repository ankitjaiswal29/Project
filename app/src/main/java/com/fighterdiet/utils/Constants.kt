package com.fighterdiet.utils

import androidx.lifecycle.MutableLiveData
import com.fighterdiet.data.model.responseModel.*

object Constants {

    interface InAppSubsProducts {
        companion object {
            val monthly_subscription = "com.fighter_diet_recipe.monthly"
            val yearly_subscription = "com.fighter_diet_recipe.yearly"
//            val three_monthly_test = "com.fighter_diet.test"
//            val test_yearly = "com.test.yearly"
//            val six_monthly_test = "com.test.half_year"
        }
    }

    val productIds = arrayOf<String>("monthly_test_subscription", "yearly_test_subscription")
    val deviceType = "1"
    val DEFAULT_PROFILE_ICON_SIZE = 200
    val EVENT_PUSH_NOTIFICATION = "EVENT_PUSH_NOTIFICATION"
    val KEY_NOTIFICATION_TYPE="KEY_NOTIFICATION_TYPE"

    const val OPERATION_DELETE = 0
    const val OPERATION_REPORT_SPAM = 1

    var isQuestonnaireCompleted: Boolean = false
    var HUNDRED = 100
    var ZERO = 0
    var ISSUBSCRIPTION = false

    const val SERVER_URL = "http://quytech.net/fighter_diet_backend/public/api"
    const val LIVE_URL = "https://fighterdietrecipe.com/api"



    const val RECIPE_MODEL = "model_recipe"
    const val RECIPE_ID = "recipe_id"
    const val RECIPE_IMAGE = "recipe_image"
    const val RECIPE_NAME = "recipe_name"

    object DashboardDetails{
        var isApiRequestNeeded: Boolean = true
        var recipiesModel: RecipeListResponseModel? = null
    }

    object RecipeDetails{
        var recipeNotes: String = ""
        var recipeNotesLive: MutableLiveData<String> = MutableLiveData("")
    }

    object RecipeFilter{
        /*var dietaryItemCount = 0
        var volumeItemCount = 0
        var mealItemCount = 0
        */
        var isFilterCleared: Boolean = false
        var isFilterApplied: Boolean = false
        var totalFilterCount :Int = 0

        var selectedDietaryFilter: HashMap<Int, GetDietaryResponseModel.Result> = HashMap()
        var selectedVolumeFilter: HashMap<Int, GetVolumeResponseModel.Result> = HashMap()
        var selectedMealFilter: HashMap<Int, GetMealResponseModel.Result> = HashMap()
    }
}