package com.fighterdiet.activities
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityMemberShipBinding

class MemberShipActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding :ActivityMemberShipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_member_ship)
        initialize()
    }

    private fun initialize() {
        binding.tvSignMonth.setOnClickListener(this)
    }

    companion object {
        const val TAG = "MemberShipActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
      view?.id.let {
          when(it){
              R.id.tv_sign_month ->{

              }
          }
      }
    }
}