package com.fighterdiet.data.model.requestModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePasswordRequestModel(
    val confirm_password:String,
    val password:String,
    val old_password:String
):Parcelable
