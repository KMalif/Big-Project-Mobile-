package com.plugin.bigproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugin.bigproject.adapters.ChatbotAdapter
import com.plugin.bigproject.contracts.ChatbotActivityContract
import com.plugin.bigproject.databinding.ActivityChatbotBinding
import com.plugin.bigproject.models.Message
import com.plugin.bigproject.presenters.ActivityChatPresenter
import com.plugin.bigproject.util.Constants

class ChatbotActivity : AppCompatActivity(), ChatbotActivityContract.View {

    private lateinit var presenter : ChatbotActivityContract.Presenter
    private lateinit var binding : ActivityChatbotBinding
    private lateinit var chatbotAdapter: ChatbotAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = ActivityChatPresenter(this)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.title = "Chatbot"
        sendChat()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showToast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun clearInput() {
        binding.EtChat.setText("")
    }

    override fun showPreviousChat(listChat: MutableList<Message>) {
        binding.RvChatbot.apply {
            chatbotAdapter = ChatbotAdapter(listChat,this@ChatbotActivity)
            layoutManager = LinearLayoutManager(this@ChatbotActivity)
            adapter = chatbotAdapter
            scrollToPosition(chatbotAdapter.itemCount-1)
            scrollState
        }
    }


    private fun sendChat(){
        val token = Constants.getToken(this)
        binding.fabSend.setOnClickListener {
        val chat = binding.EtChat.text.toString()
        if (chat.isNotEmpty()){
            presenter.postChat(token, chat)
        }else{
            showToast("Type your message first")
            }
        }
    }

    override fun onResume() {
        val token = Constants.getToken(this)
        super.onResume()
        presenter.getChat("Bearer $token")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}