package com.fighterdiet.data.model.responseModel

data class GetMealResponseModel(
    val result: List<Result>
){
    data class Result(
        val meal_id: Int,
        val meal_name: String
    ){
        var isChecked = false
        fun getSelectedParseFormat(pos:Int): String{
            return "meal_id[$pos]"
        }
    }
}
