package com.fighterdiet.model

data class Question(
    var id:Int,
    var question: String,
    var type: Int,
    var isMultipleSelection:Boolean,
    var answers: ArrayList<AnswerModel>
)