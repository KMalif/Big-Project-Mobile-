package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.ChatbotActivityContract
import com.plugin.bigproject.models.Message
import com.plugin.bigproject.responses.ChatbotResponse
import com.plugin.bigproject.responses.PreviousChatResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityChatPresenter(v : ChatbotActivityContract.View?) : ChatbotActivityContract.Presenter {

    var view : ChatbotActivityContract.View? = v
    private var apiService = APIClient.APIService()

    override fun postChat(token: String, chat: String) {
        val request = apiService.sendChat("Bearer $token", chat)

        request.enqueue(object : Callback<ChatbotResponse>{
            override fun onResponse(
                call: Call<ChatbotResponse>,
                response: Response<ChatbotResponse>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        println("Chatmu $body")
                        view?.clearInput()
                        getChat("Bearer $token")
                    }else{
                        view?.showToast("Data is Empty")
                        println("Data is Empty ${response.errorBody()}")
                    }
                }else{
                    view?.showToast("Cant connect to server")
                    println("can't connect ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ChatbotResponse>, t: Throwable) {
                println("Onfailure ${t.message}")
                view?.showToast("Onfailure")
            }

        })

    }

    override fun getChat(token: String) {
        val request = apiService.getPreviousChat(token)
        request.enqueue(object : Callback<PreviousChatResponse<Message>>{
            override fun onResponse(
                call: Call<PreviousChatResponse<Message>>,
                response: Response<PreviousChatResponse<Message>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showPreviousChat(body.data)
                        println("Previuos Chat ${body.data}")
                    }else{
                        view?.showToast("Data is Empty")
                        println("Data is Empty ${response.errorBody()}")
                    }
                }else{
                    view?.showToast("Cant connect to server")
                    println("can't connect ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<PreviousChatResponse<Message>>, t: Throwable) {
                println("Onfailure ${t.message}")
                view?.showToast("Onfailure")
            }
        })
    }

    override fun destroy() {
        view = null
    }
}