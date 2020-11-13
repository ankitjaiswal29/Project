package com.fighterdiet.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.activities.FaqActivity
import com.fighterdiet.activities.LoginActivity
import com.fighterdiet.activities.QuizActivity
import com.fighterdiet.activities.ResetPasswordActivity
import com.fighterdiet.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment(), View.OnClickListener {
    lateinit var binding:FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        binding.tvLogOut.setOnClickListener(this)
        binding.tvEditQuestion.setOnClickListener(this)
        binding.tvFaq.setOnClickListener(this)
        binding.tvClear.setOnClickListener(this)
        binding.tvEmail.setOnClickListener(this)
        binding.tvTerms.setOnClickListener(this)
        binding.tvPrivacy.setOnClickListener(this)
    }


    companion object {

        fun getInstance(context: Context): Fragment {
            val bundle = Bundle()
            val fragment = SettingFragment()
            return fragment
        }
    }

    override fun onClick(view: View?) {
        view?.let {
            when (view.id) {
                R.id.tv_log_out -> {
                    val loginIntent = Intent(activity, LoginActivity::class.java)
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(loginIntent)
                }
                R.id.tv_edit_question ->{
                    val quiz = Intent(activity, QuizActivity::class.java)
                    quiz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(quiz)
                }
                R.id.tv_faq ->{
                    val faq = Intent(activity, FaqActivity::class.java)
                    faq.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(faq)
                }
            }
        }
    }

}