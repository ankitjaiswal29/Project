package com.fighterdiet.utils

fun getProtein(protein:String): String{
    var convertedString = protein
    when(protein){
        ".1" -> convertedString = "0.1"
        ".2" -> convertedString = "0.2"
        ".3" -> convertedString = "0.3"
        ".4" -> convertedString = "0.4"
        ".5" -> convertedString = "0.5"
        ".6" -> convertedString = "0.6"
        ".7" -> convertedString = "0.7"
        ".8" -> convertedString = "0.8"
        ".9" -> convertedString = "0.9"
    }
    return convertedString
}