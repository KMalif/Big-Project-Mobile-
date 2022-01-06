package com.plugin.bigproject.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plugin.bigproject.databinding.ActivityDetailProfileBinding
import com.plugin.bigproject.models.Profile
import com.plugin.bigproject.util.Constants

class DetailProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.title = "Detail User"
        setDetailProfile()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setDetailProfile(){
        binding.apply {
            val username = intent.getStringExtra("Username")
            val name = intent.getStringExtra("Name")
            val email = intent.getStringExtra("Email")
            val hp = intent.getStringExtra("NoHp")
            val gender = intent.getStringExtra("Gender")
            TvUsername.text = username
            TvName.text = name
            Tvuser.text = username
            TvEmail.text = email
            TvHp.text = hp
            TvGender.text = gender
        }
    }

//    private fun checkIsLoggedIn(){
//        val token = Constants.getToken(this)
//        if(token.equals("UNDEFINED")){
//            startActivity(Intent(this, LoginActivity::class.java).also { finish() })
//        }
//    }
//
//    private fun logout(){
//        Constants.clearName(this)
//        Constants.clearToken(this)
//        Constants.clearId(this)
//        Constants.clearGender(this)
//        checkIsLoggedIn()
//    }
//
//    private fun showAlertDialogue(){
//        binding.BtnLogout.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Logout")
//            builder.setMessage("Are you sure ?")
//
//            builder.setPositiveButton("Yes") { dialog, which ->
//                logout()
//            }
//
//            builder.setNegativeButton("Cancel") { dialog, which ->
//                dialog.cancel()
//            }
//
//            builder.show()
//        }
//    }
}