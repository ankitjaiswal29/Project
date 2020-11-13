package com.fighterdiet.model

data class Question(
    var question: String,
    var type: Int,
    var answers: ArrayList<String>
)