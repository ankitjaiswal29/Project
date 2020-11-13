package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityIntroAndDecisionBinding
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.Utils

class IntroAndDecisionActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityIntroAndDecisionBinding
    private var selected: Int = -1 // 0 -> Not selected 1 -> Selected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_and_decision)
        initialise()
    }

    private fun initialise() {
        binding.clIntroScreen.visibility = View.VISIBLE
        binding.clDecisionScreen.visibility = View.GONE

        binding.tvIntroNext.setOnClickListener(this)
        binding.tvDecisionNext.setOnClickListener(this)

        binding.tvDecisionYes.setOnClickListener(this)
        binding.tvDecisionNo.setOnClickListener(this)
    }

    companion object {
        const val TAG = "IntroAndDecisionActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, IntroAndDecisionActivity::class.java)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_decision_yes -> {
                selected = 1
                binding.tvDecisionYes.setBackgroundResource(R.drawable.shape_decision_selected)
                binding.tvDecisionNo.setBackgroundResource(R.drawable.shape_decision_unselected)
            }
            R.id.tv_decision_no -> {
                selected = 0
                binding.tvDecisionYes.setBackgroundResource(R.drawable.shape_decision_unselected)
                binding.tvDecisionNo.setBackgroundResource(R.drawable.shape_decision_selected)
            }
            R.id.tvDecisionNext -> {
                if (selected != -1) {
                    if (selected == 1) {
                        // Yes
                        Constants.isQuestonnaireCompleted = true
                        startActivity(QuizActivity.getStartIntent(this))
                        finish()

                    } else {
                        // No
                        Constants.isQuestonnaireCompleted = false
                        startActivity(MemberShipActivity.getStartIntent(this))
                        finish()

                    }
                } else {
                    Utils.showSnackBar(v, getString(R.string.str_please_select_any_option))
                }
            }
            R.id.tvIntroNext -> {
                binding.clIntroScreen.visibility = View.GONE
                binding.clDecisionScreen.visibility = View.VISIBLE
            }
        }
    }
}