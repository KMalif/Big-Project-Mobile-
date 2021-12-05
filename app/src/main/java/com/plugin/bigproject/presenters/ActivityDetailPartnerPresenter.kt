package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.DetailPartnerActivityContract
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityDetailPartnerPresenter(v : DetailPartnerActivityContract.DetailPartnerView?) : DetailPartnerActivityContract.DetailParnerPresenter {

    private var view : DetailPartnerActivityContract.DetailPartnerView? = v
    private var apiService = APIClient.APIService()

    override fun getPartnerbyId(id: Int) {
        val request = apiService.getPartnerbyId(id)
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

    override fun destroy() {
        view = null
    }
}