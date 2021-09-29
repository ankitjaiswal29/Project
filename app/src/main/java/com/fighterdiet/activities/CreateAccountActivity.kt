package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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

                        if (apiResponse.status_code==200){
                            startActivity(Intent(this,LoginActivity::class.java))
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
        binding.btnCreateAccount.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(IntroAndDecisionActivity.getStartIntent(this))
        })
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CreateAccountActivity::class.java)
        }
    }
}