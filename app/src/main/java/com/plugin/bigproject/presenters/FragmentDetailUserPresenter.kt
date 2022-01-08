package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.DetailUserFragmentContract
import com.plugin.bigproject.models.Profile
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentDetailUserPresenter(v : DetailUserFragmentContract.View?) : DetailUserFragmentContract.Presenter {

    var view : DetailUserFragmentContract.View? = v
    private val apiService = APIClient.APIService()
    override fun getUser(token: String) {
        val request = apiService.getUserById("Bearer $token")
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<Profile>>{
            override fun onResponse(
                call: Call<WrappedResponse<Profile>>,
                response: Response<WrappedResponse<Profile>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.attachDetailtoView(body.data)
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

            override fun onFailure(call: Call<WrappedResponse<Profile>>, t: Throwable) {
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