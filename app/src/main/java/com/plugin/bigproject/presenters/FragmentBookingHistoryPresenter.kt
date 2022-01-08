package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.FragmentBookingHistoryContract
import com.plugin.bigproject.models.BookHistory
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentBookingHistoryPresenter(v : FragmentBookingHistoryContract.View?) : FragmentBookingHistoryContract.Presenter {
    private var view : FragmentBookingHistoryContract.View? = v
    private val apiService = APIClient.APIService()

    override fun getBookingHistory(token: String) {
        val request = apiService.getBookingHistory("Bearer $token")
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<BookHistory>> {
            override fun onResponse(
                call: Call<WrappedListResponse<BookHistory>>,
                response: Response<WrappedListResponse<BookHistory>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.attachBookingHistory(body.data)
                        println(body.data)
                        view?.hideLoading()
                    }else{
                        view?.showToast("Data is empty")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Check your connection")
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<BookHistory>>, t: Throwable) {
                println(t.message)
                view?.showToast("Something went wrong")
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }
}