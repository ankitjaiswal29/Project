package com.fighterdiet.activities
import android.annotation.SuppressLint
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
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.LoginViewModel
import com.fighterdiet.viewModel.LoginViewModelProvider
import com.fighterdiet.viewModel.RegisterViewModel
import com.fighterdiet.viewModel.RegisterViewModelProvider
import kotlin.math.abs
import android.content.pm.PackageManager

import android.content.SharedPreferences

import android.content.pm.PackageInfo

class LoginActivity : BaseActivity(), View.OnClickListener {
    private val MIN_DIST = 150
    private var x2: Float = 0f
    private var x1: Float = 0f

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
        clearDataIfVersionChange()

        if(PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
            val intent =DashboardActivity.getStartIntent(this)
            startActivity(intent)
            finish()
        }
        initialise()
        setupUI()
        setupViewModel()
        setupObserver()
    }

    fun clearDataIfVersionChange() {
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val mCurrentVersion = pInfo.versionCode
            val mSharedPreferences = getSharedPreferences("app_name", MODE_PRIVATE)
            val mEditor = mSharedPreferences.edit()
            val last_version = mSharedPreferences.getInt("last_version", -1)
            if (last_version != mCurrentVersion) {
                PrefManager.clearPref()
            }
            mEditor.putInt("last_version", mCurrentVersion)
            mEditor.apply()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun setupUI() {
        handleDeepLinking()
    }

    private fun handleDeepLinking() {
        val intent = intent
        if (intent != null && intent.data != null) {
            val data = intent.data
            val splittedData = data.toString().split("&")
            val recipeId = splittedData[0].split("=")[1]
            val recipeImage = splittedData[1].split("=")[1]
            val recipeName = splittedData[2].split("=")[1].replace("_" , " ")

            val recipeDetailsActivity = RecipeDetailsActivity.getStartIntent(this)
            if(recipeId.isNotBlank()){
                recipeDetailsActivity.putExtra(Constants.RECIPE_ID, recipeId)
                recipeDetailsActivity.putExtra(Constants.RECIPE_IMAGE, recipeImage)
                recipeDetailsActivity.putExtra(Constants.RECIPE_NAME, recipeName)
                Log.e(LoginActivity.TAG, ">>>>> Deep Link URl ::" + data.toString())
                startActivity(recipeDetailsActivity)
                finish()
            }
        }
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
                            //System.out.println("token"+apiResponse.data?.token.toString())
                            PrefManager.putString(PrefManager.KEY_USER_ID,apiResponse.data?.user_id.toString())
                            PrefManager.putString(PrefManager.KEY_AUTH_TOKEN, apiResponse.data?.token?:"")
                            PrefManager.putBoolean(PrefManager.IS_LOGGED_IN, true)
                            apiResponse.data?.is_subscribed?.let { isSubscribed ->
                                PrefManager.putBoolean(PrefManager.IS_SUBSCRIBED, isSubscribed=="1")
                            }
                            Constants.RecipeFilter.totalFilterCount = 0
                            Constants.RecipeFilter.selectedMealFilter.clear()
                            Constants.RecipeFilter.selectedDietaryFilter.clear()
                            Constants.RecipeFilter.selectedVolumeFilter.clear()
                            Constants.DashboardDetails.isApiRequestNeeded = true
                            Constants.RecipeFilter.isFilterCleared = false

                            Constants.RecipeFilter.isFilterCleared = true
                            Constants.RecipeFilter.isFilterApplied = false

                            startActivity(DashboardActivity.getStartIntent(this))
                            finish()
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

    @SuppressLint("ClickableViewAccessibility")
    private fun initialise() {
        viewFlipper = findViewById<ViewFlipper>(R.id.viewflip)
        for (i in imageList.indices) {
            // This will create dynamic image views and add them to the ViewFlipper.
            setFlipperImage(imageList.get(i))
        }

//        viewFlipper!!.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    x1 = event.x
//                }
//                MotionEvent.ACTION_UP -> {
//                    x2 = event.x
//                    val deltaX = x2 - x1
//                    if(abs(deltaX) > MIN_DIST){
//                        // Right to Left swipe
//                        if(x1 > x2){
//                            var position = viewFlipper!!.displayedChild
//                            if(position==imageList.size-1){
//                                position = 0
//                            }else
//                                position++
//
//                            viewFlipper!!.displayedChild = imageList[position]
//                            setupIndicator(imageList[position])
//                        }
//                        // Left to Right Swipe
//                        else{
//                            var position = viewFlipper!!.displayedChild
//                            if(position==0){
//                                position = imageList.size-1
//                            }else
//                                position--
//
//                            viewFlipper!!.displayedChild = imageList[position]
//                            setupIndicator(imageList[position])
//                        }
//                    }
//                }
//            }
//            true
//        }


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
//        binding.btnLogin.setOnClickListener(this)
        binding.tvSkip.setOnClickListener(this)
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
                PrefManager.putBoolean(PrefManager.IS_LOGGED_IN, false)
                startActivity(DashboardActivity.getStartIntent(this))
                finish()
            }
//            R.id.btnLogin -> {
//                if(isValidated())
//                    startActivity(IntroAndDecisionActivity.getStartIntent(this))
//            }
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