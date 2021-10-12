package com.fighterdiet.data.model.responseModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckUserNameResponseModel (
    val user_name: String,
    val key: String
): Parcelable
