package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.User

interface FragmentProfileContract {
    interface View{
        fun showProfiletoView(user : User)
        fun showToast(message : String)

    }

    interface Presenter{
        fun getUserById(id : Int)
        fun destroy()
    }
}