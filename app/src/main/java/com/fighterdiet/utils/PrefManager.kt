package com.fighterdiet.utils

import android.content.Context
import android.content.SharedPreferences
import com.fighterdiet.app.MyApplication

object PrefManager {

    private val KEY_USER_MODEL: String? = "MODEL"
    private var sharedPreferences: SharedPreferences =
        MyApplication.application.applicationContext.getSharedPreferences(
            "",
            Context.MODE_PRIVATE
        )

    // Keys
    const val KEY_AUTH_TOKEN = "Auth_Token"
    const val KEY_USER_ID = "User ID"
    const val IS_LOGGED_IN = "is logged in"
    const val IS_SUBSCRIBED = "Is_user_subscribed"


    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }

    fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun putInt(key: String, value: Int?) {
        value?.let { sharedPreferences.edit().putInt(key, it).apply() }
    }

    fun putBoolean(key: String, value: Boolean?) {
        value?.let { sharedPreferences.edit().putBoolean(key, it).apply() }
    }

    /*   fun saveUserModel(userModel: User?) {
           val gson = Gson()
           val editor = sharedPreferences.edit()
           editor.putString(KEY_USER_MODEL, gson.toJson(userModel))
           editor.apply()
       }

       fun getUserModel(): User? {
           val gson: Gson = Gson()
           val strUserModel = sharedPreferences.getString(KEY_USER_MODEL, "")
           if (strUserModel.isNullOrEmpty())
               return null
           else
               return gson.fromJson(strUserModel, User::class.java)
       }*/

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearPref() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}