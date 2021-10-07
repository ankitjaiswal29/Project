package com.fighterdiet.data.model.requestModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateNotesRequestModel(
    val note_id:String,
    val description:String
):Parcelable
