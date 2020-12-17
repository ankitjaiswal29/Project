package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityProfileAfterQuestionsBinding
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.Utils

class ProfileAfterQuestionsActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding:ActivityProfileAfterQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_after_questions)
        initialise()
    }

    private fun initialise() {
        binding.tvNext.setOnClickListener(this)

        var goalPercentage: Int = Utils.getPercentage(80, 100)

        if (goalPercentage > Constants.HUNDRED) {
            goalPercentage = Constants.HUNDRED
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.progressBar.setProgress(goalPercentage, true)
        } else {
            binding.progressBar.progress = goalPercentage
        }
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
                R.id.tvNext ->{
                    startActivity(PicOneDayMealsActivity.getStartIntent(this))
                }

            }
        }
    }
}