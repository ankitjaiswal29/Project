package com.fighterdiet.data.model.requestModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentListRequestModel(
    val offset:Int,
    val limit:Int,
    val recipe_id:String
):Parcelable
