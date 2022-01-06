package com.plugin.bigproject.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListNewsBinding
import com.plugin.bigproject.models.News

class NewsAdapter (private var listNews : List<News>, private val listener: NewsListener) : RecyclerView.Adapter<NewsAdapter.NewsViewholder>() {

    inner class NewsViewholder(val binding : ListNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewholder {
        return NewsViewholder(ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewholder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(listNews[position].image)
                .into(Thumbnail)

            tvHeadlines.text = listNews[position].title
            tvDescription.text =listNews[position].content

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                tvDescription.text = Html.fromHtml(listNews[position].content, Html.FROM_HTML_MODE_COMPACT)
            }else{
                tvDescription.text = Html.fromHtml(listNews[position].content)
            }
        }

        holder.itemView.setOnClickListener {
            listener.onNewsClick(listNews[position])
        }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }
}

interface NewsListener{
    fun onNewsClick(news: News)
}