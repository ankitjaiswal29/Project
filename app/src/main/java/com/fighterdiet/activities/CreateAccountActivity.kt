package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.RegisterRepository
import com.fighterdiet.databinding.ActivityCreateAccountBinding
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.utils.makeLinks
import com.fighterdiet.viewModel.RegisterViewModel
import com.fighterdiet.viewModel.RegisterViewModelProvider

class CreateAccountActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account)
        initialise()
        setupViewModel()
        setupObserver()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            RegisterViewModelProvider(RegisterRepository(RetrofitBuilder.apiService))
        ).get(RegisterViewModel::class.java)
        binding.registerViewModel = viewModel
    }

    override fun setupObserver() {

        viewModel.getResources().observe(this, {
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

                        if (apiResponse.code==200){
                            startActivity(IntroAndDecisionActivity.getStartIntent(this))

                        }else{
                            Utils.showSnackBar(binding.root, apiResponse.message)
                        }

                    } else {
                        Utils.showSnackBar(binding.root, apiResponse.message)
                    }

                }
            }
        })

        viewModel.getResourcesCheckUser().observe(this, {
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
//                    val apiResponse = it.data!!
//
//                    if (apiResponse.status) {
//
//                        if (apiResponse.code==200){
//                            startActivity(IntroAndDecisionActivity.getStartIntent(this))
//
//                        }else{
//                            Utils.showSnackBar(binding.root, apiResponse.message)
//                        }
//
//                    } else {
//                        Utils.showSnackBar(binding.root, apiResponse.message)
//                    }

                }
            }
        })

        viewModel.getErrorMsg().observe(this, Observer {
            Utils.showSnackBar(binding.root, it)
        })

    }

    private fun initialise() {
        binding.etUserId.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                if(binding.etUserId.text.toString().isNotEmpty())
                    viewModel.checkUserNameApi(binding.etUserId.text.toString())
            }
        }

        binding.tvLogin.setOnClickListener(View.OnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(loginIntent)
        })
        binding.tvPrivacypolicyTermcondition.makeLinks(
            Pair("Terms & Conditions",View.OnClickListener {

                var quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                quiz.putExtra("URL", "https://fighterdiet.com/terms-and-conditions/")
                quiz.putExtra("PRIVACY", "TERMS AND CONDITIONS")
                startActivity(quiz)

            }), Pair("Privacy Policy",View.OnClickListener {

                var quiz = Intent(this, PrivacyAndTermsActivity::class.java)
                quiz.putExtra("URL", "https://fighterdiet.com/privacy-policy/")
                quiz.putExtra("PRIVACY", "PRIVACY POLICY")
                startActivity(quiz)
            })
        )
       /* binding.btnCreateAccount.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(IntroAndDecisionActivity.getStartIntent(this))
        })*/
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CreateAccountActivity::class.java)
        }
    }
}