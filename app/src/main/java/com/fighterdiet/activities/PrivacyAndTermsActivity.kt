package com.fighterdiet.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityPrivacyAndTermsBinding

class PrivacyAndTermsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrivacyAndTermsBinding

    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if(newProgress > 75)
                binding.pbPrivacyPolicy.visibility = View.GONE
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_and_terms)
        initialise()
    }

    override fun onStart() {
        super.onStart()
        binding.pbPrivacyPolicy.visibility = View.VISIBLE
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

        binding.toolbar.tvTitle.text = title
        val webSettings = binding.wvTermsAndPrivacy.settings
        binding.wvTermsAndPrivacy.loadUrl(url)
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = false
        webSettings.javaScriptEnabled = true
        binding.wvTermsAndPrivacy.computeScroll()
        binding.wvTermsAndPrivacy.overlayHorizontalScrollbar()
        binding.wvTermsAndPrivacy.isVerticalScrollBarEnabled = true
        binding.wvTermsAndPrivacy.isHorizontalScrollBarEnabled = true
        binding.wvTermsAndPrivacy.webChromeClient = webChromeClient
    }

    override fun onBackPressed() {
        if (binding.wvTermsAndPrivacy.canGoBack()) {
            binding.wvTermsAndPrivacy.goBack()
        } else {
            super.onBackPressed()
        }

    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

    }

    override fun setupObserver() {

    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.back ->{
                onBackPressed()
            }
        }
    }
}