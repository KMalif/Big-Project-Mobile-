package com.plugin.bigproject.contracts

interface FragmentHomeContract {
    interface FragmentHomeView{
        fun attachTrendToRecycler()
        fun attachMitraToRecycler()
        fun showLoading()
        fun hideLoading()
    }

    interface FragmentHomePresenter{
        fun getTrend()
        fun getMitra()
        fun destroy()
    }
}