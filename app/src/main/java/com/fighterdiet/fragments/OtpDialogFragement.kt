package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.activities.IntroAndDecisionActivity
import com.fighterdiet.activities.UpdatePasswordActivity
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.requestModel.VerifyOtpRequestModel
import com.fighterdiet.data.repository.RegisterRepository
import com.fighterdiet.data.repository.ResendOtpRepository
import com.fighterdiet.data.repository.VerifyOtpRepository
import com.fighterdiet.databinding.FragmentCommentBinding
import com.fighterdiet.databinding.OtpDialogFragmentBinding
import com.fighterdiet.utils.*
import com.fighterdiet.viewModel.ResendOtpViewModel
import com.fighterdiet.viewModel.ResendOtpViewModelProvider
import com.fighterdiet.viewModel.VerifyOtpViewModel
import com.fighterdiet.viewModel.VerifyOtpViewModelProvider

class OtpDialogFragement: DialogFragment() {
    private lateinit var binding: OtpDialogFragmentBinding
    private lateinit var viewModel: VerifyOtpViewModel
    private lateinit var viewModel2: ResendOtpViewModel
    public  var userId : String ? =null
    public  var email : String ? =null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.bg_round_corner_top_let_right);
        getDialog()?.setCanceledOnTouchOutside(false);
        val bundle = arguments
        var otp = bundle?.getString("otp","")
        userId = bundle?.getString("userid","")
        email=bundle?.getString("email","")

        Toast.makeText(context,otp+userId,Toast.LENGTH_LONG).show()

        val dialogView: View =
            layoutInflater.inflate(R.layout.otp_dialog_fragment, container, false)
        binding = DataBindingUtil.bind(dialogView)!!
        setupViewModel()
        setupObserver()
        setupViewModelResendOtp()
        setupObserverResendOtp()
        setDataBindingValue()

        return binding.root
        //return inflater.inflate(R.layout.otp_dialog_fragment, container, false)
    }

    private fun setDataBindingValue(){
        binding.userId= userId
        binding.emailid= email

    }
   private fun setupViewModel() {
        viewModel=ViewModelProvider(this,VerifyOtpViewModelProvider(VerifyOtpRepository(RetrofitBuilder.apiService))).get(VerifyOtpViewModel::class.java)
        binding.verifyOtpViewModel = viewModel
    }

    private fun setupViewModelResendOtp(){
        viewModel2=ViewModelProvider(this,ResendOtpViewModelProvider(ResendOtpRepository(RetrofitBuilder.apiService))).get(ResendOtpViewModel::class.java)
        binding.resendViewModel = viewModel2
    }
    private fun setupObserverResendOtp(){
        viewModel2.getResources().observe(this, {
            when (it.status) {
                Status.LOADING -> {
                    ProgressDialog.showProgressDialog(context)
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
                            Toast.makeText(context,apiResponse.data?.otp.toString()+apiResponse.data?.user_id.toString(), Toast.LENGTH_LONG).show()

                            //    startActivity(IntroAndDecisionActivity.getStartIntent(context!!.applicationContext))

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
    private fun setupObserver() {

        viewModel.getResources().observe(this, {
            when (it.status) {
                Status.LOADING -> {
                    ProgressDialog.showProgressDialog(context)
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
                            PrefManager.putString(PrefManager.KEY_USER_ID,apiResponse.data?.user_id.toString())
                            PrefManager.putString(PrefManager.KEY_AUTH_TOKEN, apiResponse.data?.token?:"")
                            PrefManager.putBoolean(PrefManager.IS_LOGGED_IN, true)
                            apiResponse.data?.is_subscribed?.let { isSubscribed ->
                                PrefManager.putBoolean(PrefManager.IS_SUBSCRIBED, isSubscribed=="1")
                            }
                           startActivity(UpdatePasswordActivity.getStartIntent(requireContext()))

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


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etOtp1.addTextChangedListener(OtpTextWatcher(binding.etOtp2, binding.etOtp1))
        binding.etOtp2.addTextChangedListener(OtpTextWatcher(binding.etOtp3, binding.etOtp1))
        binding.etOtp3.addTextChangedListener(OtpTextWatcher(binding.etOtp4, binding.etOtp2))
        binding.etOtp4.addTextChangedListener(OtpTextWatcher(binding.etOtp4, binding.etOtp3))

    }

}