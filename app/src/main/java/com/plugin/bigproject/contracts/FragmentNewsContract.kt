package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.News

interface FragmentNewsContract {
    interface View{
        fun attachNewsToRecycler(listNews : List<News>)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter{

    }
}