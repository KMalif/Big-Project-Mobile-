package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.News

interface FragmentNewsContract {
    interface View{
        fun attachNewsToRecycler(listNews : List<News>)
        fun showLoading()
        fun hideLoading()
        fun showToast(message: String)
    }

    interface Presenter{
        fun getNews()
        fun destroy()
    }
}