package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugin.bigproject.databinding.ActivityEditProfilesBinding

class EditProfilesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfilesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        iconBackClicked()
    }
    private fun iconBackClicked(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}