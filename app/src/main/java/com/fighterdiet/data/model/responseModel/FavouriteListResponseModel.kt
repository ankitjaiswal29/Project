package com.fighterdiet.data.model.responseModel

data class FavouriteListResponseModel(val active: String, val is_subscribed: String, val result: List<Favourite>,
                                      val totalRecord: Int){
    data class Favourite(
        val recipe_id: Int?,
        val user_id: Int?,
        val recipe_name: String?,
        val calories: String?,
        val protein: String?,
        val serving_for: String?,
        val prep_time: String?,
        val cook_time: String?,
        val tips: String?,
        val meal: String?,
        val volume: String?,
        val recipe_image: String?,

        //self created field
        var isSelected: Boolean = false,
        var isDescOpened:Boolean = false,

        )

}