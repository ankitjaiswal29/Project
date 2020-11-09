package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.QuizAnswerAdapter
import com.fighterdiet.databinding.ActivityQuizBinding

class QuizActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz)
        initialise()
    }

    private fun initialise() {
        binding.btnNext.setOnClickListener(this)
        binding.ivPrevious.setOnClickListener(this)
        setAdapter()
    }

    companion object {
        const val TAG = "QuizActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, QuizActivity::class.java)
        }
    }

    private fun setAdapter() {
//        binding.rvAnswer.layoutManager = LinearLayoutManager(this)
        binding.rvAnswer.layoutManager = GridLayoutManager(this,3)
        binding.rvAnswer.adapter = QuizAnswerAdapter(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {

            }

            R.id.ivPrevious ->{

            }

        }
    }
}