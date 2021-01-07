package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityImageZoomOutBinding
import com.fighterdiet.databinding.ActivityIntroAndDecisionBinding

class ImageZoomOutActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImageZoomOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_zoom_out)

        binding.ivClose.setOnClickListener(this)

        binding.wvImage.getSettings().setJavaScriptEnabled(true)
        binding.wvImage.getSettings().setBuiltInZoomControls(true)
        binding.wvImage.getSettings().setDisplayZoomControls(false)
        binding.wvImage.loadDataWithBaseURL("file:///android_res/drawable/", "<img src='img_detail_page.png' />", "text/html", "utf-8", null)
    }

    companion object {
        const val TAG = "ImageZoomOutActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ImageZoomOutActivity::class.java)
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