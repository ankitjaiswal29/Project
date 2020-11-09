package com.fighterdiet.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.fighterdiet.R
import com.fighterdiet.adapters.ViewPagerAdapter
import com.fighterdiet.databinding.ActivityLoginBinding
import com.fighterdiet.fragments.WalkThroughFragment
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Utils

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initialise()
    }

    private fun initialise() {
        adapter = ViewPagerAdapter(this)

        adapter.addFragment(WalkThroughFragment.getInstance(0))
        adapter.addFragment(WalkThroughFragment.getInstance(1))
        adapter.addFragment(WalkThroughFragment.getInstance(2))
        adapter.addFragment(WalkThroughFragment.getInstance(3))

        binding.viewpager.adapter = adapter
        binding.viewpager.offscreenPageLimit = 4
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.e(TAG, ">>>>> Position :: $position")
                setupIndicator(position)
            }
        })

        binding.tvForgotPassword.setOnClickListener(this)
        binding.tvCreateAccount.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    fun setupIndicator(position: Int) {
        when (position) {
            0 -> {
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            1 -> {
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            2 -> {
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            3 -> {
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_selected)
            }
        }

    }

    companion object {
        const val TAG = "LoginActivity"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_forgot_password -> {
                startActivity(ResetPasswordActivity.getStartIntent(this))
            }
            R.id.tv_create_account -> {
                startActivity(CreateAccountActivity.getStartIntent(this))
            }
            R.id.btnLogin -> {
                ProgressDialog.openSettings(this)
//                startActivity(IntroAndDecisionActivity.getStartIntent(this))
            }
        }
    }
}