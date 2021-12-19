package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.CameraActivityContract
import com.plugin.bigproject.models.Recomendation
import com.plugin.bigproject.responses.WrapperRecomendationResponse
import com.plugin.bigproject.util.APIClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraActivityPresenter(v : CameraActivityContract.View?) : CameraActivityContract.Presenter {

    private var view : CameraActivityContract.View? = v
    private var apiService = APIClient.APIService()
    override fun prediction(image: MultipartBody.Part, hair: RequestBody) {
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
                        println("Wajahmu ${body.shape}")
                    }
                    else{
                        view?.showToast("Data not Found")
                    }
                }else{
                    view?.showToast("gagal cok")
                    println("RESPONSE " + response.body()?.message)
                    println("RESPONSE " + response)
                }
            }

            override fun onFailure(
                call: Call<WrapperRecomendationResponse<Recomendation>>,
                t: Throwable
            ) {
                println(t.message)
                view?.showToast("Gak boleh dibuka")
            }
        })
    }

    override fun destroy() {
        view = null
    }
}