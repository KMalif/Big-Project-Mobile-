package com.plugin.bigproject.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListRecomendationBinding
import com.plugin.bigproject.models.Recomendation

class RecomendationAdapter(private var recomendations : List<Recomendation>, private val listener: RecomendationListener) : RecyclerView.Adapter<RecomendationAdapter.RecomenViewHolder>() {

    inner class RecomenViewHolder(val binding : ListRecomendationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomenViewHolder {
        return RecomenViewHolder(ListRecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecomenViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(recomendations[position].image)
                .into(ImgHaircuts)
            TvHaircuts.text = recomendations[position].nama_model
            TvCategory.text = recomendations[position].bentuk
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                TvContent.text = Html.fromHtml(recomendations[position].content, Html.FROM_HTML_MODE_COMPACT)
            }else{
                TvContent.text = Html.fromHtml(recomendations[position].content)
            }
        }
        holder.itemView.setOnClickListener{
            listener.onRecomendationClick(recomendations[position])
        }

    }

    override fun getItemCount(): Int {
        return recomendations.size
    }
}

interface RecomendationListener{
    fun onRecomendationClick(recomendation: Recomendation)
}