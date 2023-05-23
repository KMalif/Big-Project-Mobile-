package com.plugin.bigproject.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ActivityDetailRekomendationBinding
import com.plugin.bigproject.models.Recomendation

class DetailRekomendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRekomendationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRekomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        bundle.apply {
            val recomendation  = this?.getSerializable("recomendation") as Recomendation?
            if(recomendation != null){
                binding.TitleDetail.text = recomendation.name_model
                htmlParser(recomendation.content!!)
                Glide.with(this@DetailRekomendationActivity)
                    .load(recomendation.image)
                    .into(binding.ImageDetail)


            }
        }
    }
}