package com.plugin.bigproject.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.plugin.bigproject.adapters.UserViewPagerAdapter
import com.plugin.bigproject.databinding.ActivityDetailProfileBinding
import com.plugin.bigproject.models.Profile
import com.plugin.bigproject.util.Constants

class DetailProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailProfileBinding
    private lateinit var userViewPagerAdapter: UserViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.title = "Detail User"
        setupViewPager()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViewPager(){
        userViewPagerAdapter = UserViewPagerAdapter(supportFragmentManager, lifecycle)
        with(binding){
            ViewPager.adapter = userViewPagerAdapter

            TabLayoutMediator(Tab, ViewPager){ tab, position ->
                when(position){
                    0 -> tab.text = "Profile"
                    1 -> tab.text = "Booking"
                }

            }.attach()
        }
    }
}