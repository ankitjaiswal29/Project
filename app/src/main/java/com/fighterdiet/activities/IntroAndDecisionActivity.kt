package com.fighterdiet.activities
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fighterdiet.R
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.repository.AboutPaulinNordinRepository
import com.fighterdiet.data.repository.LoginRepository
import com.fighterdiet.databinding.ActivityIntroAndDecisionBinding
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.AboutPaulinNordinViewModel
import com.fighterdiet.viewModel.AboutPaulinNordinViewModelProvider
import com.fighterdiet.viewModel.LoginViewModel
import com.fighterdiet.viewModel.LoginViewModelProvider
import com.potyvideo.library.globalInterfaces.AndExoPlayerListener

class IntroAndDecisionActivity : BaseActivity(), View.OnClickListener{
    private lateinit var binding: ActivityIntroAndDecisionBinding
    private lateinit var viewModel:AboutPaulinNordinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* getWindow().setFlags(
             WindowManager.LayoutParams.FLAG_SECURE,
             WindowManager.LayoutParams.FLAG_SECURE
         )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_and_decision)
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
//                                    binding.ivIntroImage.visibility=View.INVISIBLE
                                    apiResponse.data?.about.toString();
                                    showVideo(apiResponse.data?.image.toString())
                            }
//                            else{
//                                binding.ivIntroImage.visibility=View.VISIBLE
//                                Glide.with(this)
//                                    .load(apiResponse.data?.image)
//                                    .into(binding.ivIntroImage)
//                            }
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

    private fun showVideo(mp4Url: String) {
        binding.exoPlayer.playerView.useController=false
        binding.exoPlayer.setShowControllers(true)
        binding.exoPlayer.setSource(mp4Url)
        binding.exoPlayer.setPlayWhenReady(true)
//        binding.exoPlayer.setSource(mp4Url)
//        binding.exoPlayer.startPlayer()


//        binding.exoPlayer.setOnClickListener {
//
//            if (flag){
//                flag=false
//                binding.exoPlayer.stopPlayer()
//
//            }else{
//                binding.exoPlayer.setSource(mp4Url)
//                binding.exoPlayer.startPlayer()
//                flag=true
//            }
//        }
    }

    override fun onPause() {
        super.onPause()
        binding.exoPlayer.let {
            it.stopPlayer()
            it.releasePlayer()
        }
    }

    private fun initialise() {
        binding.tvIntroNext.setOnClickListener(this)
    }


    companion object {
        const val TAG = "IntroAndDecisionActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, IntroAndDecisionActivity::class.java)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tvIntroNext -> {
                val intent=Intent(this,DashboardActivity::class.java)
                startActivity(intent)
                finish()

            }

            R.id.iv_back_intro -> {
                onBackPressed()
            }


        }
    }

}