package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugin.bigproject.databinding.ActivityDetailTrendingBinding

class DetailTrendingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTrendingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTrendingBinding.inflate(layoutInflater)
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