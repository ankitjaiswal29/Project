package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityImageZoomOutBinding
import com.fighterdiet.databinding.ActivityIntroAndDecisionBinding
import java.lang.Exception

private const val IMAGE_URI = "ImageURI"
class ImageZoomOutActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImageZoomOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_zoom_out)
        setupUI()
    }

    override fun setupUI() {
        getIntentData()
        initClickListener()
        //Old Code
//        binding.wvImage.settings.javaScriptEnabled = true
//        binding.wvImage.settings.builtInZoomControls = true
//        binding.wvImage.settings.displayZoomControls = false
//        binding.wvImage.loadDataWithBaseURL("file:///android_res/drawable/", "<img src='img_detail_page.png' />", "text/html", "utf-8", null)

    }

    private fun initClickListener() {
        binding.ivClose.setOnClickListener(this)
    }

    private fun getIntentData() {
        val intentData = intent
        intentData?.let { intent ->
            val recipeImage = intent.getStringExtra(IMAGE_URI)
            recipeImage.let {
                try {
                    Glide.with(this)
                        .load(it)
                        .placeholder(R.color.skyblue)
                        .into(binding.ivBanner)
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    override fun setupViewModel() {

    }

    override fun setupObserver() {

    }

    companion object {
        const val TAG = "ImageZoomOutActivity"

        fun getStartIntent(context: Context, imageUri:String): Intent {
            return Intent(context, ImageZoomOutActivity::class.java).putExtra(IMAGE_URI, imageUri)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.iv_close->{
                finish()
            }
        }
    }
}