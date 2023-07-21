package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.RegisterActivityContract
import com.plugin.bigproject.models.User
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityRegisterPresenter (v : RegisterActivityContract.RegisterActivityView?)
    : RegisterActivityContract.RegisterActivityPresenter{
    private var view : RegisterActivityContract.RegisterActivityView? = v
    private var apiService = APIClient.APIService()
    override fun register(name : String,username : String,gender : String, noHp : String, email: String, password : String, role : String) {
        val request = apiService.register(name, username, gender, noHp, email, password, role)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null ){
                        view?.showToast("Success to Register")
                        view?.successRegister()
                        view?.hideLoading()
                    }else{
                        view?.showToast("Data is empty")
                        view?.hideLoading() }
                    }else{
                    view?.showToast("Email or Username already exist")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Can't connect to server")
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }
}