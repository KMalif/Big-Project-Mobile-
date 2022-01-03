package com.plugin.bigproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.plugin.bigproject.databinding.ChatItemBinding
import com.plugin.bigproject.models.Message
import com.plugin.bigproject.util.Constants


class ChatbotAdapter(val messageList : MutableList<Message>, val context: Context): RecyclerView.Adapter<ChatbotAdapter.ChatViewholder>() {

    inner class ChatViewholder(val binding : ChatItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewholder {
        return ChatViewholder(ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewholder, position: Int) {
        val currentMessage = messageList[position]
        val idSender = Constants.getId(context)
        val idbot = 9
        when (currentMessage.sender) {
            idSender -> {
                holder.binding.tvMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.binding.tvBotMessage.visibility = View.GONE
            }
            idbot -> {
                holder.binding.tvBotMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.binding.tvMessage.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

}