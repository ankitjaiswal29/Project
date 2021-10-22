package com.fighterdiet.activities

import android.R.id
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fighterdiet.R
import com.fighterdiet.adapters.ViewPagerRecipeInfoAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.requestModel.*
import com.fighterdiet.data.model.responseModel.CommentListResponseModel
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.data.repository.RecipeInfoViewModelProvider
import com.fighterdiet.databinding.ActivityRecipeInfoBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.Status
import com.fighterdiet.utils.Utils
import com.fighterdiet.viewModel.RecipeInfoViewModel
import com.google.android.material.tabs.TabLayout
import java.lang.Exception
import java.net.URI
import android.R.id.shareText

import androidx.core.app.ShareCompat


class RecipeDetailsActivity : BaseActivity(), View.OnClickListener {
    private var recipeImage: String? = null
    private var recipeName: String? = null
    private var recipeNoteModel: RecipeContentResponseModel.RecipeNote? = null
    private var isNoteAvailable: Boolean = false
    private var commentListModel: CommentListResponseModel? = null
    private var recipeId: String = ""
    private var recipeContentModel: RecipeContentResponseModel? = null
    lateinit var binding: ActivityRecipeInfoBinding
    private val fragments = ArrayList<Fragment>()
    private lateinit var viewModel: RecipeInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_info)
        setupViewModel()
        setupObserver()
        setupUI()
    }

    override fun setupUI() {
        intent.extras?.let {
            recipeId = it.getString(Constants.RECIPE_ID, "")
            if(recipeId.isNotEmpty()){
                viewModel.getRecipeContent(RecipeContentRequestModel(recipeId))
            }
            recipeImage = it.getString(Constants.RECIPE_IMAGE, "")
            recipeImage.let {
                try {
                    Glide.with(this)
                        .load(it)
                        .placeholder(R.color.skyblue)
                        .into(binding.ivBanner)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }

            recipeName = it.getString(Constants.RECIPE_NAME, "")
            recipeName.let {
                try {
                   binding.infoTool.tvTitle.text = it
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelProvider(this, RecipeInfoViewModelProvider(RecipeInfoRepository(RetrofitBuilder.apiService)))
            .get(RecipeInfoViewModel::class.java)
    }

    override fun setupObserver() {
        viewModel.getRecipeInfoResource().observe(this, {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { recipeContent ->
                        recipeContentModel = recipeContent.data

//                        if(recipeContent.data?.recipe_note is RecipeContentResponseModel.RecipeNote){
//                            isNoteAvailable = true
//                            recipeNoteModel = recipeContent.data?.recipe_note as RecipeContentResponseModel.RecipeNote
//                        }
                        initialise()
                    }

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })

        viewModel.getAddToFavResource().observe(this, { response ->
            when(response.status){
                Status.SUCCESS -> {
                    recipeContentModel.apply {
                        this?.apply {
                            response.data?.let {
                                this.favourite = if (it.status) 1 else 0
                                Toast.makeText(this@RecipeDetailsActivity, response.data.message?:"", Toast.LENGTH_SHORT).show()
                            }
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            updateFavUI()
                        },50)
                    }

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })

        viewModel.getAddNotesResource().observe(this, {
            when(it.status){
                Status.SUCCESS -> {
                    super.onBackPressed()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })

        viewModel.getUpdateNotesResource().observe(this, {
            when(it.status){
                Status.SUCCESS -> {
                    super.onBackPressed()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    super.onBackPressed()
                }
            }
        })

    }

    private fun updateFavUI() {
        recipeContentModel?.let {
                binding.ivFav.setImageResource(if(it.favourite == 1) R.drawable.tb_favorite_icon_blue else R.mipmap.heart_holo)
        }
    }

    private fun initialise() {
        binding.infoTool.back.setOnClickListener(this)
        binding.ivComment.setOnClickListener(this)
        binding.ivBanner.setOnClickListener(this)
        binding.ivFav.setOnClickListener(this)
        binding.ivShare.setOnClickListener(this)

        recipeContentModel?.let {
            val info = InfoFragment.getInstance(it.info)
            val ingredientsFragment = IngredientsFragment.getInstance(it.ingredients)
            val directions = DirectionsFragment.getInstance(it.directions)
            val tipsFragment = TipsFragment.getInstance(it.tips)

            fragments.add(info)
            fragments.add(ingredientsFragment)
            fragments.add(directions)
            fragments.add(tipsFragment)
        }

        binding.tab.addTab(binding.tab.newTab().setText("Info"))
        binding.tab.addTab(binding.tab.newTab().setText("Ingredients"))
        binding.tab.addTab(binding.tab.newTab().setText("Directions"))
        binding.tab.addTab(binding.tab.newTab().setText("Tips"))
        updateRecipeNote()
        setupTabs()
        updateFavUI()

    }

    private fun updateRecipeNote() {
        Constants.RecipeDetails.recipeNotes = ""
//        if(isNoteAvailable){
//            recipeNoteModel?.let {
//                Constants.RecipeDetails.recipeNotes = it.description
//            }
//        }
    }

    private fun setupTabs() {
        val adapter = ViewPagerRecipeInfoAdapter(
            fragments,
            supportFragmentManager,
            binding.tab.tabCount
        )
        binding.vpInfoRecepie.adapter = adapter

        binding.vpInfoRecepie.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.tab
            )
        )

        binding.tab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpInfoRecepie.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    companion object {
        const val TAG = "RecipeInfoActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecipeDetailsActivity::class.java)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.back -> {
                onBackPressed()
            }

            R.id.iv_comment -> {
                val commentFragment = CommentFragment.newInstance(recipeId)
                commentFragment.show(
                    supportFragmentManager,
                    CommentFragment::class.simpleName
                )
            }

            R.id.iv_banner -> {
                startActivity(
                    ImageZoomOutActivity.getStartIntent(this).putExtra("from", "Activity")
                )
            }

            R.id.iv_fav -> {
                viewModel.addToFavApi(AddToFavRequestModel(
                    recipeId,
                    PrefManager.getString(PrefManager.KEY_USER_ID)?:""
                ))
            }

            R.id.iv_share -> {
                val link = "https://fighterdiet.page.link/1gGs"+"?key="+recipeId+"&"+"image="+recipeImage+"&"+"navigationTitle="+recipeName?.replace(" ","_")

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                intent.putExtra(Intent.EXTRA_TEXT, link)
                startActivity(
                    Intent.createChooser(intent, getString(R.string.share_to))
                )
            }

        }
    }

    override fun onBackPressed() {

       /* recipeContentModel?.let {
            if(recipeNoteModel == null && Constants.RecipeDetails.recipeNotes.isNotEmpty()){
                viewModel.addNotesApi(AddNotesRequestModel(
                    recipe_id = recipeId,
                    description = Constants.RecipeDetails.recipeNotes
                ))
            }
            else{
                if(Constants.RecipeDetails.recipeNotes != recipeNoteModel?.description)
                {
                    viewModel.updateNotesApi(
                        UpdateNotesRequestModel(
                            note_id = recipeNoteModel?.id.toString(),
                            description = Constants.RecipeDetails.recipeNotes
                        )
                    )
                }
                else{
                    super.onBackPressed()
                }
            }
            return
        }*/

        super.onBackPressed()

    }
}