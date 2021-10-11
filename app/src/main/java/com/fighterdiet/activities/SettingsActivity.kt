package com.fighterdiet.activities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.LogOutRepository
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.databinding.FragmentSettingBinding
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.LogOutViewModel
import com.fighterdiet.viewModel.LogOutViewModelProvider
import com.fighterdiet.viewModel.LoginViewModel
import com.fighterdiet.viewModel.LoginViewModelProvider


class SettingsActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel:LogOutViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_setting)

        initialise()
        setupViewModel()
        setupObserver()

    }

    override fun setupUI() {

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            LogOutViewModelProvider(LogOutRepository(RetrofitBuilder.apiService))
        ).get(LogOutViewModel::class.java)
        binding.logoutViewModel = viewModel
    }

    override fun setupObserver() {
        viewModel.getResources().observe(this,{
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
                            val loginIntent = Intent(this, LoginActivity::class.java)
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(loginIntent)
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

    /* override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
         return binding.root
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         initialise()
     }*/

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


   /* companion object {

        fun getInstance(context: Context): Fragment {
            val bundle = Bundle()
            val fragment = SettingFragment()
            return fragment
        }
    }*/

    override fun onClick(view: View?) {
        view?.let {
            when (view.id) {
                R.id.tv_log_out -> {
                   /* val loginIntent = Intent(this, LoginActivity::class.java)
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(loginIntent)*/
                }

                R.id.tv_about_paulin -> {
                    val loginIntent = Intent(this, IntroAndDecisionActivity::class.java)
                    loginIntent.putExtra("SETTING", "SETTING")
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
                    val reset=Intent(this,ResetPasswordActivity::class.java)
                    reset.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(reset)
                }
                R.id.tv_cancel_subscription->{

                }
                R.id.tv_privacy -> {
                    var quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                    quiz.putExtra("URL", "https://fighterdiet.com/privacy-policy/")
                    quiz.putExtra("PRIVACY", "PRIVACY POLICY")
                    startActivity(quiz)
                }
                R.id.tv_terms -> {
                    var quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                    quiz.putExtra("URL", "https://fighterdiet.com/terms-and-conditions/")
                    quiz.putExtra("PRIVACY", "TERMS AND CONDITIONS")
                    startActivity(quiz)
                }

                R.id.iv_back ->{
                    finish()
                }
            }
        }
    }
}