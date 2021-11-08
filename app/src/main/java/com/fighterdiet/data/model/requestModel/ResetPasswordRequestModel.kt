package com.fighterdiet.data.model.requestModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResetPasswordRequestModel(
    val password:String,
    val user_id:String
):Parcelable
