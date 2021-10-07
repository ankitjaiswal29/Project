package com.fighterdiet.data.model.requestModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddCommentRequestModel(
    val recipe_id:String,
    val user_id:String,
    val comments:String
):Parcelable
