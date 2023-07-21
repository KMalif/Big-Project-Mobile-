package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.MapsFragmentContract
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentMapsPresenter (v : MapsFragmentContract.View) : MapsFragmentContract.Presenter {
    private var view : MapsFragmentContract.View? = v
    private var apiService = APIClient.APIService()
    override fun getMitra() {
        val request = apiService.getPartnersLocation()
        request.enqueue(object : Callback<WrappedListResponse<Partners>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Partners>>,
                response: Response<WrappedListResponse<Partners>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null ){
                        println("Body Mitra  $body")
                        view?.attachMitraLocation(body.data)
                    }
                }else {
                    view?.showToast("Something went wrong")
                    println(response.errorBody())
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Partners>>, t: Throwable) {
                view?.showToast("Can't connect to server")
                println(t.message)
            }
        })

    }
}