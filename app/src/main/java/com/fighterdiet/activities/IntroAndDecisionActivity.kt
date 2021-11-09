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

class IntroAndDecisionActivity : BaseActivity(), View.OnClickListener,AndExoPlayerListener{
    private lateinit var binding: ActivityIntroAndDecisionBinding
    private lateinit var viewModel:AboutPaulinNordinViewModel
    private val mp4Url = "https://html5demos.com/assets/dizzy.mp4"
    private var flag:Boolean=false
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
       //binding.ivBackgroundImage1.setAndExoPlayerListener(this)
    }



    override fun setupUI() {

    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            AboutPaulinNordinViewModelProvider(AboutPaulinNordinRepository(RetrofitBuilder.apiService))
        ).get(AboutPaulinNordinViewModel::class.java)
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
                    ProgressDialog.hideProgressDialog()
                    val apiResponse = it.data!!

                    if (apiResponse.status) {
                        if (apiResponse.code==200){
                            binding.aboutResponseModel=apiResponse.data
                           // binding.ivAbout.text=apiResponse.data?.about
                            if(apiResponse.data?.extension.equals("mp4")){
                                //mp4Url=apiResponse.data?.image.toString()
                                    binding.ivBackgroundImage2.visibility=View.INVISIBLE
                                    apiResponse.data?.about.toString();
                                    showVideo(apiResponse.data?.image.toString())
                            }else{
                                binding.ivBackgroundImage1.visibility=View.INVISIBLE
                                binding.ivPlay.visibility=View.INVISIBLE
                                binding.ivBackgroundImage2.visibility=View.VISIBLE
                                Glide.with(this)
                                    .load(apiResponse.data?.image)
                                    .into(binding.ivBackgroundImage2)
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
        viewModel.getErrorMsg().observe(this, Observer {
            Utils.showSnackBar(binding.root, it)
        })

    }

    private fun showVideo(mp4Url: String) {
        binding.ivBackgroundImage1.playerView.useController=false
        binding.ivBackgroundImage1.setShowControllers(true)
        binding.ivBackgroundImage1.setSource(mp4Url)
        binding.ivBackgroundImage1.setPlayWhenReady(false)
        binding.ivBackgroundImage1.setOnClickListener {

            if (flag){
                flag=false
                binding.ivPlay.visibility=View.VISIBLE
                binding.ivBackgroundImage1.stopPlayer()

            }else{

                binding.ivPlay.visibility=View.GONE
                binding.ivBackgroundImage1.setSource(mp4Url)
                binding.ivBackgroundImage1.startPlayer()
                flag=true
            }

        }

    }

    private fun initialise() {
        binding.clIntroScreen.visibility = View.VISIBLE
        var url: String = ""
        val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("SETTING")!!
        }
        if (url.equals("SETTING")) {
            binding.tvIntroNext.visibility = GONE
        }
        /*
        binding.clDecisionScreen.visibility = View.GONE
        binding.tvDecisionYes.setBackgroundResource(R.drawable.shape_decision_selected)
    */
        binding.tvIntroNext.setOnClickListener(this)

        binding.ivBackgroundImage1.setOnClickListener(this)

        /*  binding.tvDecisionNext.setOnClickListener(this)
          binding.tvDecisionYes.setOnClickListener(this)
          binding.tvDecisionNo.setOnClickListener(this)*/
    }


    companion object {
        const val TAG = "IntroAndDecisionActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, IntroAndDecisionActivity::class.java)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            /*    R.id.tv_decision_yes -> {
                    selected = 1
                    binding.tvDecisionYes.setBackgroundResource(R.drawable.shape_decision_selected)
                    binding.tvDecisionNo.setBackgroundResource(R.drawable.shape_decision_unselected)
                }
                R.id.tv_decision_no -> {
                    selected = 0
                    binding.tvDecisionYes.setBackgroundResource(R.drawable.shape_decision_unselected)
                    binding.tvDecisionNo.setBackgroundResource(R.drawable.shape_decision_selected)
                }
                R.id.tvDecisionNext -> {
                    if (selected != -1) {
                        if (selected == 1) {
                            // Yes
                            Constants.isQuestonnaireCompleted = true
                            startActivity(QuizActivity.getStartIntent(this))
                            finish()

                        } else {
                            // No
                            Constants.isQuestonnaireCompleted = false
                            startActivity(MemberShipActivity.getStartIntent(this))
                            finish()
                        }
                    } else {
                        Utils.showSnackBar(v, getString(R.string.str_please_select_any_option))
                    }
                }*/
            R.id.tvIntroNext -> {
                startActivity(DashboardActivity.getStartIntent(this))
                finish()
//                binding.clIntroScreen.visibility = View.GONE
//                binding.clDecisionScreen.visibility = View.VISIBLE
            }


        }
    }

    /*override fun onExoBuffering() {
        super.onExoBuffering()
        Toast.makeText(this,"onExoBuffering",Toast.LENGTH_LONG).show()
    }

    override fun onExoEnded() {
        super.onExoEnded()
        Toast.makeText(this,"onExoEnded",Toast.LENGTH_LONG).show()
    }

    override fun onExoIdle() {
        super.onExoIdle()
        Toast.makeText(this,"onExoIdle",Toast.LENGTH_LONG).show()
    }

    override fun onExoPlayerFinished() {
        super.onExoPlayerFinished()
        Toast.makeText(this,"onExoPlayerFinished",Toast.LENGTH_LONG).show()

    }

    override fun onExoPlayerStart() {
        super.onExoPlayerStart()
        Toast.makeText(this,"onExoPlayerStart",Toast.LENGTH_LONG).show()

    }

    override fun onExoReady() {
        super.onExoReady()
        Toast.makeText(this,"onExoReady",Toast.LENGTH_LONG).show()

    }*/


}