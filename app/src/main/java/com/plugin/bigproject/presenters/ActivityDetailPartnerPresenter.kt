package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.DetailPartnerActivityContract
import com.plugin.bigproject.models.Antre
import com.plugin.bigproject.models.Booking
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityDetailPartnerPresenter(v : DetailPartnerActivityContract.DetailPartnerView?) : DetailPartnerActivityContract.DetailParnerPresenter {

    private var view : DetailPartnerActivityContract.DetailPartnerView? = v
    private var apiService = APIClient.APIService()

    override fun getPartnerbyId(token : String, id: Int) {
        val request = apiService.getPartnerbyId("Bearer $token",id)
        request.enqueue(object : Callback<WrappedResponse<Partners>>{
            override fun onResponse(
                call: Call<WrappedResponse<Partners>>,
                response: Response<WrappedResponse<Partners>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showDetailPartner(body.data)
                        view?.hideLoading()
                        println("Detail parner ${body.data}")
                    }else{
                        view?.showToast("Data is Empty")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Something went wrong")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Partners>>, t: Throwable) {
                view?.showToast("Check your connection")
                println(t.message)
                view?.hideLoading()
            }
        })
    }

    override fun booking(token: String, idMitra: Int, status: String) {
        val request = apiService.booking("Bearer $token", idMitra, status)
        request.enqueue(object : Callback<WrappedResponse<Booking>>{
            override fun onResponse(
                call: Call<WrappedResponse<Booking>>,
                response: Response<WrappedResponse<Booking>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null ){     
                        println("Booking ${body.data}")
                        view?.showToast("Booking Success")
                        getAntre("Bearer $token", idMitra)
                    }else{
                        view?.showToast("Data is Empty")
                    }
                }else{
                    view?.showToast("Something went wrong")
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Booking>>, t: Throwable) {
                println(t.message)
                view?.showToast("onFailure")
            }
        })
    }

    override fun getAntre(token: String, idMitra: Int) {
        val request = apiService.getAntre("Bearer $token",idMitra)
        request.enqueue(object : Callback<WrappedListResponse<Antre>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Antre>>,
                response: Response<WrappedListResponse<Antre>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        view?.showWaitinglist(body.data)
                        println("Antre ${body.data}")
                    } else {
                        view?.showToast("Data is Empty")
                    }
                }else{
                    view?.showToast("Something went wrong")
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Antre>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    override fun destroy() {
        view = null
    }
}