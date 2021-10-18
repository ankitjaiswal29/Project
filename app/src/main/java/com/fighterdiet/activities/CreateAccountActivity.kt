package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.RegisterRepository
import com.fighterdiet.databinding.ActivityCreateAccountBinding
import com.fighterdiet.utils.*
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
        setupUI()
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

        viewModel.getResources().observe(this, { it ->
            when (it.status) {
                Status.LOADING -> {
                    ProgressDialog.showProgressDialog(this)
                }
                Status.ERROR -> {
                    ProgressDialog.hideProgressDialog()
                    it.message?.let { msg ->
                        Utils.showSnackBar(binding.root, msg)
                    }
                }
                Status.SUCCESS -> {
                    ProgressDialog.hideProgressDialog()
                    val apiResponse = it.data!!

                    if (apiResponse.status) {

                        if (apiResponse.code==200){
                            PrefManager.putString(PrefManager.KEY_USER_ID,apiResponse.data?.user_id.toString())
                            PrefManager.putString(PrefManager.KEY_AUTH_TOKEN, apiResponse.data?.token?:"")
                            PrefManager.putBoolean(PrefManager.IS_LOGGED_IN, true)
                            startActivity(IntroAndDecisionActivity.getStartIntent(this))
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
                    if(it.data?.data?.key == "0"){
                        binding.ivVerifiedUsername.visibility = View.VISIBLE
                        binding.ivVerifiedUsername.setImageResource(R.drawable.ic_green_check3x)
                    }else{
                        binding.ivVerifiedUsername.visibility = View.GONE
                    }

                }
            }
        })

        viewModel.getErrorMsg().observe(this, Observer {
            Utils.showSnackBar(binding.root, it)
        })

    }

    private fun initialise() {

        binding.etUserId.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!=null && p0.isNotEmpty()) {
                    if(p0.length in 6..12)
                        viewModel.checkUserNameApi(p0.toString())
                    else
                        Utils.showSnackBar(binding.root, "User name should be between 6 to 12 characters")
                }else{
                    binding.ivVerifiedUsername.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

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