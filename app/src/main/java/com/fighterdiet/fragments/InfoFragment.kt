package com.fighterdiet.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.FragmentInfoBinding
import com.fighterdiet.utils.Constants
import com.fighterdiet.utils.getProtein

class InfoFragment(val recipeInfoModel: List<RecipeContentResponseModel.Info>) : BaseFragment() {
    lateinit var binding: FragmentInfoBinding

    companion object {
        fun getInstance(recipeInfoModel: List<RecipeContentResponseModel.Info>): Fragment {
            return InfoFragment(recipeInfoModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        setupUI()
    }

    private fun initialise() {
        binding.etNoteInfo.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Constants.RecipeDetails.recipeNotes = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun setupUI() {
        binding.etNoteInfo.setText(Constants.RecipeDetails.recipeNotes)
        if(recipeInfoModel.isNullOrEmpty())
            return

        recipeInfoModel[0].let {
            binding.tvBigMeals.text = it.recipe_volume
            binding.tvPrepTime.text = "${it.prep_time} ${getString(R.string.min_prep_time)}"
            binding.tvCookTime.text = "${it.cook_time} ${getString(R.string.min_cook_time)}"
            binding.tvProteins.text = "${getProtein(it.calories)} ${getString(R.string.protein)}"
            binding.tvCalories.text = "${it.calories} ${getString(R.string.calories)}"
            binding.tvServing.text = if(it.serving_for.toInt() <= 1) "${it.serving_for} ${getString(R.string.serving)}" else "${it.serving_for} ${getString(R.string.servings)}"
        }
    }
}