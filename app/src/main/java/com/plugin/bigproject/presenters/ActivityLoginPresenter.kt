package com.plugin.bigproject.presenters

import android.content.Context
import com.plugin.bigproject.contracts.LoginActivityContract
import com.plugin.bigproject.models.User
import com.plugin.bigproject.responses.WrappedResponse
import com.plugin.bigproject.util.APIClient
import com.plugin.bigproject.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityLoginPresenter(v : LoginActivityContract.LoginActivityView?) : LoginActivityContract.LoginActivityPresenter {

    private var view : LoginActivityContract.LoginActivityView? = v
    private var apiService = APIClient.APIService()

    override fun login(username: String, password: String, context: Context) {
        val request = apiService.login(username, password)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>>{
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast("Succes Login")
                        view?.successLogin()
                        view?.hideLoading()
                        println("Data ${body.data}")
                        Constants.setToken(context,body.data.token!!)
                        Constants.setName(context, body.data.name!!)
                        Constants.setId(context, body.data.id!!)
                    }else{
                        view?.showToast("Data is Empty")
                        view?.hideLoading() }
                }else{
                    view?.showToast("Cant connect to server")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Username and password doesn't match")
                print(t.message)
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }
}