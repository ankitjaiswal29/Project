package com.fighterdiet.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.databinding.FragmentPersonalChartBinding


class PersonalChartFragment : Fragment() {

    private lateinit var binding: FragmentPersonalChartBinding
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val sheetView: View =
            layoutInflater.inflate(R.layout.fragment_personal_chart, container, false)
        binding = DataBindingUtil.bind(sheetView)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivQuestion.setOnClickListener {
            val commentFragment = CommentFragment.newInstance()
            commentFragment.show(
                childFragmentManager,
                CommentFragment::class.simpleName
            )
        }
    }

}