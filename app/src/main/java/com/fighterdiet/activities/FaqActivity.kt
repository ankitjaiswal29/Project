package com.fighterdiet.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.FaqAdapter
import com.fighterdiet.databinding.ActivityFaqBinding
import com.fighterdiet.model.FaqModel
import java.util.*

class FaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val faqList: MutableList<FaqModel> = ArrayList<FaqModel>()
        faqList.add(
            FaqModel(
                getString(R.string.faq_one)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_two)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_three)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_four)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_five)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_six)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_seven)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_eight)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_nine)
            )
        )
        faqList.add(
            FaqModel(
                getString(R.string.faq_ten)
            )
        )


        val productInfoAdapter =
            FaqAdapter(this@FaqActivity, faqList, binding.mainView)
        binding.rvFaq.setLayoutManager(LinearLayoutManager(this))
        binding.rvFaq.setAdapter(productInfoAdapter)
    }
}