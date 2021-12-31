package com.fighterdiet.utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fighterdiet.activities.LoginActivity
import com.fighterdiet.utils.Constants.HUNDRED
import com.fighterdiet.utils.Constants.ZERO
import com.google.android.material.snackbar.Snackbar
import java.net.URI
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


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

    fun getPercentage(selection: Int, total: Int): Int {
        var percentage: Int = HUNDRED
        return if (selection > ZERO) {
            percentage = try {
                selection * HUNDRED / total
            } catch (e: java.lang.Exception) {
                return percentage
            }
            percentage
        } else {
            ZERO
        }
    }

    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context?, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun hideKeyboard(activity: Activity) {
        val view = (activity.findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
        hideKeyboard(activity, view)
    }

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun loginAlertDialog(activity: Activity){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Complete registration for more information!")
        builder.setPositiveButton("Register") { dialog, which ->
            dialog.dismiss()
            activity.startActivity(Intent(activity, LoginActivity::class.java))
            activity.finishAffinity()
            return@setPositiveButton
        }

        builder.setNegativeButton("Dismiss") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

//    fun stringDateToMillies(): Long? {
//        val date = "Tue Apr 23 16:08:28 GMT+05:30 2013"
//        val formatter: DateTimeFormatter =
//            DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
//        val localDate: LocalDateTime = LocalDateTime.parse(date, formatter)
//        val timeInMilliseconds: Long = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
////        Log.d(TAG, "Date in milli :: FOR API >= 26 >>> $timeInMilliseconds")
//        return timeInMilliseconds
//    }

    fun getEncodedUrl(urlString: String): URL? {
        val url = URL(urlString)
        val uri = URI(
            url.protocol,
            url.userInfo,
            url.host,
            url.port,
            url.path,
            url.query,
            url.ref
        )
        return uri.toURL()
    }

}