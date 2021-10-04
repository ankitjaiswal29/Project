package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.data.repository.RegisterRepository
import com.fighterdiet.databinding.ActivityLoginBinding
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.LoginViewModel
import com.fighterdiet.viewModel.LoginViewModelProvider
import com.fighterdiet.viewModel.RegisterViewModel
import com.fighterdiet.viewModel.RegisterViewModelProvider


class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel:LoginViewModel
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
        setupViewModel()
        setupObserver()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

        viewModel = ViewModelProvider(
            this,
            LoginViewModelProvider(LoginRepository(RetrofitBuilder.apiService))
        ).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel


    }

    override fun setupObserver() {
        viewModel.getResources().observe(this,{
            when(it.status){
                Status.LOADING->{
                    ProgressDialog.showProgressDialog(this)
                }
                Status.ERROR -> {
                    ProgressDialog.hideProgressDialog()
                    it.message?.let {
                        Utils.showSnackBar(binding.root, it)
                    }

                }
                Status.SUCCESS -> {
                    ProgressDialog.hideProgressDialog()
                    val apiResponse = it.data!!

                    if (apiResponse.status) {

                        apiResponse.data
                        if (apiResponse.code==200){
                            Toast.makeText(this,
                                apiResponse.data?.first_name+apiResponse.data?.first_name, Toast.LENGTH_LONG).show()

                          //  startActivity(IntroAndDecisionActivity.getStartIntent(this))
                           /* val loginIntent = Intent(this, LoginActivity::class.java)
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(loginIntent)*/
                        }else{
                            Utils.showSnackBar(binding.root, apiResponse.message)
                        }

                    } else {
                        Utils.showSnackBar(binding.root, apiResponse.message)
                    }

                }
            }

        })
        viewModel.getErrorMsg().observe(this, Observer {
            Utils.showSnackBar(binding.root, it)
        })

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
        binding.tvSkip.setOnClickListener(this)

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

        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_forgot_password -> {
                startActivity(ResetPasswordActivity.getStartIntent(this))
            }
            R.id.tv_create_account -> {
                startActivity(CreateAccountActivity.getStartIntent(this))
            }
            R.id.tv_skip->{
                startActivity(DashboardActivity.getStartIntent(this))
                finish()
            }
           /* R.id.btnLogin -> {
                startActivity(IntroAndDecisionActivity.getStartIntent(this))
            }*/
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