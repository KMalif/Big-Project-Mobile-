package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.User

interface FragmentProfileContract {
    interface View{
        fun showProfiletoView(user : User)
    }

    interface Presenter{
        fun getProfile()
    }
}