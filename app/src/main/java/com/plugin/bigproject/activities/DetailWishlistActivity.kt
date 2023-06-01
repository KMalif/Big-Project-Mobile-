package com.plugin.bigproject.activities

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ActivityDetailWishlistBinding
import com.plugin.bigproject.models.History
import com.plugin.bigproject.models.Recomendation

class DetailWishlistActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailWishlistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        getDetailContent()
        iconBackClicked()
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

    @SuppressLint("SetTextI18n")
    private fun getDetailContent(){
        val bundle: Bundle? = intent.extras
        val wishlist  = bundle?.getSerializable("wishlist") as History?
        if(wishlist != null){
            binding.ContentDate.text = "Ditambahkan pada ${wishlist.date}"
            binding.TitleDetail.text = wishlist.nama_model
            htmlParser(wishlist.content!!)
            Glide.with(this)
                .load(wishlist.image)
                .into(binding.ImageDetail)
        }
    }
}