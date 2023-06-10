package com.plugin.bigproject.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListTrendingBinding
import com.plugin.bigproject.models.HairCuts
import com.plugin.bigproject.models.News

class HaircutsAdapter(private var listHaircut : List<HairCuts>, private val listener : HaircutListener)
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
            HaircutName.text = listHaircut[position].nama_model
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                HaircutDesc.text = Html.fromHtml(listHaircut[position].content, Html.FROM_HTML_MODE_COMPACT)
            }else{
                HaircutDesc.text = Html.fromHtml(listHaircut[position].content)
            }
        }

        holder.itemView.setOnClickListener{
            listener.onHaicutClick(listHaircut[position])
        }
    }

    override fun getItemCount(): Int {
        return listHaircut.size
    }
}

interface HaircutListener{
    fun onHaicutClick(hairCuts: HairCuts)
}

