package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Profile
import com.plugin.bigproject.models.User

interface DetailUserFragmentContract {

    interface View{
        fun attachDetailtoView(profile: Profile)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter{
        fun getUser(token : String)
        fun destroy()
    }
}