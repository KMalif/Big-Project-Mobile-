package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.HairCuts
import com.plugin.bigproject.models.Partners

interface FragmentHomeContract {
    interface FragmentHomeView{
        fun attachHaircutToRecycler(haircuts : List<HairCuts>)
        fun attachMitraToRecycler(listMitra : List<Partners>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
    }

    interface FragmentHomePresenter{
        fun getHaircuts(token: String)
        fun getMitra(token: String)
        fun destroy()
    }
}