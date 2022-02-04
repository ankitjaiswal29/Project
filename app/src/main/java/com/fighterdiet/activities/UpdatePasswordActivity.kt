package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.ChangePasswordRepository
import com.fighterdiet.data.repository.UpdatePasswordRepository
import com.fighterdiet.databinding.ActivityChangePasswordBinding
import com.fighterdiet.databinding.ActivityUpdatePasswordBinding
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.ChangePassViewModel
import com.fighterdiet.viewModel.ChangePassViewModelProvider
import com.fighterdiet.viewModel.UpdatePassViewModel
import com.fighterdiet.viewModel.UpdatePassViewModelProvider

class UpdatePasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityUpdatePasswordBinding
    private lateinit var viewModel: UpdatePassViewModel

    companion object {
        const val TAG = "UdpatePasswordActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, UpdatePasswordActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_password)
        setupViewModel()
        setupObserver()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UpdatePassViewModelProvider(UpdatePasswordRepository(RetrofitBuilder.apiService))
        ).get(UpdatePassViewModel::class.java)

        binding.viewModel = viewModel
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
                            Utils.showToast(this, "Password is changed successfully")
                          //  Handler().postDelayed(Runnable { this@YourActivity.finish() }, 2000)
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
}