package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.News

interface DetailNewsContract {
    interface DetailNewsView{
        fun showDetailNews(news : News)
        fun showToast(message : String)
    }

    interface DetailNewsPresenter{
        fun getDetailNews(id : Int)
        fun destroy()
    }
}