package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugin.bigproject.R
import com.plugin.bigproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}