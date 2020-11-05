package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fighterdiet.R

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
    }

    companion object {
        const val TAG = "QuizActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, QuizActivity::class.java)
        }
    }
}