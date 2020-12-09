package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account)
        initialise()
    }

    private fun initialise() {
        binding.btnCreateAccount.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(IntroAndDecisionActivity.getStartIntent(this))
        })
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CreateAccountActivity::class.java)
        }
    }
}