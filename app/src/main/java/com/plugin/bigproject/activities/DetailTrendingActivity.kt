package com.plugin.bigproject.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ActivityDetailTrendingBinding
import com.plugin.bigproject.models.HairCuts

class DetailTrendingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTrendingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTrendingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        iconBackClicked()
        getDetailHaircut()
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

    private fun getDetailHaircut(){
        val bundle : Bundle? = intent.extras
        bundle.apply {
            val haircuts = this?.getSerializable("haircut") as HairCuts?
            if (haircuts != null){
                binding.TitleDetail.text = haircuts.nama_model
                htmlParser(haircuts.content!!)
                Glide.with(this@DetailTrendingActivity)
                    .load(haircuts.image)
                    .into(binding.ImageDetail)

            }
        }
    }


}