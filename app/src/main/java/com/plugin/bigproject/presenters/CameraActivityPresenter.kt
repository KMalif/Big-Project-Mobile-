package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.CameraActivityContract
import com.plugin.bigproject.models.Recomendation
import com.plugin.bigproject.responses.WrapperRecomendationResponse
import com.plugin.bigproject.util.APIClient
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraActivityPresenter(v : CameraActivityContract.View?) : CameraActivityContract.Presenter {

    private var view : CameraActivityContract.View? = v
    private var apiService = APIClient.APIService()
    override fun prediction(image: MultipartBody.Part, hair: String) {
        val request = apiService.predict(image, hair)
        request.enqueue(object : Callback<WrapperRecomendationResponse<Recomendation>>{
            override fun onResponse(
                call: Call<WrapperRecomendationResponse<Recomendation>>,
                response: Response<WrapperRecomendationResponse<Recomendation>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast("Succes Upload")
                    }
                    else{
                        view?.showToast("gagal gaes")
                    }
                }else{
                    view?.showToast("gagal cok")
                    println("RESPONSE " + response)
                }
            }

            override fun onFailure(
                call: Call<WrapperRecomendationResponse<Recomendation>>,
                t: Throwable
            ) {
                view?.showToast("gagal cok")
                println(t.message)
            }
        })
    }

    override fun destroy() {
        view = null
    }
}