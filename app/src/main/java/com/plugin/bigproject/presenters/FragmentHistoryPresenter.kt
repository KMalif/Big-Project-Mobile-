package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.FragmentHistoryContract
import com.plugin.bigproject.models.History
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHistoryPresenter(v : FragmentHistoryContract.View?): FragmentHistoryContract.Presenter{
    private var view : FragmentHistoryContract.View? = v
    private var apiService = APIClient.APIService()

    override fun getHistory(token : String) {
        val request = apiService.getHistory("Bearer $token")
        request.enqueue(object : Callback<WrappedListResponse<History>> {
            override fun onResponse(
                call: Call<WrappedListResponse<History>>,
                response: Response<WrappedListResponse<History>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                     if (body.data.isEmpty()){
                        view?.showEmpty()
                     }else{
                         view?.hideEmpty()
                         view?.hideLoading()
                         view?.attachHistoryToRecycler(body.data)
                     }
                    }else{
                        view?.showToast("Data is empty")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Check your connection")
                    view?.hideLoading()
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<History>>, t: Throwable) {
                view?.showToast("Cant connect to server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun destroy() {
        view = null
    }
}