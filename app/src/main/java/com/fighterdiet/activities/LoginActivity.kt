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
import java.util.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var timer: Timer
    private var duration: Long = 2 * 1000 // Seconds
    private var currentPage: Int = 0

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
//                Log.e(TAG, ">>>>> Position :: $position")
                currentPage = position
                setupIndicator(position)
            }
        })

        binding.tvForgotPassword.setOnClickListener(this)
        binding.tvCreateAccount.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread(object : Runnable {
                    override fun run() {
                        if (currentPage == 3)
                            currentPage = -1
                        binding.viewpager.setCurrentItem(currentPage + 1, true)

                    }

                })
            }
        }, duration, duration)
    }

    fun setupIndicator(position: Int) {
        when (position) {
            0 -> {
                binding.tvDesc.setText(getString(R.string.str_walkthrough_text_1))
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            1 -> {
                binding.tvDesc.setText(getString(R.string.str_walkthrough_text_2))
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            2 -> {
                binding.tvDesc.setText(getString(R.string.str_walkthrough_text_3))
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            3 -> {
                binding.tvDesc.setText(getString(R.string.str_walkthrough_text_4))
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
                startActivity(IntroAndDecisionActivity.getStartIntent(this))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            if (isFinishing)
                timer.cancel()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}