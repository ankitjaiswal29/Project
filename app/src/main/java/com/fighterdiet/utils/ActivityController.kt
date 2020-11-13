package com.fighterdiet.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

object ActivityController {
    @JvmOverloads
    fun startActivity(
        activity: Activity, klass: Class<*>?, bundle: Bundle? = null,
        finishPrevious: Boolean = false, finishAffinity: Boolean = false
    ) {
        val intent = Intent(activity, klass)
        if (bundle != null) intent.putExtras(bundle)
        activity.startActivity(intent)
        if (finishAffinity) activity.finishAffinity() else if (finishPrevious) activity.finish()
    }

    fun startActivity(activity: Activity, klass: Class<*>?, finishPrevious: Boolean) {
        startActivity(activity, klass, null, finishPrevious, false)
    }

    fun startActivity(
        activity: Activity, klass: Class<*>?, finishPrevious: Boolean,
        finishAffinity: Boolean
    ) {
        startActivity(activity, klass, null, finishPrevious, finishAffinity)
    }

    fun startActivityForResult(
        activity: Activity, klass: Class<*>?, bundle: Bundle?,
        finishPrevious: Boolean, finishAffinity: Boolean,
        requestCode: Int
    ) {
        val intent = Intent(activity, klass)
        if (bundle != null) intent.putExtras(bundle)
        activity.startActivityForResult(intent, requestCode)
        if (finishAffinity) activity.finishAffinity() else if (finishPrevious) activity.finish()
    }

    fun startActivityForResult(activity: Activity, klass: Class<*>?, requestCode: Int) {
        startActivityForResult(
            activity, klass, null, false, false,
            requestCode
        )
    }

    fun startActivityForResult(
        activity: Activity, klass: Class<*>?, bundle: Bundle?,
        requestCode: Int
    ) {
        startActivityForResult(
            activity, klass, bundle, false, false,
            requestCode
        )
    }

    fun startActivityForResult(
        activity: Activity, klass: Class<*>?, finishPrevious: Boolean,
        requestCode: Int
    ) {
        startActivityForResult(
            activity, klass, null, finishPrevious, false,
            requestCode
        )
    }

    fun startActivityForResult(
        activity: Activity, klass: Class<*>?, bundle: Bundle?,
        finishPrevious: Boolean, requestCode: Int
    ) {
        startActivityForResult(
            activity, klass, bundle, finishPrevious, false,
            requestCode
        )
    }

    fun startActivityForResult(
        activity: Activity, klass: Class<*>?, finishPrevious: Boolean,
        finishAffinity: Boolean, requestCode: Int
    ) {
        startActivityForResult(
            activity, klass, null, finishPrevious, finishAffinity,
            requestCode
        )
    }

    @JvmOverloads
    fun startActivity(
        fragment: Fragment, klass: Class<*>?, bundle: Bundle? = null,
        finishPrevious: Boolean = false, finishAffinity: Boolean = false
    ) {
        val intent = Intent(fragment.activity, klass)
        if (bundle != null) intent.putExtras(bundle)
        fragment.startActivity(intent)
        if (finishAffinity) fragment.activity!!
            .finishAffinity() else if (finishPrevious) fragment.activity!!.finish()
    }

    fun startActivityForResult(
        fragment: Fragment, klass: Class<*>?, bundle: Bundle?,
        finishPrevious: Boolean, finishAffinity: Boolean,
        requestCode: Int
    ) {
        val intent = Intent(fragment.activity, klass)
        if (bundle != null) intent.putExtras(bundle)
        fragment.startActivityForResult(intent, requestCode)
        if (finishAffinity) fragment.activity!!
            .finishAffinity() else if (finishPrevious) fragment.activity!!.finish()
    }
}