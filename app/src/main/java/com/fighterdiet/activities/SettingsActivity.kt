package com.fighterdiet.activities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.SettingsRepository
import com.fighterdiet.databinding.FragmentSettingBinding
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.SettingsViewModel
import com.fighterdiet.viewModel.LogOutViewModelProvider


class SettingsActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel:SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_setting)
        initialise()
        setupViewModel()
        setupObserver()
        setupUI()
    }

    override fun setupUI() {
        if(!PrefManager.getBoolean(PrefManager.IS_LOGGED_IN)){
            val inactiveColor = ContextCompat.getColor(this, R.color.gray)
            binding.tvLogOut.isEnabled = false
            binding.tvLogOut.setTextColor(inactiveColor)
            binding.tvChangePassword.isEnabled = false
            binding.tvChangePassword.setTextColor(inactiveColor)
            binding.tvClear.isEnabled = false
            binding.tvClear.setTextColor(inactiveColor)
            binding.tvEmail.isEnabled = false
            binding.tvEmail.setTextColor(inactiveColor)
            binding.tvCancelSubscription.isEnabled = false
            binding.tvCancelSubscription.setTextColor(inactiveColor)
        }

        when(Constants.DashboardDetails.recipiesModel?.is_subscribed){
            "0", "expired" -> {
                binding.tvCancelSubscription.visibility = View.GONE
            }
            else -> {
                binding.tvCancelSubscription.visibility = View.VISIBLE
            }
        }

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            LogOutViewModelProvider(SettingsRepository(RetrofitBuilder.apiService))
        ).get(SettingsViewModel::class.java)
        binding.logoutViewModel = viewModel
    }

    override fun setupObserver() {
        viewModel.getResourcesLogout().observe(this,{
            when(it.status){
                Status.LOADING->{
                   // ProgressDialog.showProgressDialog(this)
                }
                Status.ERROR -> {
                    //ProgressDialog.hideProgressDialog()
                    it.message?.let {
                        Utils.showSnackBar(binding.root, it)
                    }

                }
                Status.SUCCESS -> {
                    ProgressDialog.hideProgressDialog()
                    val apiResponse = it.data!!

                    if (apiResponse.status) {
                        if (apiResponse.code==200){
                            PrefManager.clearPref()
                            PrefManager.putBoolean(PrefManager.IS_LOGGED_IN, false)
                            PrefManager.putString(PrefManager.KEY_AUTH_TOKEN, "")
                            val loginIntent = Intent(this, LoginActivity::class.java)
                            startActivity(loginIntent)
                            finishAffinity()
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
        binding.tvLogOut.setOnClickListener(this)
        binding.tvAboutPaulin.setOnClickListener(this)
        binding.tvFaq.setOnClickListener(this)
        binding.tvClear.setOnClickListener(this)
        binding.tvEmail.setOnClickListener(this)
        binding.tvTerms.setOnClickListener(this)
        binding.tvPrivacy.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
        binding.tvChangePassword.setOnClickListener(this)
        binding.tvCancelSubscription.setOnClickListener(this)
    }

    companion object {
        const val TAG = "SettingActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        view?.let {
            when (view.id) {
                R.id.tv_log_out -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Do you really want to logout?")
                    builder.setPositiveButton("Logout") { dialog, which ->
                        viewModel.getLogOutApi()
                        dialog.dismiss()
                    }

                    builder.setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }

                R.id.tv_about_paulin -> {
                    val loginIntent = Intent(this, AboutPauliActivity::class.java)
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(loginIntent)
                }

                R.id.tv_faq -> {
                    val faq = Intent(this, FaqActivity::class.java)
                    faq.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(faq)
                }
                R.id.tv_email -> {
                    try {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("mailto:" + "customerservice@fighterdiet.com")
                        )
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                    }
                }
                R.id.tv_change_password->{
                    val reset=Intent(this,ChangePasswordActivity::class.java)
                    reset.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(reset)
                }
                R.id.tv_cancel_subscription->{
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/account/subscriptions")
                    )
                    startActivity(browserIntent)
                }
                R.id.tv_privacy -> {
                    val quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                    quiz.putExtra("URL", "https://fighterdiet.com/privacy-policy/")
                    quiz.putExtra("PRIVACY", "PRIVACY POLICY")
                    startActivity(quiz)
                }
                R.id.tv_terms -> {
                    val quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                    quiz.putExtra("URL", "https://fighterdiet.com/terms-and-conditions/")
                    quiz.putExtra("PRIVACY", "TERMS AND CONDITIONS")
                    startActivity(quiz)
                }

                R.id.iv_back ->{
                    finish()
                }
                R.id.tv_clear ->{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Do you really want to clear cache?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        viewModel.getLogOutApi()
                        dialog.dismiss()
                    }

                    builder.setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
                else -> {}
            }
        }
    }
}