package com.plugin.bigproject.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.florent37.runtimepermission.kotlin.askPermission
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
        binding.BottomNavigation.background = null
        bottomNavigatio()
        askPermissions()
        fab()
        setCurrentFragment(HomeFragment())
    }

    private fun askPermissions(){
        askPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        ){

        }.onDeclined{e ->
            if (e.hasDenied()){
                e.denied.forEach{

                }
                AlertDialog.Builder(this)
                    .setMessage("Please Accept Our Permission")
                    .setPositiveButton("Yes"){_,_ ->
                        e.askAgain()
                    }
                    .setNegativeButton("No"){dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            if (e.hasForeverDenied()){
                e.foreverDenied.forEach {
                }
                e.goToSettings()
            }
        }
    }

    private fun bottomNavigatio(){
        binding.BottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icHome -> setCurrentFragment(HomeFragment())
//                R.id.icMaps -> setCurrentFragment(MapsFragment())
                R.id.icProfile->setCurrentFragment(ProfileFragment())
            }
            true
        }
    }

    private fun fab(){
        binding.fab.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
    }

    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Container, fragment)
            commit()
        }
    }



}