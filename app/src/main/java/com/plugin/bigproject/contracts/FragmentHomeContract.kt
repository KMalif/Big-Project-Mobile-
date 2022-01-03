package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Partners

interface FragmentHomeContract {
    interface FragmentHomeView{
//        fun attachTrendToRecycler(listTrend : List<HairCuts>)
        fun attachMitraToRecycler(listMitra : List<Partners>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
    }

    interface FragmentHomePresenter{
//        fun getTrend()
        fun getMitra(token: String)
        fun destroy()
    }
}