package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityIntroAndDecisionBinding
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import java.util.*
import javax.sql.DataSource

class IntroAndDecisionActivity : BaseActivity(), View.OnClickListener,Player.EventListener {
    private lateinit var binding: ActivityIntroAndDecisionBinding
    private var selected: Int = 1 // 0 -> Not selected 1 -> Selected

   /* private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition: Long = 0
    private val mp4Url = "https://html5demos.com/assets/dizzy.mp4"
    private val dashUrl = "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears_uhd.mpd"
    private val urlList = listOf(mp4Url to "default", dashUrl to "dash")

  private val dataSourceFactory: DefaultDataSourceFactory by lazy {
      DefaultDataSourceFactory(this,"exo")
  }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* getWindow().setFlags(
             WindowManager.LayoutParams.FLAG_SECURE,
             WindowManager.LayoutParams.FLAG_SECURE
         )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_and_decision)
        initialise()
    }

    override fun setupUI() {

    }

    override fun setupViewModel() {

    }


    override fun setupObserver() {

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
        /*    binding.clDecisionScreen.visibility = View.GONE

            binding.tvDecisionYes.setBackgroundResource(R.drawable.shape_decision_selected)
    */
        binding.tvIntroNext.setOnClickListener(this)
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
  /*  override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(this).build()
        val randomUrl = urlList.random()
        preparePlayer(randomUrl.first, randomUrl.second)
        binding.ivBackgroundImage1.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
        simpleExoplayer.addListener(this)

    }

    private fun buildMediaSource(uri: Uri, type: String): MediaSource {
        return if (type == "dash") {
            DashMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
        } else {
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
        }
    }

    private fun preparePlayer(videoUrl: String, type: String) {
        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri, type)
        simpleExoplayer.prepare(mediaSource)
    }

    private fun releasePlayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

     fun onPlayerError(error: ExoPlaybackException) {
        // handle error
    }*/

    /*override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING)
           // progressBar.visibility = View.VISIBLE
        else if (playbackState == Player.STATE_READY || playbackState == Player.STATE_ENDED)
            //progressBar.visibility = View.INVISIBLE
    }*/


}