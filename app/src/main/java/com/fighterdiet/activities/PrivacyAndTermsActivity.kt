package com.fighterdiet.activities

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityPrivacyAndTermsBinding

class PrivacyAndTermsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrivacyAndTermsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_and_terms)
        initialise()
    }

    private fun initialise() {
        binding.toolbar.ivCb.visibility = GONE
        binding.toolbar.back.setOnClickListener(this)


        var url: String = ""
        var title:String = ""
        var bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("URL")!!
            title = bundle.getString("PRIVACY")!!
        }

        binding.toolbar.tvTitle.setText(title)

        var webSettings = binding.wvTermsAndPrivacy.settings
        webSettings.javaScriptEnabled = true
        binding.wvTermsAndPrivacy.loadUrl(url)
        binding.wvTermsAndPrivacy.settings.domStorageEnabled = true
        binding.wvTermsAndPrivacy.settings.useWideViewPort = true
        binding.wvTermsAndPrivacy.computeScroll()
        binding.wvTermsAndPrivacy.overlayHorizontalScrollbar()
        binding.wvTermsAndPrivacy.isVerticalScrollBarEnabled = true
        binding.wvTermsAndPrivacy.isHorizontalScrollBarEnabled = true
        binding.wvTermsAndPrivacy.settings.builtInZoomControls = false

    }

    override fun onBackPressed() {
        if (binding.wvTermsAndPrivacy.canGoBack()) {
            binding.wvTermsAndPrivacy.goBack()
        } else {
            super.onBackPressed()
        }

    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.back ->{
                onBackPressed()
            }
        }
    }
}