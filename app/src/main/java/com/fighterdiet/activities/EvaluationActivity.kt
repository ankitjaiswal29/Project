package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityEvaluationBinding

class EvaluationActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding:ActivityEvaluationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_evaluation)
        initialise()
    }

    private fun initialise() {
        binding.tvNext.setOnClickListener(this)
        binding.tvNewMeal.setOnClickListener(this)
        binding.tvSameMeal.setOnClickListener(this)
        binding.tvChangeAnswer.setOnClickListener(this)
        binding.tvMostPopular.setOnClickListener(this)
        binding.tvYourRecipe.setOnClickListener(this)
        binding.ivClose.setOnClickListener(this)
    }


    companion object {
        const val TAG = "EvaluationActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, EvaluationActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){

            R.id.tv_new_meal -> {

            }
            R.id.tv_next -> {

            }
            R.id.tv_same_meal -> {

            }
            R.id.tv_change_answer -> {

            }
            R.id.tv_most_popular -> {

            }
            R.id.tv_your_recipe -> {

            }
            R.id.iv_close -> {

            }

        }
    }
}