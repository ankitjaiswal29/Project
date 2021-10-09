package com.fighterdiet.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.adapters.IngredientsRecycleAdapter
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.FragmentIngredientsBinding
import com.fighterdiet.utils.Constants

class IngredientsFragment(val recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>) : BaseFragment() {
    lateinit var binding: FragmentIngredientsBinding
    private lateinit var ingredientAdapter : IngredientsRecycleAdapter

    companion object {
        fun getInstance(recipeIngredientModel: List<RecipeContentResponseModel.Ingredient>): Fragment {
            return IngredientsFragment(recipeIngredientModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ingredients,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initialise()
    }

    private fun initListener() {
        binding.etNoteIngred.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Constants.RecipeDetails.recipeNotes = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun initialise() {
        binding.etNoteIngred.setText(Constants.RecipeDetails.recipeNotes)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvIngredientGroup.layoutManager = LinearLayoutManager(activity)
        ingredientAdapter = IngredientsRecycleAdapter(recipeIngredientModel)
        binding.rvIngredientGroup.adapter = ingredientAdapter
    }
}