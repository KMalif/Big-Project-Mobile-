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
        fun register (name : String,username : String,gender : String, noHp : Int, email: String, password : String, role : String)
        fun destroy()
    }
}