package com.fighterdiet.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.databinding.ActivityLoginBinding
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.LoginViewModel
import com.fighterdiet.viewModel.LoginViewModelProvider

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    var viewFlipper: ViewFlipper? = null
    var imageList = intArrayOf(
        R.mipmap.walkthrough_1, R.mipmap.walkthrough_2,
        R.mipmap.walkthrough_3, R.mipmap.walkthrough_4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        clearDataIfVersionChange()
//        if(PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
//            // Toast.makeText(this,"dfdf",Toast.LENGTH_LONG).show()
//            val appLinkAction: String? = intent?.action
//            val appLinkData: Uri? = intent?.data
////            showDeepLinkOffer(appLinkAction, appLinkData)
//
//            val intent=Intent(this,DashboardActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        setupUI()
        initialise()
        setupViewModel()
        setupObserver()
    }

    private fun clearDataIfVersionChange() {
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
        if (PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)) {
            val intent = intent
            val dashIntent = Intent(this, DashboardActivity::class.java)
            if (intent != null && intent.data != null) {
                val data = intent.data
                val splittedData = data.toString().split("&")
                val recipeId = try {
                    splittedData[0].split("=")[1]
                } catch (e: Exception) {
                    ""
                }
                val recipeImage = try {
                    splittedData[1].split("=")[1]
                } catch (e: Exception) {
                    ""
                }
                val recipeName = try {
                    splittedData[2].split("=")[1].replace("_", " ")
                } catch (e: Exception) {
                    ""
                }

                if (recipeId.isNotBlank()) {
                    dashIntent.putExtra(Constants.RECIPE_ID, recipeId)
                    dashIntent.putExtra(Constants.RECIPE_IMAGE, recipeImage)
                    dashIntent.putExtra(Constants.RECIPE_NAME, recipeName)

                    Log.e(LoginActivity.TAG, ">>>>> Deep Link URl ::" + data.toString())
                    startActivity(dashIntent)
                    finish()
                }

            } else {
                startActivity(dashIntent)
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

        viewModel.getResources().observe(this) {
            when (it.status) {
                Status.LOADING -> {
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
                        if (apiResponse.code == 200) {
                            //System.out.println("token"+apiResponse.data?.token.toString())
                            PrefManager.putString(
                                PrefManager.KEY_USER_ID,
                                apiResponse.data?.user_id.toString()
                            )
                            PrefManager.putString(
                                PrefManager.KEY_AUTH_TOKEN,
                                apiResponse.data?.token ?: ""
                            )
                            PrefManager.putBoolean(PrefManager.IS_LOGGED_IN, true)
                            apiResponse.data?.is_subscribed?.let { isSubscribed ->
                                PrefManager.putBoolean(
                                    PrefManager.IS_SUBSCRIBED,
                                    isSubscribed == "1"
                                )
                            }
                            clearRecipeLocalData()

                            val intent = Intent(this, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Utils.showSnackBar(binding.root, apiResponse.message)
                        }
                    } else {
                        Utils.showSnackBar(binding.root, apiResponse.message)
                    }

                }
            }

        }
        viewModel.getErrorMsg().observe(this) {
            Utils.showSnackBar(binding.root, it)
        }

    }

    private fun clearRecipeLocalData() {
        Constants.RecipeFilter.totalFilterCount = 0
        Constants.RecipeFilter.selectedMealFilter.clear()
        Constants.RecipeFilter.selectedDietaryFilter.clear()
        Constants.RecipeFilter.selectedVolumeFilter.clear()
        Constants.DashboardDetails.isApiRequestNeeded = true
        Constants.RecipeFilter.isFilterCleared = true
        Constants.RecipeFilter.isFilterApplied = false
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialise() {
        viewFlipper = findViewById<ViewFlipper>(R.id.viewflip)
        for (i in imageList.indices) {
            // This will create dynamic image views and add them to the ViewFlipper.
            setFlipperImage(imageList.get(i))
        }
        binding.tvForgotPassword.setOnClickListener(this)
        binding.tvCreateAccount.setOnClickListener(this)
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
                Log.e(
                    "positionCurrent",
                    ((viewFlipper!!.displayedChild + 1).toString() + "/" + viewFlipper!!.childCount)
                )
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
            R.id.tv_skip -> {
                PrefManager.putBoolean(PrefManager.IS_LOGGED_IN, false)
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        val appLinkAction: String? = intent?.action
//        val appLinkData: Uri? = intent?.data
//        showDeepLinkOffer(appLinkAction,appLinkData)
//    }
//    private fun showDeepLinkOffer(appLinkAction: String?, appLinkData: Uri?) {
//        // 1
//        if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
//            // 2
//                Toast.makeText(this,"through link",Toast.LENGTH_LONG).show()
//            val key = appLinkData.getQueryParameter("key")
//            if (key.isNullOrBlank().not()) {
//                val image = appLinkData.getQueryParameter("image")
//                val title = appLinkData.getQueryParameter("navigationTitle")
//                val intent=Intent(this,DashboardActivity::class.java)
//                intent.putExtra("DeepLink", "true");
//                /*intent.putExtra(Constants.RECIPE_ID, )
//                    intent.putExtra(Constants.RECIPE_IMAGE, )
//                    intent.putExtra(Constants.RECIPE_NAME, )*/
//                startActivity(intent)
//                finish()
//
//            } else {
//
//              //  Toast.makeText(this,"not through link",Toast.LENGTH_LONG).show()
//              //  activityPromoBinding.discountGroup.visibility = View.GONE
//            }
//        }else{
//
//                       Toast.makeText(this,"not through link",Toast.LENGTH_LONG).show()
//
//            val intent=Intent(this,DashboardActivity::class.java)
//                startActivity(intent)
//                finish()
//
//        }
//    }
}