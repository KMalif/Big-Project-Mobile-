package com.plugin.bigproject.contracts

import android.content.Context
import com.plugin.bigproject.models.User

interface LoginActivityContract {
    interface LoginActivityView {
        fun showToast(message : String)
        fun successLogin()
        fun showLoading()
        fun hideLoading()
    }

    interface LoginActivityPresenter {
        fun login(username : String, password: String, context : Context)
        fun destroy()
    }
}