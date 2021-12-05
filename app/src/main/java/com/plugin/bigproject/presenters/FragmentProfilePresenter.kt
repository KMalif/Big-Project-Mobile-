package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.FragmentProfileContract
import com.plugin.bigproject.models.User
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentProfilePresenter(v : FragmentProfileContract.View?):FragmentProfileContract.Presenter {
    private var view : FragmentProfileContract.View? = v
    private val apiService = APIClient.APIService()
    override fun getUserById(id: Int) {
        val request = apiService.getUserById(id)
        request.enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showProfiletoView(body.data)
                        view?.showToast("Get Profile user success")
                    }else{
                        view?.showToast("User is empty")
                    }
                }else{
                    view?.showToast("Check your connection")
                }
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                view?.showToast("Something went wrong")
            }
        })

    }

    override fun destroy() {
        view = null
    }
}