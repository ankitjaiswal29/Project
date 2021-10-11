package com.fighterdiet.data.model.responseModel

data class GetDietaryResponseModel(
    val result: List<Result>
){
    data class Result(
        val allergy_id: Int,
        val allergy_name: String
    ){
        var isChecked = false
        fun getSelectedParseFormat(pos:Int): String{
            return "allergy_id[$pos]"
        }
    }
}
