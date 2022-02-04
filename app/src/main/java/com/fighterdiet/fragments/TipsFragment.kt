package com.fighterdiet.fragments

import android.os.Bundle
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

class TipsFragment : BaseFragment() {
    var recipeTipsModel: List<RecipeContentResponseModel.Tip>?=null
    private lateinit var rvTips: RecyclerView
    lateinit var binding: FragmentDirectionsBinding

    companion object {
        fun getInstance(): TipsFragment {
            return TipsFragment()
        }
    }

    fun passData(recipeTipsModel: List<RecipeContentResponseModel.Tip>){
        this.recipeTipsModel=recipeTipsModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_directions,container,false)
        rvTips = binding.rvDirections
        Constants.RecipeDetails.recipeNotesLive.observe(viewLifecycleOwner,{
            binding.etNote.setText(it)
        })

        initListener()
        initRv()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initRv() {
        rvTips.layoutManager = LinearLayoutManager(activity)
        val rvTipsAdapter = RecipeTipsRecycleAdapter(recipeTipsModel!!)
        binding.rvDirections.adapter = rvTipsAdapter
    }

    private fun initListener() {
//        binding.etNote.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString()!=binding.etNote.text.toString())
//                    Constants.RecipeDetails.recipeNotesLive.postValue(p0.toString())
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//        })

//        binding.etNote.setOnFocusChangeListener { view, isFocused ->
//            if(!isFocused){
//                Constants.RecipeDetails.recipeNotesLive.postValue(binding.etNote.text.toString())
//            }
//        }
    }

    override fun onPause() {
        super.onPause()
        Constants.RecipeDetails.recipeNotesLive.postValue(binding.etNote.text.toString())
    }

}