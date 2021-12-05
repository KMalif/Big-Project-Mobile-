package com.plugin.bigproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.plugin.bigproject.contracts.LoginActivityContract
import com.plugin.bigproject.databinding.ActivityLoginBinding
import com.plugin.bigproject.presenters.ActivityLoginPresenter
import com.plugin.bigproject.util.Constants

class LoginActivity : AppCompatActivity(), LoginActivityContract.LoginActivityView {
    private lateinit var presenter : LoginActivityContract.LoginActivityPresenter
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = ActivityLoginPresenter(this)
        createAccount()
        login()
    }

    override fun onStart() {
        super.onStart()
        isLogin()
    }

    private fun isLogin(){
        val name = Constants.getName(this)

        println("Name "+Constants.getName(this))
        if (name != "JhonDoe"){
            startActivity(Intent(this, MainActivity::class.java).also { finish() })
        }
    }
    private fun createAccount(){
        binding.tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun login(){
        binding.btnLogin.setOnClickListener {
            val userName = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (userName.isNotEmpty() && password.isNotEmpty()){
                presenter.login(userName, password,this)
            }else{
                showToast("Please input all form")
            }

        }
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun successLogin() {
        startActivity(Intent(this, MainActivity::class.java).also { finish() })
    }

    override fun showLoading() {
        binding.loadingLogin.apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loadingLogin.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}