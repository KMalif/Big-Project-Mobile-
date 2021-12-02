package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.HairCuts
import com.plugin.bigproject.models.Partners

interface FragmentHomeContract {
    interface FragmentHomeView{
        fun attachTrendToRecycler(listTrend : List<HairCuts>)
        fun attachMitraToRecycler(listMitra : List<Partners>)
        fun showLoading()
        fun hideLoading()
    }

    interface FragmentHomePresenter{
        fun getTrend()
        fun getMitra()
        fun destroy()
    }
}