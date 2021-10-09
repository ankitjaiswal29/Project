package com.fighterdiet.data.model.requestModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddNotesRequestModel(
    val recipe_id:String,
    val description:String
):Parcelable
