package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.AboutPaulinNordinRepository
import com.fighterdiet.databinding.ActivityAboutPauliBinding
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.AboutPaulinNordinViewModel
import com.fighterdiet.viewModel.AboutPaulinNordinViewModelProvider
import com.potyvideo.library.globalInterfaces.AndExoPlayerListener

class AboutPauliActivity : BaseActivity(), View.OnClickListener,AndExoPlayerListener{
    private lateinit var binding: ActivityAboutPauliBinding
    private lateinit var viewModel:AboutPaulinNordinViewModel
    private val mp4Url = "https://html5demos.com/assets/dizzy.mp4"
    private var flag:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* getWindow().setFlags(
             WindowManager.LayoutParams.FLAG_SECURE,
             WindowManager.LayoutParams.FLAG_SECURE
         )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_pauli)
        initialise()
        setupViewModel()
        setupObserver()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            AboutPaulinNordinViewModelProvider(AboutPaulinNordinRepository(RetrofitBuilder.apiService))
        ).get(AboutPaulinNordinViewModel::class.java)
        binding.pbAboutIntro.visibility = View.VISIBLE
       viewModel.getAboutPaulinNordinList()
        //binding.about=viewModel
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
//                    ProgressDialog.hideProgressDialog()
                    val apiResponse = it.data!!

                    if (apiResponse.status) {
                        if (apiResponse.code==200){
                            binding.pbAboutIntro.visibility = View.GONE
                            binding.aboutResponseModel=apiResponse.data
                           // binding.ivAbout.text=apiResponse.data?.about
                            if(apiResponse.data?.extension.equals("mp4")){
                                //mp4Url=apiResponse.data?.image.toString()
                                    apiResponse.data?.about.toString()
                                    showVideo(apiResponse.data?.image.toString())
                            }
                        }else{
                            Utils.showSnackBar(binding.root, apiResponse.message)
                        }
                    } else {
                        Utils.showSnackBar(binding.root, apiResponse.message)
                    }

                }
            }

        })
        viewModel.getErrorMsg().observe(this, {
            Utils.showSnackBar(binding.root, it)
        })

    }

    private fun showVideo(mp4Url: String) {
        binding.exoPlayer.playerView.useController=false
        binding.exoPlayer.setShowControllers(true)
        binding.exoPlayer.setSource(mp4Url)
        binding.exoPlayer.setPlayWhenReady(true)
    }

    private fun initialise() {
        binding.clIntroScreen.visibility = View.VISIBLE
        binding.exoPlayer.setOnClickListener(this)
        binding.ivBackIntro.setOnClickListener(this)
    }


    companion object {
        const val TAG = "IntroAndDecisionActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AboutPauliActivity::class.java)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvIntroNext -> {
                startActivity(DashboardActivity.getStartIntent(this))
                finish()
            }

            R.id.iv_back_intro -> {
                onBackPressed()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.exoPlayer.let {
            it.stopPlayer()
            it.releasePlayer()
        }
    }
}