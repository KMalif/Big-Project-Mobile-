package com.plugin.bigproject.activities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.bumptech.glide.Glide
import com.plugin.bigproject.contracts.DetailRecomendationContract
import com.plugin.bigproject.databinding.ActivityDetailRekomendationBinding
import com.plugin.bigproject.models.Recomendation
import com.plugin.bigproject.presenters.DetailRecomendationActivityPresenter
import com.plugin.bigproject.util.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class DetailRekomendationActivity : AppCompatActivity(), DetailRecomendationContract.View {
    private lateinit var binding: ActivityDetailRekomendationBinding
    private lateinit var presenter: DetailRecomendationContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRekomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = DetailRecomendationActivityPresenter(this)
        iconBackClicked()
        getDetailContent()
    }

    private fun iconBackClicked(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun htmlParser(content : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            binding.ContentDetail.text = Html.fromHtml("$content", Html.FROM_HTML_MODE_COMPACT)
        }else{
            binding.ContentDetail.text = Html.fromHtml("$content")
        }
    }

    private fun getDetailContent(){
        val bundle: Bundle? = intent.extras
        val recomendation  = bundle?.getSerializable("recomendation") as Recomendation?
        if(recomendation != null){
            binding.TitleDetail.text = recomendation.nama_model
            htmlParser(recomendation.content!!)
            Glide.with(this@DetailRekomendationActivity)
                .load(recomendation.image)
                .into(binding.ImageDetail)
            addWishlist(recomendation)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun addWishlist(recomendation : Recomendation?){
        if(recomendation != null){
        val token = Constants.getToken(this)
        val jsonObject = JSONObject()
        jsonObject.put("image", recomendation.image)
        jsonObject.put("nama_model", recomendation.nama_model)
        jsonObject.put("content", recomendation.content)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            binding.BtnWishlist.setOnClickListener {
                presenter.addWishlist(token, requestBody)
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}