package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.DetailNewsContract
import com.plugin.bigproject.models.News
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityDetailNewsPresenter(v : DetailNewsContract.DetailNewsView?) : DetailNewsContract.DetailNewsPresenter {
    private var view : DetailNewsContract.DetailNewsView? = v
    private var apiService = APIClient.APIService()
    override fun getDetailNews(id: Int) {
        val request = apiService.getNewsByID(id)
        request.enqueue(object : Callback<WrappedResponse<News>>{
            override fun onResponse(
                call: Call<WrappedResponse<News>>,
                response: Response<WrappedResponse<News>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showDetailNews(body.data)
                    }else{
                        view?.showToast("Data is null")
                    }
                }else{
                    view?.showToast("Something went wrong")
                }
            }

            override fun onFailure(call: Call<WrappedResponse<News>>, t: Throwable) {
                view?.showToast("Check your connection")
                println(t.message)
            }
        })
    }

    override fun destroy() {
        view = null
    }
}