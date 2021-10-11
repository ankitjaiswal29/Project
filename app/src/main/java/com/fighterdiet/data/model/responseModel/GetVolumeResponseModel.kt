package com.fighterdiet.data.model.responseModel

data class GetVolumeResponseModel(
    val result: List<Result>
){
    data class Result(
        val volume_id: Int,
        val volume_name: String
    ){
        var isChecked = false
        fun getSelectedParseFormat(pos:Int): String{
            return "volume_id[$pos]"
        }
    }
}
