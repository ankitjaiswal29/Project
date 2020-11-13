package com.fighterdiet.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityPersonalChartBinding
import com.fighterdiet.fragments.CommentFragment


class PersonalChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_chart)

        binding.ivQuestion.setOnClickListener {
            val commentFragment = CommentFragment.newInstance()
            commentFragment.show(
                supportFragmentManager,
                CommentFragment::class.simpleName
            )
        }

    }
}