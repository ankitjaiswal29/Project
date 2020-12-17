package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityPersonalChartBinding
import com.fighterdiet.fragments.PersonalChartFragment

class PersonalChartActivity : BaseActivity() {

    private lateinit var binding: ActivityPersonalChartBinding
    lateinit var from :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_chart)
        var bundle:Bundle = intent.extras!!
        var personalChartFragment:PersonalChartFragment = PersonalChartFragment()
        personalChartFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, personalChartFragment).commit()
    }

    companion object {
        const val TAG = "PersonalChartActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, PersonalChartActivity::class.java)
        }
    }
}