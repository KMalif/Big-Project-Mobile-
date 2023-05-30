package com.plugin.bigproject.contracts

import okhttp3.RequestBody

interface DetailRecomendationContract {
    interface View{
        fun showToast(message : String)
    }

    interface Presenter{
        fun addWishlist(token : String, requestBody: RequestBody)
        fun onDestroy()
    }
}