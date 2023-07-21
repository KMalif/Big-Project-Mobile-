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
        fun onItemDelete(wishlist : History)
    }

    interface Presenter{
        fun getHistory(token : String)
        fun deleteWishlist(token : String, id : Int)
        fun destroy()
    }
}