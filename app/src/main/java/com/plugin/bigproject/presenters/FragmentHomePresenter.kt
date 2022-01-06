package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.FragmentHomeContract
import com.plugin.bigproject.models.HairCuts
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHomePresenter(v : FragmentHomeContract.FragmentHomeView?) : FragmentHomeContract.FragmentHomePresenter {

    private var view : FragmentHomeContract.FragmentHomeView? = v
    private var apiService = APIClient.APIService()

    override fun getMitra(token : String) {
        val request = apiService.getPartners("Bearer $token" )
        request.enqueue(object : Callback<WrappedListResponse<Partners>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Partners>>,
                response: Response<WrappedListResponse<Partners>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null ){
                        println("Body Mitra  $body")
                        view?.attachMitraToRecycler(body.data)
                        view?.hideLoading()
                    }
                }else {
                    view?.showToast("Something went wrong")
                    println(response.errorBody())
                    view?.hideLoading()
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Partners>>, t: Throwable) {
                view?.showToast("Can't connect to server")
                println(t.message)
                view?.hideLoading()
            }
        })
    }

    override fun getHaircuts(token: String) {
        val request = apiService.getHaircuts("Bearer $token")
        request.enqueue(object : Callback<WrappedListResponse<HairCuts>>{
            override fun onResponse(
                call: Call<WrappedListResponse<HairCuts>>,
                response: Response<WrappedListResponse<HairCuts>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null ){
                        view?.attachHaircutToRecycler(body.data)
                    }
                }else {
                    view?.showToast("Something went wrong")
                    println(response.errorBody())
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<HairCuts>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    override fun destroy() {
        view = null
    }
}