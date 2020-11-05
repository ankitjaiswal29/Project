package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.fighterdiet.R

class ResetPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
    }

    private fun initialise() {

    }

    companion object {
        const val TAG = "ResetPassword"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }
}