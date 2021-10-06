package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.ForgotPasswordRepository
import com.fighterdiet.databinding.ActivityResetPasswordBinding
import com.fighterdiet.fragments.OtpDialogFragement
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.ForgotPasswordViewModel
import com.fighterdiet.viewModel.ForgotPasswordViewModelProvider

class ResetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password)
        initialise()
        setupViewModel()
        setupObserver()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {
        viewModel=ViewModelProvider(this,ForgotPasswordViewModelProvider(ForgotPasswordRepository(RetrofitBuilder.apiService)))
            .get(ForgotPasswordViewModel::class.java)
        binding.forgotpasswordViewModel=viewModel
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

                        if (apiResponse.code==200){

                            Utils.showSnackBar(binding.root, apiResponse.message)

                            val otpDialogFragement = OtpDialogFragement()
                            val userdata = Bundle()
                            userdata.putString("otp",apiResponse.data?.otp.toString())
                            userdata.putString("userid",apiResponse.data?.user_id.toString())
                            userdata.putString("email",apiResponse.data?.email.toString())
                            Toast.makeText(this,it.data.data?.otp.toString()+apiResponse.data?.user_id.toString(), Toast.LENGTH_LONG).show()
                            otpDialogFragement.arguments=userdata

                            otpDialogFragement.show(supportFragmentManager, "OtpDialogFragement")
                              print("data"+apiResponse.data?.otp+apiResponse.data?.user_id)

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
        binding.btnResetPassword.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    companion object {
        const val TAG = "ResetPassword"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ResetPasswordActivity::class.java)
        }
    }
}