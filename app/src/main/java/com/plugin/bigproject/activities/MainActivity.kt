package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.plugin.bigproject.R
import com.plugin.bigproject.databinding.ActivityMainBinding
import com.plugin.bigproject.fragments.HomeFragment
import com.plugin.bigproject.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        bottomNavigatio()
    }

    private fun bottomNavigatio(){
        binding.BottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icHome -> setCurrentFragment(HomeFragment())
                R.id.icProfile->setCurrentFragment(ProfileFragment())

            }
            true
        }
    }

    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Container, fragment)
            commit()
        }
    }

}