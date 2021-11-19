package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.User

interface RegisterActivityContract {
    interface RegisterActivityView{
        fun showToast(message : String)
        fun successRegister(user : User)
        fun showLoading()
        fun hideLoading()
    }

    interface RegisterActivityPresenter{
        fun register (username : String, email: String, password : String)
        fun destroy()
    }
}