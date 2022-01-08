package com.plugin.bigproject.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.plugin.bigproject.databinding.BookingHistoryItemBinding
import com.plugin.bigproject.models.BookHistory
import java.time.format.DateTimeFormatter

class BookingHistoryAdapter(private val listHistory : List<BookHistory>) :RecyclerView.Adapter<BookingHistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding : BookingHistoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(BookingHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = listHistory[position]
        val tanggal = listHistory[position].date
        val formater = DateTimeFormatter.ofPattern("dd/MM/yyy")
        holder.binding.apply {
            Address.text = history.alamat_mitra
            BarberName.text = history.nama_mitra
            tvDate.text = tanggal?.format(formater)
        }
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }
}