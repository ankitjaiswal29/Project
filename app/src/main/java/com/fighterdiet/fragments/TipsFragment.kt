package com.fighterdiet.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fighterdiet.R
import com.fighterdiet.adapters.RecipeTipsRecycleAdapter
import com.fighterdiet.data.model.responseModel.RecipeContentResponseModel
import com.fighterdiet.databinding.FragmentDirectionsBinding
import com.fighterdiet.utils.Constants

class TipsFragment(val recipeTipsModel: List<RecipeContentResponseModel.Tip>) : BaseFragment() {
    private lateinit var rvTips: RecyclerView
    lateinit var binding: FragmentDirectionsBinding

    companion object {
        fun getInstance(recipeTipsModel: List<RecipeContentResponseModel.Tip>): Fragment {
            return TipsFragment(recipeTipsModel)
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_directions,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTips = binding.rvDirections
        initListener()
        initRv()
        initUI()
    }

    private fun initUI() {
        binding.etNote.setText(Constants.RecipeDetails.recipeNotes)
    }

    private fun initRv() {
        rvTips.layoutManager = LinearLayoutManager(activity)
        val rvTipsAdapter = RecipeTipsRecycleAdapter(recipeTipsModel)
        binding.rvDirections.adapter = rvTipsAdapter
    }

    private fun initListener() {
        binding.etNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Constants.RecipeDetails.recipeNotes = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
}