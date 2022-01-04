package com.plugin.bigproject.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.plugin.bigproject.adapters.AntreAdapter
import com.plugin.bigproject.contracts.DetailPartnerActivityContract
import com.plugin.bigproject.databinding.ActivityDetailPartnerBinding
import com.plugin.bigproject.models.Antre
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.presenters.ActivityDetailPartnerPresenter
import com.plugin.bigproject.util.Constants

class DetailPartnerActivity : AppCompatActivity(), DetailPartnerActivityContract.DetailPartnerView {
    private var presenter : DetailPartnerActivityContract.DetailParnerPresenter? = null
    private lateinit var binding : ActivityDetailPartnerBinding
    private lateinit var antreAdapter: AntreAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ActivityDetailPartnerPresenter(this)
        supportActionBar?.hide()
        back()
        showBookingDialogue()
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
        binding.TitleDetail.text = partner.nama_mitra
        binding.BarberAddress.text = partner.alamat_mitra
        binding.TvJmlCrews.text = partner.jmlh_tukangCukur.toString()
        Glide.with(this)
            .load(partner.image)
            .into(binding.ImageDetail)
    }

    override fun showWaitinglist(waitingList: List<Antre>) {
        binding.rvWaitingList.apply {
            antreAdapter = AntreAdapter(waitingList, this@DetailPartnerActivity)
            val layoutManage = LinearLayoutManager(this@DetailPartnerActivity)
            layoutManage.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = layoutManage
            adapter = antreAdapter
        }
    }

    private fun getDetailPartner(){
        val token = Constants.getToken(this)
        val id = intent.getIntExtra("idPartner", 0)
        presenter?.getPartnerbyId(token, id)
    }

    private fun showBookingDialogue(){
        binding.BtnBooking.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Booking this Partner")
            builder.setMessage("Are you sure ?")

            builder.setPositiveButton("Yes") { dialog, which ->
                sendBooking()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        }
    }

    private fun sendBooking(){
        val token = Constants.getToken(this)
        val idMitra = intent.getIntExtra("idPartner", 0)
        val status = "menunggu"
        presenter?.booking(token, idMitra, status)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    override fun onResume() {
        super.onResume()
        getDetailPartner()
        val token = Constants.getToken(this)
        val idMitra = intent.getIntExtra("idPartner", 0)
        presenter?.getAntre(token, idMitra)
    }
}