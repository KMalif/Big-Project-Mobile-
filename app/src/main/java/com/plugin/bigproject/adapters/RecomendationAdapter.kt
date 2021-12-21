package com.plugin.bigproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListRecomendationBinding
import com.plugin.bigproject.models.Recomendation

class RecomendationAdapter(private var recomendations : List<Recomendation>) : RecyclerView.Adapter<RecomendationAdapter.RecomenViewHolder>() {

    inner class RecomenViewHolder(val binding : ListRecomendationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomenViewHolder {
        return RecomenViewHolder(ListRecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecomenViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(recomendations[position].image)
                .into(ImgHaircuts)
            TvHaircuts.text = recomendations[position].shape
        }
    }

    override fun getItemCount(): Int {
        return recomendations.size
    }
}