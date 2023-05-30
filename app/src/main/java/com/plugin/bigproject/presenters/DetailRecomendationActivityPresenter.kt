package com.plugin.bigproject.presenters

import android.util.Log
import com.plugin.bigproject.contracts.DetailRecomendationContract
import com.plugin.bigproject.models.History
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRecomendationActivityPresenter(v : DetailRecomendationContract.View?) : DetailRecomendationContract.Presenter {
    private var view : DetailRecomendationContract.View? = v
    private var apiService = APIClient.APIService()

    override fun addWishlist(token: String, requestBody: RequestBody) {
        val request = apiService.addWishlist(token, requestBody)
        request.enqueue(object : Callback<WrappedResponse<History>>{
            override fun onResponse(
                call: Call<WrappedResponse<History>>,
                response: Response<WrappedResponse<History>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        view?.showToast("berhasil ditambahkan")
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<History>>, t: Throwable) {
                view?.showToast("Cant Connect with server")
                print(t.message)
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}