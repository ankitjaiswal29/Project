package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityMemberShipBinding
import com.fighterdiet.utils.Constants

class MemberShipActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMemberShipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member_ship)
        initialize()
    }

    private fun initialize() {
        binding.clMemberShipYear.setOnClickListener(this)
        binding.btnMembershipMonth.setOnClickListener(this)
    }

    companion object {
        const val TAG = "MemberShipActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MemberShipActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        view?.id.let {
            when (it) {
                R.id.clMemberShipYear -> {
                    moveToNextFlow()
                }
                R.id.btnMembershipMonth -> {
                    moveToNextFlow()
                }
            }
        }
    }

    private fun moveToNextFlow() {
        if (Constants.isQuestonnaireCompleted) {
            startActivity(ProfileAfterQuestionsActivity.getStartIntent(this))
        } else {
            startActivity(DashboardActivity.getStartIntent(this))
        }
    }
}