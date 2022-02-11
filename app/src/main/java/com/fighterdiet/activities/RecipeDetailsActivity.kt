package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fighterdiet.R
import com.fighterdiet.adapters.ViewPagerRecipeInfoAdapter
import com.fighterdiet.data.api.RetrofitBuilder
import com.fighterdiet.data.model.requestModel.AddNotesRequestModel
import com.fighterdiet.data.model.requestModel.AddToFavRequestModel
import com.fighterdiet.data.model.requestModel.RecipeContentRequestModel
import com.fighterdiet.data.model.requestModel.UpdateNotesRequestModel
import com.fighterdiet.data.model.responseModel.CommentListResponseModel
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.data.repository.RecipeInfoViewModelProvider
import com.fighterdiet.databinding.ActivityRecipeInfoBinding
import com.fighterdiet.fragments.*
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.PrefManager
import com.fighterdiet.utils.ProgressDialog
import com.fighterdiet.utils.Status
import com.fighterdiet.viewModel.RecipeInfoViewModel
import com.google.android.material.tabs.TabLayout

import com.google.firebase.ktx.Firebase
import com.google.gson.internal.LinkedTreeMap


class RecipeDetailsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var viewPagerAdapter: ViewPagerRecipeInfoAdapter
    private lateinit var infoFragment: InfoFragment
    private lateinit var ingredientsFragment: IngredientsFragment
    private lateinit var directionsFragment: DirectionsFragment
    private lateinit var tipsFragment: TipsFragment
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
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_info)
        initProgress()
        setupViewModel()
        setupObserver()
        setupUI()
    }

    private fun initProgress() {
        circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
    }

    override fun setupUI() {
        intent.extras?.let {
            recipeId = it.getString(Constants.RECIPE_ID, "")
            if(recipeId.isNotEmpty()){

                if(PrefManager.getString(PrefManager.KEY_AUTH_TOKEN).isNullOrBlank()){
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                }

                ProgressDialog.showProgressDialog(this)
                viewModel.getRecipeContent(RecipeContentRequestModel(recipeId))
            }

            recipeImage = it.getString(Constants.RECIPE_IMAGE, "")
            recipeImage.let {imageUrl ->
                try {
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(circularProgressDrawable)
                        .into(binding.ivBanner)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }

            recipeName = it.getString(Constants.RECIPE_NAME, "")
            recipeName.let {
                try {
                    binding.tvTitle.text = it
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
        viewModel.getRecipeInfoResource().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialog.hideProgressDialog()
                    it.data?.let { recipeContent ->
                        recipeContentModel = recipeContent.data

                        recipeContent.data?.let {

                            if (it.recipe_note !is String) {
                                val recipeNotesTree: LinkedTreeMap<String, Any> =
                                    recipeContentModel?.recipe_note as LinkedTreeMap<String, Any>

                                recipeNoteModel = RecipeContentResponseModel.RecipeNote(
                                    recipeNotesTree["id"] as Double,
                                    recipeNotesTree["user_id"] as Double,
                                    recipeNotesTree["receipe_id"] as Double,
                                    recipeNotesTree["type"] as Double,
                                    recipeNotesTree["description"] as String,
                                    recipeNotesTree["created_at"] as String,
                                    recipeNotesTree["updated_at"] as String
                                )
                                isNoteAvailable = true
                                it.recipe_note =
                                    (recipeNoteModel as RecipeContentResponseModel.RecipeNote).toString()
                            }
                            initialise()
                        }
                    }

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        }

        viewModel.getAddToFavResource().observe(this) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    ProgressDialog.hideProgressDialog()
                    recipeContentModel.apply {
                        this?.apply {
                            response.data?.let {
                                this.favourite = if (it.status) 1 else 0
                                //Toast.makeText(this@RecipeDetailsActivity, response.data.message?:"", Toast.LENGTH_SHORT).show()
                            }
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            updateFavUI()
                        }, 50)
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
            }
        }

        viewModel.getAddNotesResource().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    super.onBackPressed()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        }

        viewModel.getUpdateNotesResource().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    super.onBackPressed()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    super.onBackPressed()
                }
            }
        }

    }

    private fun updateFavUI() {
        recipeContentModel?.let {
            binding.ivFav.setImageResource(if(it.favourite == 1) R.drawable.tb_favorite_icon_blue else R.mipmap.heart_holo)
            binding.ivFav.setColorFilter(if(it.favourite == 1) resources.getColor(R.color.blue_main) else resources.getColor(R.color.white))
        }
    }

    private fun initialise() {
        binding.back.setOnClickListener(this)
        binding.ivComment.setOnClickListener(this)
        binding.ivBanner.setOnClickListener(this)
        binding.ivFav.setOnClickListener(this)
        binding.ivShare.setOnClickListener(this)

        recipeContentModel?.let {
            try {
                infoFragment = InfoFragment()
                infoFragment.passData(it.info)

                ingredientsFragment = IngredientsFragment()
                ingredientsFragment.passData(it.ingredients)

                directionsFragment = DirectionsFragment()
                directionsFragment.passData(it.directions)

                tipsFragment = TipsFragment()
                tipsFragment.passData(it.tips)

                fragments.add(infoFragment)
                fragments.add(ingredientsFragment)
                fragments.add(directionsFragment)
                fragments.add(tipsFragment)
            } catch (e: Exception) {
                Toast.makeText(this, ""+e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }
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
        recipeNoteModel?.let {
            Constants.RecipeDetails.recipeNotes = it.description
            Constants.RecipeDetails.recipeNotesLive.postValue(it.description)
            return
        }
        Constants.RecipeDetails.recipeNotesLive.postValue("")
//        }
    }

    private fun setupTabs() {
        viewPagerAdapter = ViewPagerRecipeInfoAdapter(
            fragments,
            supportFragmentManager,
            binding.tab.tabCount
        )

        binding.vpInfoRecepie.adapter = viewPagerAdapter

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
                recipeImage?.let {
                    startActivity(
                        ImageZoomOutActivity.getStartIntent(this, it)
                    )
                }
            }

            R.id.iv_fav -> {
                ProgressDialog.showProgressDialog(this)
                viewModel.addToFavApi(AddToFavRequestModel(
                    recipeId,
                    PrefManager.getString(PrefManager.KEY_USER_ID)?:""
                ))
            }

            R.id.iv_share -> {
                //val sharedLink = "key="+recipeId+"&"+"image="+recipeImage+"&"+"recipeTitle="+recipeName?.replace(" ","_")

                val sharedLink = "https://fighterdiet.page.link/1gGs?key="+recipeId+"&"+"image="+recipeImage+"&"+"recipeTitle="+recipeName?.replace(" ","_")
//                val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
//                    link =
//                        Uri.parse(sharedLink)
//                    domainUriPrefix = "https://fighterdiet.page.link/1gGs"
//
//                    androidParameters {
//                        // The versionCode of the minimum version of your app that can open the link.
//                        // If the installed app is an older version, the user is taken to the Play Store to upgrade the app.
//                        minimumVersion = 1
//                    }
//                    // Set parameters
//                    // ...
//                }.addOnSuccessListener { result ->
//                    // Short link created
//                    val link = result.shortLink
//                    //val flowchartLink = result.previewLink
//                    shareLink(link.toString())
//                }.addOnFailureListener {
//                    // Error
//                    // ...
//                    Log.d("log_tag", "==> ${it.localizedMessage}", it)
//                }

                shareLink(sharedLink)

            }

        }
    }

    private fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, link)
        startActivity(
            Intent.createChooser(intent, getString(R.string.share_to))
        )
    }

    override fun onBackPressed() {
        recipeContentModel?.let {
            val currentFragment: Fragment
            when(binding.vpInfoRecepie.currentItem){
                0 -> {
                    currentFragment = infoFragment
                    Constants.RecipeDetails.recipeNotesLive.value = currentFragment.binding.etNoteInfo.text.toString()
                }
                1 -> {
                    currentFragment = ingredientsFragment
                    Constants.RecipeDetails.recipeNotesLive.value = currentFragment.binding.etNoteIngred.text.toString()
                }
                2 -> {
                    currentFragment = directionsFragment
                    Constants.RecipeDetails.recipeNotesLive.value = currentFragment.binding.etNote.text.toString()
                }
                3 -> {
                    currentFragment = tipsFragment
                    Constants.RecipeDetails.recipeNotesLive.value = currentFragment.binding.etNote.text.toString()
                }
            }

            if(recipeNoteModel == null && Constants.RecipeDetails.recipeNotesLive.value!!.isNotEmpty()){
                viewModel.addNotesApi(AddNotesRequestModel(
                    recipe_id = recipeId,
                    description = Constants.RecipeDetails.recipeNotesLive.value!!
                ))
            }
            else{
                if(recipeNoteModel != null && Constants.RecipeDetails.recipeNotesLive.value != recipeNoteModel?.description) {
                    viewModel.updateNotesApi(
                        UpdateNotesRequestModel(
                            note_id = recipeNoteModel?.id.toString(),
                            description = Constants.RecipeDetails.recipeNotesLive.value!!
                        )
                    )
                }
                else{
                    super.onBackPressed()
                }
            }
            return
        }
    }

}