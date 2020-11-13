package com.fighterdiet.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fighterdiet.R
import com.fighterdiet.databinding.ActivityPersonalChartBinding
import com.fighterdiet.fragments.PersonalChartFragment

class PersonalChartActivity : BaseActivity() {

    private lateinit var binding: ActivityPersonalChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_chart)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, PersonalChartFragment()).commit()
    }

    companion object {
        const val TAG = "PersonalChartActivity"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, PersonalChartActivity::class.java)
        }
    }
}