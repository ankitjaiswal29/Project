package com.fighterdiet.data.model.responseModel

data class GetAllergyResponseModel(
    val result: List<Result>
){
    data class Result(
        val allergy_id: Int,
        val allergy_name: String
    ){
        var isChecked = false
    }
}
