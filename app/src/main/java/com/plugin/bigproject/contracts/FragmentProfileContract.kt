package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Profile
import com.plugin.bigproject.models.User

interface FragmentProfileContract {
    interface View{
        fun showProfiletoView(profile : Profile)
        fun showToast(message : String)

    }

    interface Presenter{
        fun getUserById(token : String)
        fun destroy()
    }
}