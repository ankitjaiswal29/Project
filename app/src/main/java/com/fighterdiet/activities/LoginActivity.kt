package com.fighterdiet.activities

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityLoginBinding


class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
   // private lateinit var adapter: ViewPagerAdapter
   // private lateinit var timer: Timer
   // private var duration: Long = 2 * 1000 // Seconds
   // private var currentPage: Int = 0
   // private var isWalkthroughHold: Boolean = false
    var viewFlipper: ViewFlipper? = null
    var imageList = intArrayOf(
        R.mipmap.walkthrough_1, R.mipmap.walkthrough_2,
        R.mipmap.walkthrough_3, R.mipmap.walkthrough_4
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* getWindow().setFlags(
             WindowManager.LayoutParams.FLAG_SECURE,
             WindowManager.LayoutParams.FLAG_SECURE
         )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initialise()
    }

    private fun initialise() {
        viewFlipper = findViewById<ViewFlipper>(R.id.viewflip)
        for (i in 0 until imageList.size) {
            // This will create dynamic image views and add them to the ViewFlipper.
            setFlipperImage(imageList.get(i))
        }



        viewFlipper!!.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.e("Dowwn", "Stop")
                    if (viewFlipper!!.isFlipping)
                        viewFlipper!!.stopFlipping()
                }
                MotionEvent.ACTION_UP -> {
                    viewFlipper!!.startFlipping()
                    Log.e("UP", "start")
                }
            }
            true
        })


       // Log.e("position", viewFlipper!!.indexOfChild(viewFlipper!!.currentView).toString())
       // Log.e("position1", viewFlipper!!.displayedChild.toString())


        /*adapter = ViewPagerAdapter(this)

        adapter.addFragment(WalkThroughFragment.getInstance(0))
        adapter.addFragment(WalkThroughFragment.getInstance(1))
        adapter.addFragment(WalkThroughFragment.getInstance(2))
        adapter.addFragment(WalkThroughFragment.getInstance(3))

        binding.viewpager.adapter = adapter
        binding.viewpager.offscreenPageLimit = 4
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
                setupIndicator(position)
            }
        })*/

        binding.tvForgotPassword.setOnClickListener(this)
        binding.tvCreateAccount.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

        /*timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread(object : Runnable {
                    override fun run() {
                        if (isWalkthroughHold)
                            return
                        if (currentPage == 3)
                            currentPage = -1
                        // binding.viewpager.setCurrentItem(currentPage + 1, true)

                    }

                })
            }
        }, duration, duration)*/
    }


    private fun setFlipperImage(res: Int) {
        Log.i("Set Filpper Called", res.toString() + "")
        val image = ImageView(this)
        image.setBackgroundResource(res)
        viewFlipper!!.addView(image)
        viewFlipper!!.flipInterval = 2000
        viewFlipper!!.isAutoStart = true

        viewFlipper!!.setInAnimation(this, R.anim.slide_in_right)
        viewFlipper!!.setOutAnimation(this, R.anim.slide_in_left)

        viewFlipper!!.inAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                Log.e("positionCurrent",((viewFlipper!!.displayedChild + 1).toString() + "/" + viewFlipper!!.childCount))
                setupIndicator(viewFlipper!!.displayedChild + 1)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        binding.tvDesc.text = getString(R.string.str_walkthrough_text_1)

    }

    fun setupIndicator(position: Int) {
        when (position) {
            1 -> {
                binding.tvDesc.text = getString(R.string.str_walkthrough_text_1)
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            2 -> {
                binding.tvDesc.text = getString(R.string.str_walkthrough_text_2)
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            3 -> {
                binding.tvDesc.text = getString(R.string.str_walkthrough_text_3)
                binding.layoutIndicator.vFirst.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vSecond.setBackgroundResource(R.drawable.shape_indicator_unselected)
                binding.layoutIndicator.vThird.setBackgroundResource(R.drawable.shape_indicator_selected)
                binding.layoutIndicator.vFourth.setBackgroundResource(R.drawable.shape_indicator_unselected)
            }
            4 -> {
                binding.tvDesc.text = getString(R.string.str_walkthrough_text_4)
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

    /*fun setHold(value: Boolean) {
        this.isWalkthroughHold = value
    }*/

    /*override fun onStop() {
        super.onStop()
        try {
            if (isFinishing)
                timer.cancel()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }*/
}