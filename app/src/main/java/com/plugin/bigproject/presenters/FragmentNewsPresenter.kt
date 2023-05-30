package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.FragmentNewsContract
import com.plugin.bigproject.models.News
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.util.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentNewsPresenter (v : FragmentNewsContract.View?): FragmentNewsContract.Presenter{
    private var view : FragmentNewsContract.View? = v
    private var apiService = APIClient.APIService()

    override fun getNews(token : String) {
        val request = apiService.getNews("Bearer $token")
        request.enqueue(object : Callback<WrappedListResponse<News>>{
            override fun onResponse(
                call: Call<WrappedListResponse<News>>,
                response: Response<WrappedListResponse<News>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        if (body.data.isEmpty()){
                            view?.showEmpty()
                        }else{
                            view?.hideEmpty()
                            view?.hideLoading()
                            view?.attachNewsToRecycler(body.data)
//                        view?.showToast("Success get News")
                        }

                    }else{
                        view?.showToast("Data is empty")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Check your connection")
                    view?.hideLoading()
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<News>>, t: Throwable) {
                view?.showToast("Cant connect to server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun destroy() {
        view = null
    }
}