package com.plugin.bigproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.plugin.bigproject.R
import com.plugin.bigproject.contracts.RegisterActivityContract
import com.plugin.bigproject.databinding.ActivityRegisterBinding
import com.plugin.bigproject.presenters.ActivityRegisterPresenter

class RegisterActivity : AppCompatActivity(), RegisterActivityContract.RegisterActivityView {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter : RegisterActivityContract.RegisterActivityPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ActivityRegisterPresenter(this)
        supportActionBar?.hide()
        doRegister()
    }

    private fun doRegister(){
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val userName = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            presenter.register(name, userName, email, password)
        }

    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun successRegister() {
        startActivity(Intent(this, LoginActivity::class.java).also { finish() })
    }

    override fun showLoading() {
        binding.loading.apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loading.apply {
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