package com.fighterdiet.fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.activities.*
import com.fighterdiet.databinding.FragmentSettingBinding
import com.fighterdiet.utils.Constants


class SettingFragment : BaseActivity(), View.OnClickListener {
    lateinit var binding: FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_setting)

        initialise()

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
        binding.tvEditQuestion.setOnClickListener(this)
        binding.tvFaq.setOnClickListener(this)
        binding.tvClear.setOnClickListener(this)
        binding.tvEmail.setOnClickListener(this)
        binding.tvTerms.setOnClickListener(this)
        binding.tvPrivacy.setOnClickListener(this)
        binding.ivBack.setOnClickListener(this)
    }

    companion object {
        const val TAG = "SettingActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SettingFragment::class.java)
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
                    val loginIntent = Intent(this, LoginActivity::class.java)
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(loginIntent)
                }
                R.id.tv_edit_question -> {
                    val quiz = Intent(this, QuizActivity::class.java)
                    quiz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(quiz)
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