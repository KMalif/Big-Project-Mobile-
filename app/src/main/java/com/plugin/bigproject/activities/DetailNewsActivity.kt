package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugin.bigproject.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun back(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}