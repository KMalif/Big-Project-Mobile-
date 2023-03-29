package com.plugin.bigproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListHistoryBinding
import com.plugin.bigproject.models.History

class HistoryAdapter(private var listHistory : List<History>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding : ListHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(listHistory[position].image)
                .into(Thumbnail)
            tvResult.text = listHistory[position].nama_hasil
            TvHaircutsName.text = listHistory[position].nama_model
            TvDate.text = listHistory[position].date.toString()
        }
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }
}