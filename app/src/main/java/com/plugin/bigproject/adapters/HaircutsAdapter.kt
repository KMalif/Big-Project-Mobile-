package com.plugin.bigproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListTrendingBinding
import com.plugin.bigproject.models.HairCuts

class HaircutsAdapter(private var listHaircut : List<HairCuts>, private val listener: HaircutsListener)
    :RecyclerView.Adapter<HaircutsAdapter.HaircutsViewholder>()
{
        inner class HaircutsViewholder(val binding: ListTrendingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HaircutsViewholder {
        return HaircutsViewholder(ListTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HaircutsViewholder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(listHaircut[position].image)
                .into(Thumbnail)
            HaircutName.text = listHaircut[position].nama
        }
        holder.itemView.setOnClickListener {
            listener.onHaircutClick(listHaircut[position])
        }
    }

    override fun getItemCount(): Int {
        return listHaircut.size
    }
}

interface HaircutsListener{
    fun onHaircutClick(hairCuts: HairCuts)
}