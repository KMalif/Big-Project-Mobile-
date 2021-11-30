package com.plugin.bigproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListPartnerBinding
import com.plugin.bigproject.models.Partners

class PartnersAdapter(private var listPartner : List<Partners>, private val listener: PartnersListener) : RecyclerView.Adapter<PartnersAdapter.PartnerViewholder>() {

    inner class PartnerViewholder(val binding : ListPartnerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerViewholder {
        return PartnerViewholder(ListPartnerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PartnerViewholder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(listPartner[position].image)
                .into(ImgBarber)
            BarberName.text = listPartner[position].image
            Address.text = listPartner[position].lokasi
        }
        holder.itemView.setOnClickListener {
            listener.onParnerClick(listPartner[position])
        }
    }

    override fun getItemCount(): Int {
        return listPartner.size
    }
}

interface PartnersListener{
    fun onParnerClick(partners: Partners)
}