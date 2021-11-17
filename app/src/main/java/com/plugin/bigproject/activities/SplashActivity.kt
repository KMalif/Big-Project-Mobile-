package com.plugin.bigproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.plugin.bigproject.R
import com.plugin.bigproject.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        splashScreen()
    }
    private fun splashScreen(){
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 3000)
    }
}