package com.fighterdiet.activities

import android.graphics.ColorFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fighterdiet.R
import com.fighterdiet.utils.Utils

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onCreate(savedInstanceState, persistentState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Utils.hideKeyboard(this)
       // requestWindowFeature(Window.FEATURE_NO_TITLE)
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    abstract fun setupUI()
    abstract fun setupViewModel()
    abstract fun setupObserver()

}