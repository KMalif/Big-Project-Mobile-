package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.plugin.bigproject.contracts.DetailPartnerActivityContract
import com.plugin.bigproject.databinding.ActivityDetailPartnerBinding
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.presenters.ActivityDetailPartnerPresenter

class DetailPartnerActivity : AppCompatActivity(), DetailPartnerActivityContract.DetailPartnerView {
    private var presenter : DetailPartnerActivityContract.DetailParnerPresenter? = null
    private lateinit var binding : ActivityDetailPartnerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ActivityDetailPartnerPresenter(this)
        supportActionBar?.hide()
        back()
    }

    private fun back(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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

    override fun showDetailPartner(partner: Partners) {
        binding.TitleDetail.text = partner.namaMitra
        binding.BarberAddress.text = partner.alamat
        Glide.with(this)
            .load(partner.image)
            .into(binding.ImageDetail)
    }

    private fun getDetailPartner(){
        val id = intent.getIntExtra("idPartner", 0)
        presenter?.getPartnerbyId(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    override fun onResume() {
        super.onResume()
        getDetailPartner()
    }
}