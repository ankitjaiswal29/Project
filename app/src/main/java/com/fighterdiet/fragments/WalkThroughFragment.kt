package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.databinding.FragmentWalkThroughBinding

class WalkThroughFragment : BaseFragment() {
    private lateinit var binding: FragmentWalkThroughBinding
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_walk_through, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        position = arguments?.getInt(KEY_INDEX)!!
        when (position) {
            0 -> {
                binding.ivBackgroundImage.setImageResource(R.drawable.colorBlack)
            }
            1 -> {
                binding.ivBackgroundImage.setImageResource(R.drawable.colorGray)
            }
            2 -> {
                binding.ivBackgroundImage.setImageResource(R.drawable.colorBlack)
            }
            3 -> {
                binding.ivBackgroundImage.setImageResource(R.drawable.colorGray)
            }
        }
    }

    companion object {
        const val KEY_INDEX = "key_index"

        fun getInstance(index: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt(KEY_INDEX, index)
            val fragment = WalkThroughFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}