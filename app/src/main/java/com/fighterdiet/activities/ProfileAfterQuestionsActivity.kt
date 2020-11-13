package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityProfileAfterQuestionsBinding

class ProfileAfterQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding:ActivityProfileAfterQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_after_questions)
        initialise()
    }

    private fun initialise() {
        binding.tvNext.setOnClickListener(this)
    }

    companion object {
        const val TAG = "ProfileAfterQuestionsActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ProfileAfterQuestionsActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        view.let {
            when(view?.id){

                R.id.tv_next ->{
                    startActivity(PicOneDayMealsActivity.getStartIntent(this))
                }

            }
        }
    }
}