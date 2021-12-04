package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.User

interface RegisterActivityContract {
    interface RegisterActivityView{
        fun showToast(message : String)
        fun successRegister()
        fun showLoading()
        fun hideLoading()
    }

    interface RegisterActivityPresenter{
        fun register (name : String,username : String, email: String, password : String)
        fun destroy()
    }
}