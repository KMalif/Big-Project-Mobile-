package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugin.bigproject.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnBack()
    }
    private fun btnBack(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }


}