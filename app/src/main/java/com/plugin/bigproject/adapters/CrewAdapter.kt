package com.plugin.bigproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plugin.bigproject.databinding.ListCrewBinding
import com.plugin.bigproject.models.BarberMan

class CrewAdapter (private var listCrew : List<BarberMan>) : RecyclerView.Adapter<CrewAdapter.CrewViewholder>() {
    inner class CrewViewholder (val binding : ListCrewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewholder {
        return CrewViewholder(ListCrewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CrewViewholder, position: Int) {
        holder.binding.apply {
            TVCrewName.text = listCrew[position].name
        }
    }

    override fun getItemCount(): Int {
        return listCrew.size
    }
}

