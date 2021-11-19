package com.plugin.bigproject.activities

import android.content.Intent
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
        createAccount()
        login()
    }
    private fun createAccount(){
        binding.tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun login(){
        binding.btnLogin.setOnClickListener { startActivity(Intent(this, MainActivity::class.java).also { finish() }) }
    }
}