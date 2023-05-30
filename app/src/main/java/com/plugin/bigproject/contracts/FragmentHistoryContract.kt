package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.History
import com.plugin.bigproject.models.News

interface FragmentHistoryContract {
    interface View{
        fun attachHistoryToRecycler(listHistory : List<History>)
        fun showLoading()
        fun hideLoading()
        fun showEmpty()
        fun hideEmpty()
        fun showToast(message: String)
    }

    interface Presenter{
        fun getHistory(token : String)
        fun destroy()
    }
}