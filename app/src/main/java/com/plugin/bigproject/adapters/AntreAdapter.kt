package com.plugin.bigproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plugin.bigproject.databinding.ListAntreBinding
import com.plugin.bigproject.models.Antre
import com.plugin.bigproject.util.Constants

class AntreAdapter(private var waitingList : List<Antre>, val context: Context): RecyclerView.Adapter<AntreAdapter.AntreViewholder>() {

    inner class AntreViewholder(val binding : ListAntreBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AntreViewholder {
        return AntreViewholder(ListAntreBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: AntreViewholder, position: Int) {
        val curent = waitingList[position]
        val namaUser = Constants.getName(context)
        if (curent.status.equals("menunggu") && curent.nama_user.equals(namaUser)){
            holder.binding.apply {
                tvAntre.setText("${curent.no_urut}")
                tvStatus.setText(curent.status)
                tvYou.visibility = View.VISIBLE
            }
        }else if(curent.status.equals("menunggu")){
            holder.binding.apply {
                tvAntre.setText("${curent.no_urut}")
                tvStatus.setText(curent.status)
            }
        }
    }

    override fun getItemCount(): Int {
        return waitingList.size
    }
}