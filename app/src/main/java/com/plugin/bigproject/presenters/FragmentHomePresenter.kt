package com.plugin.bigproject.presenters

import com.plugin.bigproject.contracts.FragmentHomeContract
import com.plugin.bigproject.util.APIClient

class FragmentHomePresenter(v : FragmentHomeContract.FragmentHomeView?) : FragmentHomeContract.FragmentHomePresenter {

    private var view : FragmentHomeContract.FragmentHomeView? = v
    private var apiServices = APIClient.APIService()

    override fun getTrend() {

    }

    override fun getMitra() {

    }

    override fun destroy() {
        view = null
    }
}