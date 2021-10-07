package com.fighterdiet.data.model.requestModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddToFavRequestModel(
    val recipe_id:String,
    val user_id:String
):Parcelable
