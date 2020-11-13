package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        initialise()
    }

    private fun initialise() {
        binding.btnResetPassword.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    companion object {
        const val TAG = "ResetPassword"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }
}