package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.BookHistory

interface FragmentBookingHistoryContract {
    interface View{
        fun attachBookingHistory(listHistory : List<BookHistory>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter{
        fun getBookingHistory (token : String)
        fun destroy()
    }
}