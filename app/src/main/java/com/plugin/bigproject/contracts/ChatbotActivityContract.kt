package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Message

interface ChatbotActivityContract {

    interface View{
        fun showToast(message : String)
        fun clearInput()
        fun showPreviousChat(listChat : MutableList<Message>)
    }

    interface Presenter{
        fun postChat(token : String, chat : String)
        fun getChat(token : String)
        fun destroy()
    }
}