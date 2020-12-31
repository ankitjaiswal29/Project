package com.fighterdiet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fighterdiet.R
import com.fighterdiet.activities.FilterActivity.Companion.count
import com.fighterdiet.adapters.DietryInfoAdapter
import com.fighterdiet.databinding.FragmentDietryInfoBinding
import com.fighterdiet.model.DietryModel

class DietryInfoFragment : Fragment(),
    DietryInfoAdapter.DietaryCountListener {
    lateinit var binding: FragmentDietryInfoBinding
    private lateinit var dietryInfoAdapter : DietryInfoAdapter
    private lateinit var dietaryListener: DietaryInfoInterface
    var list:ArrayList<DietryModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dietry_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dietaryListener = (activity as DietaryInfoInterface?)!!
        initialise()
    }

    private fun initialise() {
        setUpDietryList()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvDietryInfo.layoutManager = LinearLayoutManager(activity)
        dietryInfoAdapter = DietryInfoAdapter(activity, list, this)/*{
                position,view ->
            Utils.showSnackBar(binding.rvDietryInfo,"mes")
        }*/
        binding.rvDietryInfo.adapter = dietryInfoAdapter
    }

    private fun setUpDietryList() {
        list.add(DietryModel("Milk/Dairy Free", false))
        list.add(DietryModel("Soy Free", true))
        list.add(DietryModel("Seafood Free", true))
        list.add(DietryModel("Shellfish Free", false))
        list.add(DietryModel("Eggs Free", false))
        list.add(DietryModel("Peanuts Free", false))
        list.add(DietryModel("Wheat Free", false))
        list.add(DietryModel("Tree Nuts Free", true))
        list.add(DietryModel("Gluten Free", false))
    }


    override fun dietaryInfoAdapterListener(position: Int, checkBox: CheckBox) {
        if (checkBox.isChecked){
            count++
        }else if (!checkBox.isChecked){
            count--

        }
        dietaryListener.dietaryInfoCount(count)
//       Utils.showToast(context, "fragment   $count")
    }

    interface DietaryInfoInterface{
        fun dietaryInfoCount(count: Int)
    }

}