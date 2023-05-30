package com.plugin.bigproject.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.plugin.bigproject.databinding.ListHistoryBinding
import com.plugin.bigproject.models.History

class HistoryAdapter(private var listHistory : List<History>, private val listener: WishlistListener) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding : ListHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(listHistory[position].image)
                .into(Thumbnail)
            TvHaircutsName.text = listHistory[position].nama_model
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                TvContent.text = Html.fromHtml(listHistory[position].content, Html.FROM_HTML_MODE_COMPACT)
            }else{
                TvContent.text = Html.fromHtml(listHistory[position].content)
            }
        }
        holder.itemView.setOnClickListener {
            listener.onWishlistCLick(listHistory[position])
        }
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }
}

interface WishlistListener{
    fun onWishlistCLick(wishlist: History)
}