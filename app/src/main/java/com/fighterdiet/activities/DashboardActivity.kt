package com.fighterdiet.activities
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityDashboardBinding
import com.fighterdiet.fragments.FavouriteFragment
import com.fighterdiet.fragments.HomeFragment
import com.fighterdiet.fragments.HotFragment
import com.fighterdiet.fragments.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding:ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard)
        initialize()
    }

    private fun initialize() {
        binding.bottmNav.setOnNavigationItemSelectedListener(this)
        var frag  = HomeFragment()
        addFragment(frag)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.search_nav ->{
                val fragment = HomeFragment()
                addFragment(fragment)
                return true
            }

            R.id.hot_nav ->{
                val fragment = HotFragment()
                addFragment(fragment)
                return true
            }

            R.id.fav_nav ->{
                val fragment = FavouriteFragment()
                addFragment(fragment)
                return true
            }

            R.id.set_nav ->{
                val fragment = SettingFragment()
                addFragment(fragment)
                return true
            }
        }
        return false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.dash_container, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

}