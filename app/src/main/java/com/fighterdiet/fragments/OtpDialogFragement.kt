package com.fighterdiet.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.fighterdiet.R
import com.fighterdiet.databinding.FragmentCommentBinding
import com.fighterdiet.databinding.OtpDialogFragmentBinding
import com.fighterdiet.utils.OtpTextWatcher

class OtpDialogFragement: DialogFragment() {
    private lateinit var binding: OtpDialogFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.bg_round_corner_top_let_right);
        getDialog()?.setCanceledOnTouchOutside(false);
        val bundle = arguments
        var otp = bundle?.getString("otp","")
        var userid = bundle?.getString("userid","")

        Toast.makeText(context,otp+userid,Toast.LENGTH_LONG).show()

        val dialogView: View =
            layoutInflater.inflate(R.layout.otp_dialog_fragment, container, false)
        binding = DataBindingUtil.bind(dialogView)!!

        return binding.root
        //return inflater.inflate(R.layout.otp_dialog_fragment, container, false)
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