package com.fighterdiet.utils

import android.R
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

object Utils {
    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun isOnline(context: Context): Boolean {
        return try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.activeNetworkInfo
            info != null && info.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun hideKeyboard(activity: Activity) {
        val view = (activity.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
        hideKeyboard(activity, view)
    }

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}