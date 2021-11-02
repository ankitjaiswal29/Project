package com.fighterdiet.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerRecipeInfoAdapter(var fragments: ArrayList<Fragment>,
                                 var fm: FragmentManager,
                                 internal var totalTabs: Int):FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

}