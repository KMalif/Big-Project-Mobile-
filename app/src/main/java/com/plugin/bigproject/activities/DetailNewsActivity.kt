package com.plugin.bigproject.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.bumptech.glide.Glide
import com.plugin.bigproject.contracts.DetailNewsContract
import com.plugin.bigproject.databinding.ActivityDetailNewsBinding
import com.plugin.bigproject.models.News
import com.plugin.bigproject.presenters.ActivityDetailNewsPresenter
import com.plugin.bigproject.util.Constants

class DetailNewsActivity : AppCompatActivity(), DetailNewsContract.DetailNewsView {
    private var presenter : DetailNewsContract.DetailNewsPresenter? = null
    private lateinit var binding : ActivityDetailNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = ActivityDetailNewsPresenter(this)
        back()
    }
    private fun back(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getDetailNews(){
        val id = intent.getIntExtra("idNews", 0)
        val token = Constants.getToken(this)
        presenter?.getDetailNews(id, token)
    }

    override fun showDetailNews(news: News) {
        binding.TitleDetail.text = news.title
//        binding.ContentDetail.text = news.title
        htmlParser(news.content!!)
        Glide.with(this)
            .load(news.image)
            .into(binding.ImageDetail)


    }

    private fun htmlParser(content : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            binding.ContentDetail.text = Html.fromHtml("$content",Html.FROM_HTML_MODE_COMPACT)
        }else{
            binding.ContentDetail.text = Html.fromHtml("$content")
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        getDetailNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}